package ase;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import dao.*;
import interfaces.*;
import dto.*;
import dto.DTOOperatoer.Aktiv;
import dto.DTOProduktBatch.Status;
import exception.DALException;
import ase.WeightSocket;
import connector.MySQLConnector;


public class Controller {
    WeightSocket socket;
    Logger logger;
    IDAOOperatoer MySQLoperatoer;
    IDAOProduktBatch MySQLproductBatch;
    DTOOperatoer operatoer;
    DTOProduktBatch produktBatch;
    List<DTOReceptKomp> receptKompList;
    IDAOReceptKomp tempreceptkomp;
    IDAORaavareBatch mysqlraavareBatch;
    IDAORaavare mysqlraavare;
    DTORaavare raavare;
    HashMap<Integer, PreparedStatement> preparedstatementsContainer = new HashMap<>();
    public Controller(WeightSocket socket) {
        this.socket = socket;
        this.logger = new Logger();
        this.MySQLoperatoer = new DAOOperatoer();
        this.MySQLproductBatch = new DAOProduktBatch();
        this.operatoer = new DTOOperatoer();
        this.produktBatch = null;
    }

    public void run() throws DALException {
        boolean userOK = false, batchOK = false;
        try {
            // Connect to weight
            socket.connect();
            // TO-DO Use proper error messages
            try {new MySQLConnector();} catch (InstantiationException e1) {e1.printStackTrace();} catch (IllegalAccessException e1) {e1.printStackTrace();} catch (ClassNotFoundException e1) {e1.printStackTrace();} catch (SQLException e1) {e1.printStackTrace();}
            MySQLConnector.getConn().setAutoCommit(false);
            tempreceptkomp = new DAOReceptKomp();
            mysqlraavareBatch = new DAORaavareBatch();
            mysqlraavare = new DAORaavare();
            // Get UserID from input
            do {
                userOK = requestUserID();
            } while (operatoer == null || !userOK);

            // Get Batch Id
            do {
                batchOK = requestBatchID();
            } while (produktBatch == null || !batchOK);
            if(produktBatch.getStatus() != Status.Klar) {
                requestInput("Fejl afvejning allerede startet", "", "");
                throw new DALException("Produktbatchets status var ikke klar ved start");
            }

            produktBatch.setStatus(Status.Igang);
            preparedstatementsContainer.put(1, MySQLConnector.getConn().prepareStatement("call updateProductBatch("+produktBatch.getPbId()+",'"+produktBatch.getStatus()+"',"+produktBatch.getReceptId()+")"));
            //MySQLproductBatch.updateProduktBatch(produktBatch);

            getReceptKomp(produktBatch);

            bruttoCheck();
            produktBatch.setStatus(Status.Afsluttet);
            MySQLproductBatch.updateProduktBatch(produktBatch);
            requestInput("Faerdig med afvejningen", "", "");
        }
        catch (IOException e) {
            produktBatch.setStatus(Status.Klar);
            MySQLproductBatch.updateProduktBatch(produktBatch);
            logger.writeToLog(e.getMessage());
            System.exit(0);
        } catch (NumberFormatException e) {
            produktBatch.setStatus(Status.Klar);
            MySQLproductBatch.updateProduktBatch(produktBatch);
            e.printStackTrace();
        } catch (DALException e) {
            produktBatch.setStatus(Status.Klar);
            MySQLproductBatch.updateProduktBatch(produktBatch);
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void bruttoCheck() throws IOException, DALException {
        //Nomnetto: Required amount
        //Tolerance: weighed amount has to be within +- nomnetto


        double tara, nomnetto, tolerance, weightAmount, result;
        for (DTOReceptKomp receptKompDTO : receptKompList) {
            nomnetto = receptKompDTO.getNomNetto();
            tolerance = receptKompDTO.getTolerance();
            tarare();
            do {
                requestInput("Toem Vaegt","","");
            } while (readWeight() >= 0.01);

            raavare = mysqlraavare.getRaavare(receptKompDTO.getRaavareId());
            requestInput(raavare.getRaavareId()+":"+raavare.getRaavareNavn(),"", "");
            // Request tara.
            requestInput("Placer tara","","");
            tara = tarare();

            // Request netto.
            requestInput("Placer netto","","");
            weightAmount = readWeight();

            result = weightAmount-tara;
            //If weighted amount minus the container minus the tolerance is greater than or equal to
            //-the required amount, then we have weighed out sufficient.
            if(result-tolerance >= nomnetto) {
                DTORaavareBatch tempraavarebatch = mysqlraavareBatch.getRaavareBatchRaavare(raavare.getRaavareId());
                tempraavarebatch.setMaengde(tempraavarebatch.getMaengde()-result);
                mysqlraavareBatch.updateRaavareBatch(tempraavarebatch);
                DTOProduktBatchKomp tempProduktBatchKomp = new DTOProduktBatchKomp(produktBatch.getPbId(), tempraavarebatch.getRbId(), tara, weightAmount, operatoer.getOprId());

                IDAOProduktBatchKomp tempMySQLProdukt = new DAOProductBatchKomponent();
                tempMySQLProdukt.createProduktBatchKomp(tempProduktBatchKomp);
            }else {
                continue;
            }

        }
    }
    public String requestInput(String string1, String string2, String string3) throws IOException {
        //Format string to the weight format.
        String msg = "RM20 8 "+"\""+string1+"\" "+"\""+string2+"\" "+"\""+string3+"\" "+"\n";
        String str;
        logger.writeToLog("Client: "+msg.replace("\n", "\\n"));

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        socket.write(msg);
        //wait until we actually get the return msg.
        while (!(str = socket.read()).contains("RM20 A")) {
            logger.writeToLog("Server: "+str);
        }
        logger.writeToLog("Server: "+str);
        String[] strArr = str.split(" ");

        if(strArr.length == 3) {
            strArr = strArr[2].split("\"");
            if(strArr.length == 0) {
                return "";
            }
            return strArr[1];
        }
        return strArr[1];
    }

    private boolean userCheck(DTOOperatoer user) throws IOException {
        String str = null;
        //Request 1 for right name or 0 for wrong name.
        try {
            str = requestInput(user.initials(user.trimmer(user.getFornavn()+" "+user.getEfternavn()))+"? 1:0","","");
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NullPointerException e) {
            str = "User does not exist.";
            logger.writeToLog(str);
            return false;
        }
        if(str.length() == 0) {
            return true;
        }
        String[] strArr = str.split(" ");
        if(Integer.parseInt(strArr[0]) == 1){
            return true;
        }else {
            logger.writeToLog("Wrong name or bad input.");
            return false;
        }
    }

    public boolean requestUserID() throws IOException, NumberFormatException, DALException {
        String answer = requestInput("Input operator ID","ID","");
        operatoer = MySQLoperatoer.getOperatoer(Integer.parseInt(answer));
        if(operatoer.getAktiv().equals(Aktiv.inaktiv)) {
            throw new DALException("Fejl, brugeren er inaktiv");
        }
        return userCheck(operatoer); // Get confirmation from user
    }

    public boolean productBatchCheck(DTOProduktBatch batch) {
        String str = null;
        //Request 1 for right name or 0 for wrong name.
        try {
            IDAORecept MySQLrecept = new DAORecept();
            DTORecept recept = MySQLrecept.getRecept(batch.getReceptId());
            str = requestInput(recept.getReceptNavn() + "? 1:0","","");
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NullPointerException e) {
            str = "User does not exist.";
            logger.writeToLog(str);
            return false;
        } catch (DALException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(str.length() == 0) {
            return true;
        }
        String[] strArr = str.split(" ");
        //strArr = strArr[2].split("\"");
        if(Integer.parseInt(strArr[0]) == 1){
            return true;
        }else {
            logger.writeToLog("Wrong name or bad input.");
            return false;
        }
    }

    public boolean requestBatchID() throws IOException, NumberFormatException, DALException {
        String batchID = requestInput("Input batch ID","1-9999999","");
        produktBatch = MySQLproductBatch.getProduktBatch(Integer.parseInt(batchID));
        return productBatchCheck(produktBatch); // Get confirmation
    }

    public void getReceptKomp(DTOProduktBatch productbatch) throws DALException, IOException {
        receptKompList = tempreceptkomp.getReceptKompList(produktBatch.getReceptId());
        for (DTOReceptKomp receptKompDTO : receptKompList) {
            // Request empty weight
            DTORaavareBatch tempraavarebatch = mysqlraavareBatch.getRaavareBatchRaavare(receptKompDTO.getRaavareId());
            if(!(receptKompDTO.getNomNetto() + receptKompDTO.getTolerance() <= tempraavarebatch.getMaengde())){
                requestInput("Ikke nok materiale","","");
                throw new DALException("Ikke nok materiale");
            }
        }
    }

    public double readWeight() throws IOException{
        //Send cmd.
        String str;
        socket.write("S\n");
        logger.writeToLog("Client: S\\n");
        //Receive weight.
        str = socket.read();
        logger.writeToLog("Server: "+str);
        System.out.println(str);
        String[] strArr = str.split(" ");
        System.out.println("Debug str: "+Arrays.toString(strArr)+ " Length" + strArr.length);
        return Double.parseDouble(strArr[6]);
    }

    public double tarare() throws IOException {
        String str;
        //Send cmd.
        socket.write("T\n");
        logger.writeToLog("Client: T");
        //Read tarrering.
        str = socket.read();
        logger.writeToLog("Server: "+str);
        String[] strArr = str.split(" ");
        System.out.println("Debug str: "+Arrays.toString(strArr)+ " Length" + strArr.length);
        return Double.parseDouble(strArr[6]);
    }


}