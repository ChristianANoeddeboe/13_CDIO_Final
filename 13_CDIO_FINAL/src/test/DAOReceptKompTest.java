package test;

import connector.MySQLConnector;
import dao.DAORecept;
import dao.DAOReceptKomp;
import dto.DTORecept;
import exception.DALException;
import dto.DTOReceptKomp;
import interfaces.IDAORecept;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DAOReceptKompTest {

    DAOReceptKomp receptkompDAO;
    DTOReceptKomp receptKompDTO;

    @BeforeEach
    void setUp() {
        try {
            new MySQLConnector();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        receptkompDAO = new DAOReceptKomp();
    }

    @Test
    void getReceptKompPositiv() {
        try {
            Object actual;
            receptKompDTO = receptkompDAO.getReceptKomp(1, 1);

            actual = receptKompDTO.getReceptId();
            assertEquals(1, actual, "Recept ID was " + actual);

            actual = receptKompDTO.getRaavareId();
            assertEquals(1, actual, "Raavare ID was " + actual);

            actual = receptKompDTO.getNomNetto();
            assertEquals(10.0, actual, "NomNetto was " + actual);

            actual = receptKompDTO.getTolerance();
            assertEquals(10.0, actual, "Tolerance was " + actual);
        } catch (DALException e) {
            System.out.println("Query could not be resolved.");
            e.printStackTrace();
        }
    }

    @Test
    void getReceptKompNegativ() {
        int receptID = -10;
        int raavareID = 5;
        try {
            receptKompDTO = receptkompDAO.getReceptKomp(receptID, raavareID);
            fail("Recept Komp existed");
        } catch (DALException e) {
            if (!e.getMessage().contains("Recept komponenet med recept ID " + receptID + " og raavare ID " + raavareID + " findes ikke.")) {
                fail(e.getMessage());
            }
        }
    }

    @Test
    void getReceptKompList() {
        try {
            assertFalse(receptkompDAO.getReceptKompList().isEmpty());
        } catch (DALException e) {
            System.out.println("Query could not be resolved.");
            e.printStackTrace();
        }

    }

    @Test
    void getReceptKompList1() {
        try {
            assertFalse(receptkompDAO.getReceptKompList(1).isEmpty());
        } catch (DALException e) {
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
            receptkompDAO.createReceptKomp(receptKompDTO);

            compare = receptkompDAO.getReceptKomp(receptKompDTO.getReceptId(), receptKompDTO.getRaavareId());

            assertEquals(receptKompDTO.getReceptId(), compare.getReceptId(), "Recept ID was not " + compare.getReceptId());
            assertEquals(receptKompDTO.getRaavareId(), compare.getRaavareId(), "Raavare ID was not " + compare.getRaavareId());
            assertEquals(receptKompDTO.getNomNetto(), compare.getNomNetto(), "Nom Netto was not " + compare.getNomNetto());
            assertEquals(receptKompDTO.getTolerance(), compare.getTolerance(), "Tolerance was not " + compare.getTolerance());

        } catch (DALException e) {
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
            receptKompDTO = receptkompDAO.getReceptKomp(receptID, raavareID);
            originalNetto = receptKompDTO.getNomNetto();
            receptKompDTO.setNomNetto(100);
            receptkompDAO.updateReceptKomp(receptKompDTO);
            receptKompDTO = receptkompDAO.getReceptKomp(receptID, raavareID);
            assertEquals(100.0, receptKompDTO.getNomNetto());
            receptKompDTO.setNomNetto(originalNetto);
            receptkompDAO.updateReceptKomp(receptKompDTO);
        } catch (DALException e) {
            fail("Invalid recept or raavare id: " + receptID + ", " + raavareID);
        } catch (Exception e) {
            fail("Something went wrong");
        }
    }

    @Test
    void updateReceptKompNegativ() {
        try {
            receptkompDAO.updateReceptKomp(new DTOReceptKomp(9999, 9999, 1, 1));
            fail("Batch existed.");
        } catch (DALException e) {
            if (!e.getMessage().contains("No rows updated in \"Recept komponent\"")) {
                fail(e.getMessage());
            }
        }
    }
}