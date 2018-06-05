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
import controller.OperatoerController;
import controller.ProduktBatchController;
import controller.ProduktBatchKompController;
import controller.RaavareBatchController;
import controller.RaavareController;
import controller.ReceptController;
import controller.ReceptKompController;


public class Controller {
	final static int MSGLENGTH = 24;
	final static int UNITLENGTH = 7;
	
    WeightSocket socket;
    Logger logger;
	static OperatoerController operatoerController = new OperatoerController(new DAOOperatoer());
	static RaavareBatchController raavareBatchController = new RaavareBatchController(new DAORaavareBatch());
	static ProduktBatchController productBatchController = new ProduktBatchController(new DAOProduktBatch());
	static RaavareController raavareController = new RaavareController(new DAORaavare());
	static ReceptController receptController = new ReceptController(new DAORecept());
	static ReceptKompController receptKompController = new ReceptKompController(new DAOReceptKomp());
    DTOOperatoer operatoer;
    DTOProduktBatch produktBatch;
    List<DTOReceptKomp> receptKompList;
    DTORaavare raavare;
    //HashMap<Integer, PreparedStatement> preparedstatementsContainer = new HashMap<>();
    public Controller(WeightSocket socket) {
        this.socket = socket;
        this.logger = new Logger();
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
            productBatchController.updateProduktBatch(produktBatch);

            getReceptKomp(produktBatch);

            afvejning();
            produktBatch.setStatus(Status.Afsluttet);
            productBatchController.updateProduktBatch(produktBatch);
            requestInput("Faerdig med afvejningen", "", "");
        }
        catch (IOException e) {
            produktBatch.setStatus(Status.Klar);
            productBatchController.updateProduktBatch(produktBatch);
            logger.writeToLog(e.getMessage());
            System.exit(0);
        } catch (NumberFormatException e) {
            produktBatch.setStatus(Status.Klar);
            productBatchController.updateProduktBatch(produktBatch);
            e.printStackTrace();
        } catch (DALException e) {
            produktBatch.setStatus(Status.Klar);
            productBatchController.updateProduktBatch(produktBatch);
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void afvejning() throws IOException, DALException {
        //Nomnetto: Required amount
        //Tolerance: weighed amount has to be within +- nomnetto
        double tara, nomnetto, tolerance, weightAmount, result;
        DTOProduktBatchKomp tempProduktBatchKomp = null;
        DTORaavareBatch temp = null;
        ProduktBatchKompController pbkController = new ProduktBatchKompController(new DAOProduktBatchKomp());
        
        for (DTOReceptKomp receptKompDTO : receptKompList) {
            nomnetto = receptKompDTO.getNomNetto();
            tolerance = receptKompDTO.getTolerance();
            tarare();
            do {
                requestInput("Toem Vaegt","","");
            } while (readWeight() >= 0.01);

            raavare = raavareController.getRaavare(receptKompDTO.getRaavareId());
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
            	int index = 0;
                List<DTORaavareBatch> raavareBatches = raavareBatchController.getRaavareBatchList(raavare.getRaavareId());       
                
                do {
                	temp = raavareBatches.get(index++);
                	if(temp.getMaengde()<result) {
                		result-=temp.getMaengde();
                		tempProduktBatchKomp = new DTOProduktBatchKomp(produktBatch.getPbId(), temp.getRbId(), tara, temp.getMaengde(), operatoer.getOprId());
                		temp.setMaengde(0);
                		raavareBatchController.updateRaavareBatch(temp);
                	}
                	else {
                		tempProduktBatchKomp = new DTOProduktBatchKomp(produktBatch.getPbId(), temp.getRbId(), tara, result, operatoer.getOprId());
                		temp.setMaengde(temp.getMaengde()-result);
                		raavareBatchController.updateRaavareBatch(temp);
                		result = 0;
                	}
                	 pbkController.createProdBatchKomp(tempProduktBatchKomp);
                } while(result>0);
            }
            else {
                continue;
            }
        }
    }
    
    public String requestInput(String string1, String string2, String string3) throws IOException {
        //Format string to the weight format.
    	string1 = truncateMsg(string1);
    	string2 = truncateMsg(string2);
    	string3 = truncateUnit(string3);
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
        operatoer = operatoerController.getOperatoer(Integer.parseInt(answer));
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
        produktBatch = productBatchController.getProduktBatch(Integer.parseInt(batchID));
        return productBatchCheck(produktBatch); // Get confirmation
    }

    public void getReceptKomp(DTOProduktBatch productbatch) throws DALException, IOException {
        receptKompList = receptKompController.getReceptKompList(produktBatch.getReceptId());
        for (DTOReceptKomp receptKompDTO : receptKompList) {
            // Request empty weight
            if(!(receptKompDTO.getNomNetto() + receptKompDTO.getTolerance() <= samletMaengdeRaavare(receptKompDTO.getRaavareId()))){
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
    
    private String truncateMsg(String msg) {
    		return msg.substring(0, Math.min(msg.length()-1, MSGLENGTH-1));
    }
    
    private String truncateUnit(String unit) {
    	return unit.substring(0, Math.min(unit.length()-1, UNITLENGTH-1));
    }
    
    private double samletMaengdeRaavare(int raavareId) {
    	double maengde = 0;
    	try {
			List<DTORaavareBatch> raavareBatches = raavareBatchController.getRaavareBatchList(raavareId);
			for(DTORaavareBatch rb : raavareBatches) {
				maengde += rb.getMaengde();
			}
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return maengde;
    }
    
    private void regulerMaengde(int raavareId, double maengde) {
    	List<DTORaavareBatch> raavareBatches;
		try {
			raavareBatches = raavareBatchController.getRaavareBatchList(raavareId);
	    	for(DTORaavareBatch rb : raavareBatches) {
	    		
	    	}
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}