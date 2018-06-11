package ase;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.List;

import connector.MySQLConnector;
import controller.OperatoerController;
import controller.ProduktBatchController;
import controller.RaavareBatchController;
import controller.ReceptController;
import controller.ReceptKompController;
import dao.DAOOperatoer;
import dao.DAOProduktBatch;
import dao.DAORaavareBatch;
import dao.DAORecept;
import dao.DAOReceptKomp;
import dto.DTOOperatoer;
import dto.DTOProduktBatch;
import dto.DTOProduktBatchKomp;
import dto.DTORaavareBatch;
import dto.DTORecept;
import dto.DTOReceptKomp;
import dto.Status;
import exception.DALException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AseController {
	private WeightSocket socket;
	
	public AseController(String ip, int port) {
		socket = new WeightSocket(ip, port);
	}
	
	public void run() {
		List<DTOReceptKomp> receptkompList = null;
		DTOProduktBatch pb = null;

		socket.connect();

		try {
			new MySQLConnector();
			MySQLConnector.getConn().setAutoCommit(false);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			socket.rm20("Kontakt administrator", "", "Error.");
			log.error(e.getMessage());
			socket.disconnect();
			System.exit(0);
		}

		validerOperatoer();
		pb = getProduktbatch();
		opdaterPbStatus(pb);
		receptkompList = getReceptkompliste(pb);
		
	}

	private void validerOperatoer() {
		log.info("Hent operatør.");
		OperatoerController controller = new OperatoerController(new DAOOperatoer());
		int operatorId;
		DTOOperatoer operatoer;
		String str = null;

		while(true) {
			try {
				operatorId = Integer.parseInt(socket.rm20("Input operator ID", "ID", ""));
				operatoer = controller.getOperatoer(operatorId);
				break;
			}catch(NumberFormatException e) {
				log.warn("Ikke gyldigt ID");
				socket.rm20("", "Ikke gyldigt ID", "");
			} catch (DALException e) {
				socket.rm20(e.getMessage(), "", "Fejl");
				log.warn(e.getMessage());
			}
		}

		do {
			socket.rm20(operatoer.initials(operatoer.getFornavn()+" "+operatoer.getEfternavn()), "", "");
		} while(str.length()!=0);
	}
	
	private DTOProduktBatch getProduktbatch(){
		DTOProduktBatch produktbatch = null;
		ProduktBatchController pbcontroller = new ProduktBatchController(new DAOProduktBatch());
		ReceptController rcontroller = new ReceptController(new DAORecept());
		String str = null;
		while(true){
			try {
				String batchID = socket.rm20("Input batch ID","1-9999999","");
				produktbatch = pbcontroller.getProduktBatch(Integer.parseInt(batchID));
			} catch(NumberFormatException e) {
				log.info("Ikke gyldigt PB ID.");
				socket.rm20("", "Ikke gyldigt PB ID.", "");
			} catch(DALException e) {
				log.warn(e.getMessage());
				socket.rm20("", "PB findes ikke.", "");
			}
			
			try {
				DTORecept recept = rcontroller.getRecept(produktbatch.getReceptId());
				socket.rm20(recept.getReceptNavn(), "", "Bekræft.");
				if(str.length()==0) break;
			} catch (DALException e) {
				log.warn(e.getMessage());
				socket.rm20("", e.getMessage(), "");
			}
		}
		return produktbatch;
	}
	
	private void opdaterPbStatus(DTOProduktBatch pb) {
		if(pb.getStatus()!=Status.Klar) {
			socket.rm20("Afvejning er påbegyndt.", "", "");
			socket.disconnect();
			System.exit(0);
		}
		else {
			pb.setStatus(Status.Igang);
			ProduktBatchController pbcontroller = new ProduktBatchController(new DAOProduktBatch());
			try {
				pbcontroller.updateProduktBatch(pb);
			} catch (DALException e) {
				socket.rm20(e.getMessage(), "", "");
				log.error(e.getMessage());
				socket.disconnect();
				System.exit(0);
			}
		}
	}
	
	private List<DTOReceptKomp> getReceptkompliste(DTOProduktBatch pb) {
		ReceptKompController controller = new ReceptKompController(new DAOReceptKomp());
		double netto;
		List<DTOReceptKomp> list = null;
		try {
			list = controller.getReceptKompList(pb.getReceptId());
		} catch (DALException e) {
			socket.rm20("receptkomp ikke fudnet.", "", "");
			socket.rm20("System afsluttes.", "", "");
			log.error(e.getMessage());
			socket.disconnect();
			System.exit(0);
		}
		
		for (DTOReceptKomp rk : list) {
			netto = rk.getNomNetto()*(1+(rk.getTolerance()/100));
			if(netto > getRaavareMaengde(rk.getRaavareId())){
				socket.rm20("Ikke nok materiale.", "", "");
				socket.rm20("System afsluttes.", "", "");
				log.error("Ikke nok materiale.");
				socket.disconnect();
				System.exit(0);
			}
		}
		return list;
	}
	
	private double getRaavareMaengde(int raavareId) {
		RaavareBatchController controller = new RaavareBatchController(new DAORaavareBatch());
		double maengde = 0;
		try {
			List<DTORaavareBatch> raavareBatches = controller.getRaavareBatchList(raavareId);
			for(DTORaavareBatch rb : raavareBatches) {
				maengde += rb.getMaengde();
			}
		} catch (DALException e) {
			socket.rm20("Raavarebatch ikke fundet", "", "");
			socket.rm20("System afsluttes", "", "");
			log.error(e.getMessage());
			socket.disconnect();
			System.exit(0);
		}
		return maengde;	
	}
	
	private void afvejning() {
		double tara, nomnetto;
		DTOProduktBatchKomp tempPBK = null;
		DTORaavareBatch tempRB = null;
		
	}
}
