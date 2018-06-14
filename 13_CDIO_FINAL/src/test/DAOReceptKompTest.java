package test;

import connector.MySQLConnector;
import controller.ReceptKompController;
import dao.DAORecept;
import dto.DTORecept;
import dto.DTOReceptKomp;
import exception.DALException;
import interfaces.IDAORecept;
import interfaces.IReceptKompController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DAOReceptKompTest {

    IReceptKompController receptKompController;
    DTOReceptKomp receptKompDTO;

    @BeforeEach
    void setUp() {
        receptKompController = ReceptKompController.getInstance();
    }

    @Test
    void getReceptKompPositiv() {
        try {
            Object actual;
            receptKompDTO = receptKompController.getReceptKomp(1, 1);

            actual = receptKompDTO.getReceptId();
            assertEquals(1, actual, "Recept ID was " + actual);

            actual = receptKompDTO.getRaavareId();
            assertEquals(1, actual, "Raavare ID was " + actual);

            actual = receptKompDTO.getNomNetto();
            assertEquals(10.0, actual, "NomNetto was " + actual);

            actual = receptKompDTO.getTolerance();
            assertEquals(10.0, actual, "Tolerance was " + actual);
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            System.out.println("Query could not be resolved.");
            e.printStackTrace();
        }
    }

    @Test
    void getReceptKompNegativ() {
        int receptID = -10;
        int raavareID = 5;
        try {
            receptKompDTO = receptKompController.getReceptKomp(receptID, raavareID);
            fail("Recept Komp existed");
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            if (!e.getMessage().contains("Recept komponenet med recept ID " + receptID + " og raavare ID " + raavareID + " findes ikke.")) {
                fail(e.getMessage());
            }
        }
    }

    @Test
    void getReceptKompList() {
        try {
            assertFalse(receptKompController.getReceptKompList().isEmpty());
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            System.out.println("Query could not be resolved.");
            e.printStackTrace();
        }

    }

    @Test
    void getReceptKompList1() {
        try {
            assertFalse(receptKompController.getReceptKompList(1).isEmpty());
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            System.out.println("Query could not be resolved.");
            e.printStackTrace();
        }
    }

    @Test
    void createReceptKomp() {
        int last_recept_id = 0, last_recept_index = 0, test_recept_id = 1;
        DTOReceptKomp compare;
        IDAORecept daoRecept = new DAORecept();
        try {
            // Creating a recept we can use for testing.
            last_recept_index = daoRecept.getReceptList().size() - 1;
            last_recept_id = daoRecept.getReceptList().get(last_recept_index).getReceptId();
            test_recept_id = last_recept_id + 1;
            DTORecept dtoRecept = new DTORecept(test_recept_id, "test");
            daoRecept.createRecept(dtoRecept);

            // Now we can create a recept kompoennt, without destroy current data
            receptKompDTO = new DTOReceptKomp(test_recept_id, 1, 10, 10.0);
            receptKompController.createReceptKomp(receptKompDTO);

            compare = receptKompController.getReceptKomp(receptKompDTO.getReceptId(), receptKompDTO.getRaavareId());

            assertEquals(receptKompDTO.getReceptId(), compare.getReceptId(), "Recept ID was not " + compare.getReceptId());
            assertEquals(receptKompDTO.getRaavareId(), compare.getRaavareId(), "Raavare ID was not " + compare.getRaavareId());
            assertEquals(receptKompDTO.getNomNetto(), compare.getNomNetto(), "Nom Netto was not " + compare.getNomNetto());
            assertEquals(receptKompDTO.getTolerance(), compare.getTolerance(), "Tolerance was not " + compare.getTolerance());

        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            fail("Could not create new Raavare");
        } finally {
            try {
                MySQLConnector.doQuery("CALL deleteReceptKomp('" + test_recept_id + "','" + 1 + "');");
                MySQLConnector.doQuery("CALL deleteRecept('" + test_recept_id + "');");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void updateReceptKompPositiv() {
        double originalNetto;
        int raavareID = 1;
        int receptID = 1;
        try {
            receptKompDTO = receptKompController.getReceptKomp(receptID, raavareID);
            originalNetto = receptKompDTO.getNomNetto();
            receptKompDTO.setNomNetto(100);
            receptKompController.updateReceptKomp(receptKompDTO);
            receptKompDTO = receptKompController.getReceptKomp(receptID, raavareID);
            assertEquals(100.0, receptKompDTO.getNomNetto());
            receptKompDTO.setNomNetto(originalNetto);
            receptKompController.updateReceptKomp(receptKompDTO);
        } catch (DALException e) {
            fail("Invalid recept or raavare id: " + receptID + ", " + raavareID);
        } catch (Exception e) {
            fail("Something went wrong");
        }
    }

    @Test
    void updateReceptKompNegativ() {
        try {
            receptKompController.updateReceptKomp(new DTOReceptKomp(9999, 9999, 1, 1));
            fail("Batch existed.");
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            if (!e.getMessage().contains("No rows updated in \"Recept komponent\"")) {
                fail(e.getMessage());
            }
        }
    }
}