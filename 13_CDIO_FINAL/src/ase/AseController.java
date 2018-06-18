package ase;

import controller.*;
import dto.*;
import exception.DALException;
import interfaces.*;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.List;

public class AseController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AseController.class);
    private WeightSocket socket;

    public AseController(String ip, int port) {
        socket = new WeightSocket(ip, port);
    }

    /**
     * Opretter forbindelse mellem vaegt, system og database, samt paabegynder afvejningsprocessen.
     */
    public void run() {
        List<DTOReceptKomp> receptkompList = null;
        DTOProduktBatch pb = null;
        IProduktBatchController pbController;

        socket.connect();

        try {
            socket.flushInput();
        } catch (IOException e) {
            log.info("Fejl i opstart, kontakt administrator.");
            log.info("System afsluttes.");
            socket.disconnect();
            System.exit(0);
        }

        DTOBruger operatoer = validerOperatoer(); // Get operator
        if (operatoer == null) {
            log.info("Cancel: operatør.");
            socket.disconnect();
            return;
        }

        pb = getProduktbatch(); // Get product batch and confirm with recept name
        if (pb == null) {
            log.info("Cancel: Produktbatch.");
            socket.disconnect();
            return;
        }

        if (!opdaterPbStatus(pb)) { // Check status and update
            log.info("Cancel: Opdater produktbatch.");
            socket.disconnect();
            return;
        }

        receptkompList = getReceptkompliste(pb);
        if (receptkompList == null) {
            log.info("Cancel: Get recept komponent");
            socket.disconnect();
            return;
        }

        afvejning(receptkompList, pb, operatoer);
        pbController = ProduktBatchController.getInstance();
        pb.setStatus(Status.Afsluttet);
        try {
			pbController.updateProduktBatch(pb);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | DALException e) {
			//TODO opdater produktbatch exception, skal vi bare prøve at opdatere igen eller hvordan?
			socket.rm20(e.getMessage(), "", "Fejl");
			log.error(e.getMessage());
		}
        socket.rm20("Afvejning godkendt.", "", "");
        socket.disconnect();
    }

    private DTOBruger validerOperatoer() {
        log.info("Hent operatør.");
        IBrugerController controller = BrugerController.getInstance();
        int operatorId;
        DTOBruger operatoer = null;
        String str = null;

        while (true) {
            try {
                str = socket.rm20("Indtast operator ID", "ID", "");
                if (str.contains("C") || str.contains("exit")) return null;

                operatorId = Integer.parseInt(str);
                operatoer = controller.getBruger(operatorId);
                str = socket.rm20(operatoer.initials(operatoer.getFornavn() + " " + operatoer.getEfternavn()), "Bekraeft!", "");
                if (str.contains("C") || str.contains("exit")) return null;
                break;
            } catch (NumberFormatException  | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                log.warn("Ikke gyldigt ID");
                socket.rm20("", "Ikke gyldigt ID", "");
            } catch (DALException e) {
                socket.rm20(e.getMessage(), "", "Fejl");
                log.warn(e.getMessage());
            }
        }
        return operatoer;
    }

    private DTOProduktBatch getProduktbatch() {
        DTOProduktBatch produktbatch = null;
        IProduktBatchController pbcontroller = ProduktBatchController.getInstance();
        IReceptController rcontroller = ReceptController.getInstance();
        String str = null;
        while (true) {
            try {
                String batchID = socket.rm20("Indtast batch ID", "ID", "");
                if (batchID.contains("RM20 C") || batchID.contains("exit")) return null;
                produktbatch = pbcontroller.getProduktBatch(Integer.parseInt(batchID));
            } catch (NumberFormatException e) {
                log.info("Ikke gyldigt PB ID.");
                socket.rm20("", "Ikke gyldigt PB ID.", "");
                continue;
            } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                log.warn(e.getMessage());
                socket.rm20("", "PB findes ikke.", "");
                continue;
            }

            // Henter det recept, produktbatched tilhører, for at faa navnet paa vores produktbatch.
            try {
                DTORecept recept = rcontroller.getRecept(produktbatch.getReceptId());
                str = socket.rm20(recept.getReceptNavn(), "Bekraeft!", "");
                if (str.contains("RM20 C")) continue;
                if (str.equals("exit")) return null;
                break;
            } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                log.warn(e.getMessage());
                socket.rm20("", e.getMessage(), "");
            }
        }
        return produktbatch;
    }

    private boolean opdaterPbStatus(DTOProduktBatch pb) {
        if (pb.getStatus() != Status.Klar) {
            socket.rm20("Status: ikke klar.", "ok?", "");
            return false;
        }else {
            pb.setStatus(Status.Igang);
            IProduktBatchController pbcontroller = ProduktBatchController.getInstance();
            try {
                pbcontroller.updateProduktBatch(pb);
            } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                socket.rm20(e.getMessage(), "", "");
                log.error(e.getMessage());
                return false;
            }
        }
        return true;
    }

    private List<DTOReceptKomp> getReceptkompliste(DTOProduktBatch pb) {
        IReceptKompController controller = ReceptKompController.getInstance();
        double netto;
        List<DTOReceptKomp> list = null;
        try {
            list = controller.getReceptKompList(pb.getReceptId()); // Get list of recept comps for matching recept id
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            socket.rm20("receptkomp ikke fundet.", "ok?", "");
            socket.rm20("System afsluttes.", "ok?", "");
            log.error(e.getMessage());
            return null;
        }
        
        for (DTOReceptKomp rk : list) {
            if (rk.getNomNetto() >= getRaavareMaengde(rk.getRaavareId())) { // Check if required(nom netto) is greater than or equal to what we have on stock
                socket.rm20("Ikke nok materiale.", "ok?", "");
                socket.rm20("System afsluttes.", "ok?", "");
                log.error("Ikke nok materiale.");
                return null;
            }
        }
        return list;
    }

    private double getRaavareMaengde(int raavareId) {
        IRaavareBatchController controller = RaavareBatchController.getInstance();
        double maengde = 0;
        try {
            List<DTORaavareBatch> raavareBatches = controller.getRaavareBatchList(raavareId);
            for (DTORaavareBatch rb : raavareBatches) {
                maengde += rb.getMaengde();
            }
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            socket.rm20("Raavarebatch ikke fundet", "ok?", "");
            socket.rm20("System afsluttes", "ok?", "");
            abort("Raavarebatch ikke fundet.", e);
        }
        return maengde;
    }

    private void afvejning(List<DTOReceptKomp> receptkompList, DTOProduktBatch produktbatch, DTOBruger operatoer) {
        double lowerbound, upperbound, weight = 0, tara = 0, diffWeight = 0, tempweight = 0;
        DTORaavare raavare = null;
        DTOProduktBatchKomp tempPBK = null;
        DTORaavareBatch tempRB = null;
        IProduktBatchKompController pbkController = ProduktBatchKompController.getInstance();
        IRaavareBatchController rbController = RaavareBatchController.getInstance();
        List<DTORaavareBatch> raavareBatches = null;
        String str = null;

        for (DTOReceptKomp receptKomp : receptkompList) { // For all recept components

            emptyWeight(); // Request weight to be emptied
            raavare = retreiveRaavare(receptKomp.getRaavareId()); // Retrive the raavare(specified in recept component) and confirm with user
            tara = retreiveTara(); // Request the user to tara weight

            // Specificerer den tolerence vi tillader, i form af 2 vaerdier til senere tjek.
            lowerbound = receptKomp.getNomNetto() * (1 - (receptKomp.getTolerance() / 100));// Calculate lower bound
            upperbound = receptKomp.getNomNetto() * (1 + (receptKomp.getTolerance() / 100));// Calculate upper bound

            do {
                try {
                    str = socket.rm20("Angiv raavarebatch ID", "ID", ""); // Request user to specify which raavarebatchID
                    if (str.contains("RM20 C") || str.contains("exit")) socket.rm20("Not allowed", "ok?", "");
                    tempRB = rbController.getRaavareBatch(Integer.parseInt(str));//Save raavarebatchID here
                    if (tempRB.getRaavareId() != raavare.getRaavareId()) continue; // Check if the raavare id of the inputted raavarebatch id matches what the recepie requires
                } catch (NumberFormatException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    log.warn("Ikke gyldig vægt");
                    continue;
                } catch (DALException e) {
                    socket.rm20("Databasefejl.", "ok?", "");
                    abort("Databasefejl.", e);
                }

                socket.rm20("RB vaegt: " + tempRB.getMaengde() + "kg.", "Mangler: "+ (receptKomp.getNomNetto() - weight) + "kg.", "");
                try {
                	//Temp weight -> Current weighed, weight -> total weighed so far
                    tempweight = afvej(); // Weigh something
                    while (tempweight - weight > tempRB.getMaengde()) { //  ???
                        log.info("Afvejet mængde overstiger mængden i råvarebatch.");
                        socket.rm20("Afvejet overstiger RB.", "", "Fejl.");
                        tempweight = afvej();
                    }

                    while (upperbound < tempweight) { // Check if weighed amount is greater than what we need
                        log.info("Afvejet mængde overstiger nomnetto.");
                        socket.rm20("Afvejet>nomnetto.", "", "Fejl.");
                        tempweight = afvej();
                    }

                    weight = tempweight;
                    diffWeight = weight - diffWeight; // ??
                    tempRB.setMaengde(tempRB.getMaengde() - diffWeight); //??
                    log.info(tempRB.getRaavareId() + " " + tempRB.getRbId() + " " + tempRB.getMaengde());
                    rbController.updateRaavareBatch(tempRB);

                    tempPBK = new DTOProduktBatchKomp(produktbatch.getPbId(), tempRB.getRbId(), tara, diffWeight, operatoer.getOprId()); //?
                    log.warn(tempPBK.toString());
                    pbkController.createProdBatchKomp(tempPBK);
                    double testDouble = receptKomp.getNomNetto() - weight;
                    if (lowerbound > weight || upperbound < weight) {
                        socket.rm20("Afvejet: "+tempRB.getRbId(), "Mangler: " + testDouble+"kg", "");
                    }
                } catch (IOException e) {
                    abort("IOException.", e);
                } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    socket.rm20("Database fejl.", "ok?", "");
                    abort("Databasefejl.", e);
                }
            } while (lowerbound > weight || upperbound < weight); // While lower bound greater than weight or upper bound lower than weight

            bruttokontrol(tara);
            weight = 0;
            tempweight = 0;
            diffWeight = 0;
            str = null;
        }
    }

    private void emptyWeight() {
        try {
            do {
                socket.rm20("Toem vaegt", "ok?", "");
            } while (socket.readWeight() >= 0.0001);
        } catch (IOException e) {
            abort("IOException", e);
        }
    }

    private DTORaavare retreiveRaavare(int raavareID) {
        IRaavareController rController = RaavareController.getInstance();
        DTORaavare raavare = null;
        try {
            raavare = rController.getRaavare(raavareID);
            socket.rm20(raavare.getRaavareId() + "; " + raavare.getRaavareNavn(), "Bekraeft!", "");
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            socket.rm20("Database fejl.", "ok?", "");
            abort("Database fejl", e);
        }
        return raavare;
    }

    private double retreiveTara() {
        socket.rm20("Placer tara.", "ok?", "");
        try {
            return socket.tarer();
        } catch (IOException e) {
            abort("Fejl ved tara", e);
            return 0;
        }
    }

    private double afvej() throws IOException {
        double tempweight = 0;
        String str;
        do {
            socket.rm20("Se vaegt.", "ok?", "");
            tempweight = socket.readWeight();
            str = socket.rm20("Vaegt ok?", "", tempweight + "kg.");
        } while (str.contains("RM20 C"));
        return tempweight;
    }

    private void bruttokontrol(double tara) {
        boolean passedControl = false;
        double afvejning = 0;

        do {
            socket.rm20("Toem vaegt.", "ok?", "");

            try {
                afvejning = socket.readWeight() * -1;
            } catch (IOException e) {
                abort("Fejl ved læsning af vægt.", e);
            }

            if (Math.abs(afvejning - tara) < 0.00001) {
                passedControl = true;
                socket.rm20("Bruttokontrol godkendt.", "ok?", "");
            } else {
                passedControl = false;
                socket.rm20("", "Bruttokontrol fejlet.", "");
            }
        }
        while (!passedControl);

        try {
            socket.tarer();
        } catch (IOException e) {
            abort("Fejl under tarering.", e);
        }
    }

    private void abort(String msg, Exception e) {
        log.error(msg);
        log.error("System afsluttes.");
        log.error(e.getMessage());
        socket.disconnect();
        System.exit(0);
    }
}
