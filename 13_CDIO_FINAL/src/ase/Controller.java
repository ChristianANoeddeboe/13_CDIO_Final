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
//import dto.DTOOperatoer.Aktiv;
//import dto.DTOProduktBatch.Status;
import exception.DALException;
import ase.WeightSocketOld;
import connector.MySQLConnector;
import controller.OperatoerController;
import controller.ProduktBatchController;
import controller.ProduktBatchKompController;
import controller.RaavareBatchController;
import controller.RaavareController;
import controller.ReceptController;
import controller.ReceptKompController;
import logging.LogHandler;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Controller {
	WeightSocketOld socket;
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
	
	public Controller(WeightSocketOld socket) {
		this.socket = socket;
		this.operatoer = new DTOOperatoer();
		this.produktBatch = null;
	}

	public void run() {
		boolean userOK = false, batchOK = false;
		try {
//			// Connect to weight
//			socket.connect();
//			// TO-DO Use proper error messages
//			try {
//				new MySQLConnector();
//			} catch (InstantiationException e1) {e1.printStackTrace();
//			} catch (IllegalAccessException e1) {e1.printStackTrace();
//			} catch (ClassNotFoundException e1) {e1.printStackTrace();
//			} catch (SQLException e1) {e1.printStackTrace();
//			}
//			MySQLConnector.getConn().setAutoCommit(false);
//
//			// Get UserID from input
//			do {
//				userOK = requestUserID();
//			} while (operatoer == null || !userOK);

			// Get Batch Id
//			do {
//				batchOK = requestBatchID();
//			} while (produktBatch == null || !batchOK);
//			if(produktBatch.getStatus() != Status.Klar) {
//				requestInput("Fejl afvejning allerede startet", "", "");
//				throw new DALException("Produktbatchets status var ikke klar ved start");
//			}

//			produktBatch.setStatus(Status.Igang);
//			productBatchController.updateProduktBatch(produktBatch);

//			getReceptKomp(produktBatch);

			afvejning();

			produktBatch.setStatus(Status.Afsluttet);
			productBatchController.updateProduktBatch(produktBatch);
			requestInput("Faerdig med afvejningen", "", "");
		}
		catch (IOException e) {
			log.warn(e.getMessage());
		} catch (SQLException e) {
			log.warn(e.getMessage());
		} catch(DALException e) {
			log.info(e.getMessage());
		}
		finally {
			try {
				socket.disconnect();
			} catch (IOException e1) {
				log.warn(e1.getMessage());
			}
//			log.getHandlers()[0].close();
			log.info("System exit.");
			System.exit(0);
		}
	}
	private void afvejning() throws IOException, DALException {
		double tara, nomnetto, tolerance, weightAmount, upperbound, lowerbound;
		DTOProduktBatchKomp tempProduktBatchKomp = null;
		DTORaavareBatch temp = null;
		ProduktBatchKompController pbkController = new ProduktBatchKompController(new DAOProduktBatchKomp());

		for (DTOReceptKomp receptKompDTO : receptKompList) {
			nomnetto = receptKompDTO.getNomNetto();
			tolerance = receptKompDTO.getTolerance();

			do {
				requestInput("Toem Vaegt","","");
			} while (readWeight()>=0.0001);

			raavare = raavareController.getRaavare(receptKompDTO.getRaavareId());
			requestInput(raavare.getRaavareId()+" : "+raavare.getRaavareNavn(),"", "");
			// Request tara.
			requestInput("Placer tara","","");
			tara = tarer();

			lowerbound = nomnetto*(1-(tolerance/100));
			upperbound = nomnetto*(1+(tolerance/100));
			do {
				requestInput("Placer netto","","");
				weightAmount = readWeight();
			} while(lowerbound>weightAmount || upperbound<weightAmount);

			int index = 0;
			List<DTORaavareBatch> raavareBatches = raavareBatchController.getRaavareBatchList(raavare.getRaavareId());

			do {
				temp = raavareBatches.get(index++);
				if(temp.getMaengde()<weightAmount) {
					weightAmount-=temp.getMaengde();
					tempProduktBatchKomp = new DTOProduktBatchKomp(produktBatch.getPbId(), temp.getRbId(), tara, temp.getMaengde(), operatoer.getOprId());
					temp.setMaengde(0);
					raavareBatchController.updateRaavareBatch(temp);
				}
				else {
					tempProduktBatchKomp = new DTOProduktBatchKomp(produktBatch.getPbId(), temp.getRbId(), tara, weightAmount, operatoer.getOprId());
					temp.setMaengde(temp.getMaengde()-weightAmount);
					raavareBatchController.updateRaavareBatch(temp);
					weightAmount = 0;
				}
				pbkController.createProdBatchKomp(tempProduktBatchKomp);
			} while(weightAmount>0.00001);

			requestInput("", "T�m v�gten.", "");
			while(!bruttokontrol(tara)) {
				requestInput("", "Bruttokontrol fejlet.", "");
			}
			tarer();
		}
	}

//	private String requestInput(String string1, String string2, String string3) {
//		//Format string to the weight format.
//		string1 = truncateMsg(string1);
//		string2 = truncateMsg(string2);
//		string3 = truncateUnit(string3);
//		String msg = "RM20 8 "+"\""+string1+"\" "+"\""+string2+"\" "+"\""+string3+"\" "+"\n";
//		String str = null;
//		log.info("Client: "+msg.replace("\n", "\\n"));
//
//		/*Der skal sleepes f�r den vil vise beskeden for some reason.*/
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		try {
//			socket.write(msg);
//			//wait until we actually get the return msg.
//			while (!(str = socket.read()).contains("RM20 A")) {
//				log.info("Server: "+str);
//				if(str.contains("RM20 C")) {
//					log.info("Server: "+str);
//					showMsg("Bye", 1000);
////					log.getHandlers()[0].close();
//					try {
//						socket.disconnect();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					System.exit(0);
//				}
//			}
//		} catch(IOException e) {
//			log.warn(e.getMessage());
//			try {
//				socket.disconnect();
//			} catch (IOException e1) {
//				log.warn(e.getMessage());
//			}
////			log.getHandlers()[0].close();
//			System.exit(0);
//		}
//
//		log.info("Server: "+str);
//		String[] strArr = str.split(" ");
//
//		if(strArr.length == 3) {
//			strArr = strArr[2].split("\"");
//			if(strArr.length == 0) {
//				return "";
//			}
//			return strArr[1];
//		}
//		return strArr[1];
//	}

//	private boolean userCheck(DTOOperatoer user) throws IOException {
//		String str = null;
//		//Request 1 for right name or 0 for wrong name.
//		try {
//			str = requestInput(user.initials(user.trimmer(user.getFornavn()+" "+user.getEfternavn()))+"? 1:0","","");
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NullPointerException e) {
//			str = "User does not exist.";
//			log.warn(str);
//			return false;
//		}
//		if(str.length() == 0) {
//			return true;
//		}
//		String[] strArr = str.split(" ");
//		if(Integer.parseInt(strArr[0]) == 1){
//			return true;
//		}else {
//			log.warn("Wrong name or bad input.");
//			return false;
//		}
//	}

//	private boolean requestUserID() throws IOException {
//		while(true) {
//			try {
//				String answer = requestInput("Input operator ID","ID","");
//				operatoer = operatoerController.getOperatoer(Integer.parseInt(answer));
//
//				if(operatoer.getAktiv().equals(Aktiv.inaktiv)) {
//					throw new DALException("Fejl, brugeren er inaktiv");
//				}
//				return userCheck(operatoer);
//			} catch(NumberFormatException e) {
//				log.info("Ikke gyldigt ID");
//				requestInput("", "Ikke gyldigt ID", "");
//			} catch(DALException e) {
//				log.info(e.getMessage());
//				requestInput("", e.getMessage(), "");
//			} 
//		}
//	}

//	private boolean productBatchCheck(DTOProduktBatch batch) {
//		String str = null;
//		//Request 1 for right name or 0 for wrong name.
//		try {
//			IDAORecept MySQLrecept = new DAORecept();
//			DTORecept recept = MySQLrecept.getRecept(batch.getReceptId());
//			str = requestInput(recept.getReceptNavn() + "? 1:0","","");
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NullPointerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (DALException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		if(str.length() == 0) {
//			return true;
//		}
//		String[] strArr = str.split(" ");
//		//strArr = strArr[2].split("\"");
//		if(Integer.parseInt(strArr[0]) == 1){
//			return true;
//		}else {
//			log.warn("Wrong name or bad input.");
//			return false;
//		}
//	}

//	private boolean requestBatchID() throws IOException {
//		while(true){
//			try {
//				String batchID = requestInput("Input batch ID","1-9999999","");
//				produktBatch = productBatchController.getProduktBatch(Integer.parseInt(batchID));
//				return productBatchCheck(produktBatch);
//			} catch(NumberFormatException e) {
//				log.info("Ikke gyldigt PB ID.");
//				requestInput("", "Ikke gyldigt PB ID.", "");
//			} catch(DALException e) {
//				log.info(e.getMessage());
//				requestInput("", "PB findes ikke.", "");
//			}
//		}
//	}

//	private void getReceptKomp(DTOProduktBatch productbatch) throws DALException, IOException {
//		receptKompList = receptKompController.getReceptKompList(produktBatch.getReceptId());
//		double netto;
//		for (DTOReceptKomp receptKompDTO : receptKompList) {
//			netto = receptKompDTO.getNomNetto()*(1+(receptKompDTO.getTolerance()/100));
//			if(netto > samletMaengdeRaavare(receptKompDTO.getRaavareId())){
//				requestInput("Ikke nok materiale","","");
//				throw new DALException("Ikke nok materiale");
//			}
//		}
//	}

//	private double readWeight() throws IOException{
//		double weight = 0;
//		String str;
//		//Send cmd.
//		socket.write("S\n");
//		log.info("Client: S\\n");
//
//		//Receive weight.
//		str = socket.read();
//		log.info("Server: "+str);
//
//		if(str.contains("-")) {
//			String[] strArr = str.split(" ");
//			weight = Double.parseDouble(strArr[5]); 
//		}
//		else {
//			String[] strArr = str.split(" ");
//			weight = Double.parseDouble(strArr[6]); 
//		}
//		return weight;
//	}

	private double tarer() throws IOException {
		String str;
		//Send cmd.
		socket.write("T\n");
		log.info("Client: T");
		//Read tarrering.
		str = socket.read();
		log.info("Server: "+str);
		String[] strArr = str.split(" ");
		return Double.parseDouble(strArr[6]);
	}

//	private String truncateMsg(String msg) {
//		if(msg.length()>=1)
//			return msg.substring(0, Math.min(msg.length(), MSGLENGTH));
//		return "";
//	}
//
//	private String truncateUnit(String unit) {
//		if(unit.length()>=1)
//			return unit.substring(0, Math.min(unit.length(), UNITLENGTH));
//		return "";
//	}

//	private double samletMaengdeRaavare(int raavareId) {
//		double maengde = 0;
//		try {
//			List<DTORaavareBatch> raavareBatches = raavareBatchController.getRaavareBatchList(raavareId);
//			for(DTORaavareBatch rb : raavareBatches) {
//				maengde += rb.getMaengde();
//			}
//		} catch (DALException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return maengde;
//	}

//	private void showMsg(String msg, int mili) throws IOException {
//		/*Der skal sleepes f�r den vil vise beskeden for some reason.*/
//		try {
//			Thread.sleep(100);
//			String str = "D \""+msg+"\"\n";
//			socket.write(str);
//			Thread.sleep(mili);
//			socket.write("DW\n");
//		} catch (InterruptedException e) {
//			// En tr�det system, b�r ikke kunne interruptes.
//			e.printStackTrace();
//		}
//	}

	private boolean bruttokontrol(double tara) throws IOException {
		double afvejning = 0;
		afvejning = readWeight()*-1;
		if(Math.abs(afvejning-tara)<0.00001) return true;
		return false;
	}
}