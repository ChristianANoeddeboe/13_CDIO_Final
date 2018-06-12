package ase;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import connector.MySQLConnector;
import controller.*;
import dao.*;
import dto.*;
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

		try {
			socket.flushInput();
		} catch (IOException e) {
			log.info("Fejl i opstart, kontakt administrator.");
			log.info("System afsluttes.");
			socket.disconnect();
			System.exit(0);
		}

		DTOOperatoer operatoer = validerOperatoer();
		if(operatoer==null) {
			log.info("Cancel: operatør.");
			socket.disconnect();
			return;
		}

		pb = getProduktbatch();
		if(pb==null) {
			log.info("Cancel: Produktbatch.");
			socket.disconnect();
			return;
		}

		if(!opdaterPbStatus(pb)) {
			log.info("Cancel: Opdater produktbatch.");
			socket.disconnect();
			return;
		}

		receptkompList = getReceptkompliste(pb);
		afvejning(receptkompList, pb, operatoer);
		socket.rm20("Afvejning godkendt.", "", "");
		socket.disconnect();
	}

	@SuppressWarnings("null")
	private DTOOperatoer validerOperatoer() {
		log.info("Hent operatør.");
		OperatoerController controller = new OperatoerController(new DAOOperatoer());
		int operatorId;
		DTOOperatoer operatoer = null;
		String str = null;

		while(true) {
			try {
				str = socket.rm20("Input operator ID", "ID", "");
				if(str.contains("C") || str.contains("exit")) return null;

				operatorId = Integer.parseInt(str);
				operatoer = controller.getOperatoer(operatorId);
				str = socket.rm20(operatoer.initials(operatoer.getFornavn()+" "+operatoer.getEfternavn()), "", "Confirm.");
				if(str.contains("C") || str.contains("exit")) return null;
				break;
			}catch(NumberFormatException e) {
				log.warn("Ikke gyldigt ID");
				socket.rm20("", "Ikke gyldigt ID", "");
			} catch (DALException e) {
				socket.rm20(e.getMessage(), "", "Fejl");
				log.warn(e.getMessage());
			}
		}
		return operatoer;
	}

	@SuppressWarnings("null")
	private DTOProduktBatch getProduktbatch(){
		DTOProduktBatch produktbatch = null;
		ProduktBatchController pbcontroller = new ProduktBatchController(new DAOProduktBatch());
		ReceptController rcontroller = new ReceptController(new DAORecept());
		String str = null;
		while(true){
			try {
				String batchID = socket.rm20("Input batch ID","1-9999999","");
				if(batchID.contains("RM20 C") || batchID.contains("exit")) return null;				
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
				str = socket.rm20(recept.getReceptNavn(), "", "Confirm.");
				if(str.contains("RM20 C")) continue;
				if(str.equals("exit")) return null;
				break;
			} catch (DALException e) {
				log.warn(e.getMessage());
				socket.rm20("", e.getMessage(), "");
			}
		}
		return produktbatch;
	}

	private boolean opdaterPbStatus(DTOProduktBatch pb) {
		if(pb.getStatus()!=Status.Klar) {
			socket.rm20("Status: ikke klar.", "", "");
			return false;
		}
		else {
			pb.setStatus(Status.Igang);
			ProduktBatchController pbcontroller = new ProduktBatchController(new DAOProduktBatch());
			try {
				pbcontroller.updateProduktBatch(pb);
			} catch (DALException e) {
				socket.rm20(e.getMessage(), "", "");
				log.error(e.getMessage());
				return false;
			}
		}
		return true;
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

	private void afvejning(List<DTOReceptKomp> receptkompList, DTOProduktBatch produktbatch, DTOOperatoer operatoer) {
		double lowerbound, upperbound, weight=0, tara = 0, diffWeight=0;
		int index = 0;
		DTORaavare raavare = null;
		DTOProduktBatchKomp tempPBK = null;
		DTORaavareBatch tempRB = null;
		ProduktBatchKompController pbkController = new ProduktBatchKompController(new DAOProduktBatchKomp());
		RaavareController rController = new RaavareController(new DAORaavare());
		RaavareBatchController rbController = new RaavareBatchController(new DAORaavareBatch());
		List<DTORaavareBatch> raavareBatches = null;
		String str = null;

		for(DTOReceptKomp receptKomp : receptkompList) {
			try {
				do {
					socket.rm20("Toem vaegt", "", "");
				} while (socket.readWeight()>=0.0001);
			} catch (IOException e) {
				System.out.println("Fejl i afvejning, kontakt administrator.");
				System.out.println("System afsluttes.");
				socket.disconnect();
				System.exit(0);
			}

			try {
				raavare = rController.getRaavare(receptKomp.getRaavareId());
				socket.rm20(raavare.getRaavareId()+"; "+raavare.getRaavareNavn(), "", "");
			} catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			socket.rm20("Placer tara.", "", "");
			try {
				tara = socket.tarer();
			} catch (IOException e) {
				System.out.println("Fejl i tarering, kontakt administrator.");
				System.out.println("System afsluttes.");
				socket.disconnect();
				System.exit(0);
			}

			lowerbound = receptKomp.getNomNetto()*(1-(receptKomp.getTolerance()/100));
			upperbound = receptKomp.getNomNetto()*(1+(receptKomp.getTolerance()/100));

			//TODO fix this.
			do {
				try {
					str = socket.rm20("Angiv raavarebatch ID", "", "");
					if(str.contains("RM20 C") || str.contains("exit")) socket.rm20("What to do...", "", ""); 
					tempRB = rbController.getRaavareBatch(Integer.parseInt(str));
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (DALException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				socket.rm20("Current RB: "+tempRB.getMaengde()+"kg.", "", receptKomp.getNomNetto()+"kg.");
				try {
					weight = socket.readWeight();
					diffWeight = weight-diffWeight;
					tempRB.setMaengde(tempRB.getMaengde()-diffWeight);
					tempPBK = new DTOProduktBatchKomp(produktbatch.getPbId(), tempRB.getRaavareId(), tara, diffWeight, operatoer.getOprId());
					pbkController.createProdBatchKomp(tempPBK);
				} catch (IOException e) {
					System.out.println("Fejl i afvejning, kontakt administrator.");
					System.out.println("System afsluttes.");
					socket.disconnect();
					System.exit(0);
				} catch (DALException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while(lowerbound>weight || upperbound<weight);

			socket.rm20("Toem vaegt.", "", "");
			while(!bruttokontrol(tara)) {
				socket.rm20("", "Bruttokontrol fejlet.", "");
			}
			try {
				socket.rm20("Bruttokontrol godkendt.", "", "");
				socket.tarer();
				index=0;
			} catch (IOException e) {
				System.out.println("Fejl under bruttokontrol, kontakt administrator.");
				System.out.println("System afsluttes.");
				log.error(e.getMessage());
				socket.disconnect();
				System.exit(0);
			}
		}
	}

	private boolean bruttokontrol(double tara) {
		double afvejning = 0;
		try {
			afvejning = socket.readWeight()*-1;
		} catch (IOException e) {
			System.out.println("Opstod fejl, kontakt administrator.");
			System.out.println("Sytem afsluttes.");
			log.error(e.getMessage());
			socket.disconnect();
			System.exit(0);
		}
		if(Math.abs(afvejning-tara)<0.00001) return true;
		return false;
	}
}
