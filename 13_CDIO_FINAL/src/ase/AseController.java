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

    /**
     * Opretter forbindelse mellem vaegt, system og database, samt paabegynder afvejningsprocessen.
     */
    public void run() {
        List<DTOReceptKomp> receptkompList = null;
        DTOProduktBatch pb = null;

        socket.connect();

        try {
            MySQLConnector.getInstance().setAutoCommit(false);
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
        if (operatoer == null) {
            log.info("Cancel: operatør.");
            socket.disconnect();
            return;
        }

        pb = getProduktbatch();
        if (pb == null) {
            log.info("Cancel: Produktbatch.");
            socket.disconnect();
            return;
        }

        if (!opdaterPbStatus(pb)) {
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
        socket.rm20("Afvejning godkendt.", "", "");
        socket.disconnect();
    }

    private DTOOperatoer validerOperatoer() {
        log.info("Hent operatør.");
        OperatoerController controller = OperatoerController.getInstance();
        int operatorId;
        DTOOperatoer operatoer = null;
        String str = null;

        while (true) {
            try {
                str = socket.rm20("Input operator ID", "ID", "");
                if (str.contains("C") || str.contains("exit")) return null;

                operatorId = Integer.parseInt(str);
                operatoer = controller.getOperatoer(operatorId);
                str = socket.rm20(operatoer.initials(operatoer.getFornavn() + " " + operatoer.getEfternavn()), "", "Confirm.");
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
        ProduktBatchController pbcontroller = ProduktBatchController.getInstance();
        ReceptController rcontroller = ReceptController.getInstance();
        String str = null;
        while (true) {
            try {
                String batchID = socket.rm20("Input batch ID", "1-9999999", "");
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
                str = socket.rm20(recept.getReceptNavn(), "", "Confirm.");
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
            socket.rm20("Status: ikke klar.", "", "");
            return false;
        }else if(pb.getStatus() != Status.Afsluttet){
            socket.rm20("Status: Afsluttet.", "", "");
            return false;
        }else {
            pb.setStatus(Status.Igang);
            ProduktBatchController pbcontroller = ProduktBatchController.getInstance();
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
        ReceptKompController controller = ReceptKompController.getInstance();
        double netto;
        List<DTOReceptKomp> list = null;
        try {
            list = controller.getReceptKompList(pb.getReceptId());
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            socket.rm20("receptkomp ikke fudnet.", "", "");
            socket.rm20("System afsluttes.", "", "");
            log.error(e.getMessage());
            return null;
        }

        for (DTOReceptKomp rk : list) {
            netto = rk.getNomNetto() * (1 + (rk.getTolerance() / 100));
            if (netto > getRaavareMaengde(rk.getRaavareId())) {
                socket.rm20("Ikke nok materiale.", "", "");
                socket.rm20("System afsluttes.", "", "");
                log.error("Ikke nok materiale.");
                return null;
            }
        }
        return list;
    }

    private double getRaavareMaengde(int raavareId) {
        RaavareBatchController controller = RaavareBatchController.getInstance();
        double maengde = 0;
        try {
            List<DTORaavareBatch> raavareBatches = controller.getRaavareBatchList(raavareId);
            for (DTORaavareBatch rb : raavareBatches) {
                maengde += rb.getMaengde();
            }
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            socket.rm20("Raavarebatch ikke fundet", "", "");
            socket.rm20("System afsluttes", "", "");
            abort("Raavarebatch ikke fundet.", e);
        }
        return maengde;
    }

    private void afvejning(List<DTOReceptKomp> receptkompList, DTOProduktBatch produktbatch, DTOOperatoer operatoer) {
        double lowerbound, upperbound, weight = 0, tara = 0, diffWeight = 0, tempweight = 0;
        DTORaavare raavare = null;
        DTOProduktBatchKomp tempPBK = null;
        DTORaavareBatch tempRB = null;
        ProduktBatchKompController pbkController = ProduktBatchKompController.getInstance();
        RaavareBatchController rbController = RaavareBatchController.getInstance();
        List<DTORaavareBatch> raavareBatches = null;
        String str = null;

        for (DTOReceptKomp receptKomp : receptkompList) {

            emptyWeight();
            raavare = retreiveRaavare(receptKomp.getRaavareId());
            tara = retreiveTara();

            // Specificerer den tolerence vi tillader, i form af 2 vaerdier til senere tjek.
            lowerbound = receptKomp.getNomNetto() * (1 - (receptKomp.getTolerance() / 100));
            upperbound = receptKomp.getNomNetto() * (1 + (receptKomp.getTolerance() / 100));

            do {
                try {
                    str = socket.rm20("Angiv raavarebatch ID", "", "");
                    if (str.contains("RM20 C") || str.contains("exit")) socket.rm20("Not allowed", "", "");
                    tempRB = rbController.getRaavareBatch(Integer.parseInt(str));
                    if (tempRB.getRaavareId() != raavare.getRaavareId()) continue;
                } catch (NumberFormatException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    log.warn("Ikke gyldig vægt");
                    continue;
                } catch (DALException e) {
                    socket.rm20("Databasefejl.", "", "");
                    abort("Databasefejl.", e);
                }

                socket.rm20("Current RB: " + tempRB.getMaengde() + "kg.", "", (receptKomp.getNomNetto() - weight) + "kg.");
                try {

                    tempweight = afvej();
                    while (tempweight - weight > tempRB.getMaengde()) {
                        log.info("Afvejet mængde overstiger mængden i råvarebatch.");
                        socket.rm20("Afvejet overstiger RB.", "", "Fejl.");
                        tempweight = afvej();
                        continue;
                    }

                    while (upperbound < tempweight) {
                        log.info("Afvejet mængde overstiger nomnetto.");
                        socket.rm20("Afvejet>nomnetto.", "", "Fejl.");
                        tempweight = afvej();
                        continue;
                    }

                    weight = tempweight;
                    diffWeight = weight - diffWeight;

                    tempRB.setMangde(tempRB.getMaengde() - diffWeight);
                    log.info(tempRB.getRaavareId() + " " + tempRB.getRbId() + " " + tempRB.getMaengde());
                    rbController.updateRaavareBatch(tempRB);

                    tempPBK = new DTOProduktBatchKomp(produktbatch.getPbId(), tempRB.getRbId(), tara, diffWeight, operatoer.getOprId());
                    log.warn(tempPBK.toString());
                    pbkController.createProdBatchKomp(tempPBK);

                    if (lowerbound > weight || upperbound < weight) {
                        socket.rm20(tempRB.getRbId() + " afvejet, mangler: " + (receptKomp.getNomNetto() - weight) + "kg.", "", "");
                    }
                } catch (IOException e) {
                    abort("IOException.", e);
                } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    socket.rm20("Database fejl.", "", "");
                    abort("Databasefejl.", e);
                }
            } while (lowerbound > weight || upperbound < weight);

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
                socket.rm20("Toem vaegt", "", "");
            } while (socket.readWeight() >= 0.0001);
        } catch (IOException e) {
            abort("IOException", e);
        }
    }

    private DTORaavare retreiveRaavare(int raavareID) {
        RaavareController rController = RaavareController.getInstance();
        DTORaavare raavare = null;
        try {
            raavare = rController.getRaavare(raavareID);
            socket.rm20(raavare.getRaavareId() + "; " + raavare.getRaavareNavn(), "", "");
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            socket.rm20("Database fejl.", "", "");
            abort("Database fejl", e);
        }
        return raavare;
    }

    private double retreiveTara() {
        socket.rm20("Placer tara.", "", "");
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
            socket.rm20("Se vaegt.", "", "");
            tempweight = socket.readWeight();
            str = socket.rm20("Vaegt ok?", "", tempweight + "kg.");
        } while (str.contains("RM20 C"));
        return tempweight;
    }

    private void bruttokontrol(double tara) {
        boolean passedControl = false;
        double afvejning = 0;

        do {
            socket.rm20("Toem vaegt.", "", "");

            try {
                afvejning = socket.readWeight() * -1;
            } catch (IOException e) {
                abort("Fejl ved læsning af vægt.", e);
            }

            if (Math.abs(afvejning - tara) < 0.00001) {
                passedControl = true;
                socket.rm20("Bruttokontrol godkendt.", "", "");
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
