package ase;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import connector.MySQLConnector;
import dao.MySQLOperatoerDAO;
import dao.MySQLProduktBatchDAO;
import dao.MySQLRaavareBatchDAO;
import dao.MySQLRaavareDAO;
import dao.MySQLReceptDAO;
import dao.MySQLReceptKompDAO;
import dto.OperatoerDTO;
import dto.ProduktBatchDTO;
import dto.ReceptDTO;
import dto.ReceptKompDTO;
import dto.ProduktBatchDTO.Status;
import dto.RaavareBatchDTO;
import dto.RaavareDTO;
import exception.DALException;

public class Controller {
	WeightSocket socket;
	Logger logger;
	MySQLOperatoerDAO MySQLoperatoer;
	MySQLProduktBatchDAO MySQLproductBatch;
	OperatoerDTO operatoer;
	ProduktBatchDTO produktBatch;
	List<ReceptKompDTO> receptKompList = new ArrayList<ReceptKompDTO>();


	public Controller(WeightSocket socket) {
		this.socket = socket;
		this.logger = new Logger();
		this.MySQLoperatoer = new MySQLOperatoerDAO();
		this.MySQLproductBatch = new MySQLProduktBatchDAO();
		this.operatoer = new OperatoerDTO();
		this.produktBatch = null;
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

	public void run() {
		// TODO Remove variables below
		// TODO Collect all vars in one place
		double netto1, netto2; 
		double tar1;
		try {
			// Connect to weight
			socket.connect();
			// TODO Use proper error messages
			try {new MySQLConnector();} catch (InstantiationException e1) {
				e1.printStackTrace();} catch (IllegalAccessException e1) {
					e1.printStackTrace();} catch (ClassNotFoundException e1) {
						e1.printStackTrace();} catch (SQLException e1) {
							e1.printStackTrace();}
			// Find User
			boolean userOK = false;
			// TODO move into own method
			do {
				String answer = requestInput("Input operator ID","ID","");
				operatoer = MySQLoperatoer.getOperatoer(Integer.parseInt(answer));
				userOK = userCheck(operatoer);
			} while (operatoer == null || !userOK);

			// Get Batch Id
			produktBatch = null;
			boolean batchOK = false;
			// TODO move into own method
			do {
				try {
					String batchID = requestInput("Input batch ID","1-9999999","");
					produktBatch = MySQLproductBatch.getProduktBatch(Integer.parseInt(batchID));
					batchOK = productBatchCheck(produktBatch);
				} catch(NullPointerException e) {
					logger.writeToLog("Batch does not exist or bad input.");
				}
			} while (produktBatch == null || !batchOK);

			produktBatch.setStatus(Status.Igang);
			MySQLproductBatch.updateProduktBatch(produktBatch);
			// TODO initialize at top
			MySQLReceptKompDAO tempreceptkomp = new MySQLReceptKompDAO();
			MySQLRaavareBatchDAO mysqlraavareBatch = new MySQLRaavareBatchDAO();
			MySQLRaavareDAO mysqlraavare = new MySQLRaavareDAO();
			RaavareDTO raavare;
			// TODO Move blow into own method
			receptKompList = tempreceptkomp.getReceptKompList(produktBatch.getReceptId());
			for (ReceptKompDTO receptKompDTO : receptKompList) {
				// Request empty weight
				RaavareBatchDTO tempraavarebatch = mysqlraavareBatch.getRaavareBatchRaavare(receptKompDTO.getRaavareId());
				if(!(receptKompDTO.getNomNetto() + receptKompDTO.getTolerance() <= tempraavarebatch.getMaengde())){
					requestInput("Ikke nok materiale","","");
					throw new DALException("Ikke nok materiale");
				}
			}
			// TODO move vars to top and for loop into own method
			double netto, tara, result;
			for (ReceptKompDTO receptKompDTO : receptKompList) {
				do {
					requestInput("Toem Vaegt","","");
				} while (readWeight() >= 0.01);
				raavare = mysqlraavare.getRaavare(receptKompDTO.getRaavareId());
				requestInput(raavare.getRaavareId()+":"+raavare.getRaavareNavn(),"", "");
				// Request tara.
				requestInput("Placer tara","","");
				tar1 = tarare();

				// Request netto.
				requestInput("Placer netto","","");
				netto1 = readWeight();
				tarare();

				// Request empty weight
				do {
					requestInput("Fjern netto", "","");
					netto2 = readWeight();
				} while (netto2 == tar1);
			}
			
			
			// TODO Finish and convert to trans



			// Inform
			requestInput("Kasser","","");
			tarare();
		}	
		catch (IOException e) {
			logger.writeToLog(e.getMessage());
			System.exit(0);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Requests an input from the user.
	 * @param msg
	 * @return
	 * @throws IOException
	 */
	// TODO Cleanup
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
	
	private boolean userCheck(OperatoerDTO user) throws IOException {
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

		String[] strArr = str.split(" ");
		//strArr = strArr[2].split("\"");
		if(Integer.parseInt(strArr[0]) == 1){
			return true;
		}else {
			logger.writeToLog("Wrong name or bad input.");
			return false;
		}
	}

	public boolean productBatchCheck(ProduktBatchDTO batch) {
		String str = null;
		//Request 1 for right name or 0 for wrong name.
		try {
			MySQLReceptDAO MySQLrecept = new MySQLReceptDAO();
			ReceptDTO recept = MySQLrecept.getRecept(batch.getReceptId());
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

		String[] strArr = str.split(" ");
		//strArr = strArr[2].split("\"");
		if(Integer.parseInt(strArr[0]) == 1){
			return true;
		}else {
			logger.writeToLog("Wrong name or bad input.");
			return false;
		}
	}
}
