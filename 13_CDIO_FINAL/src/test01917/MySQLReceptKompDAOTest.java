package test01917;

import connector01917.Connector;
import daoimpl01917.MySQLReceptKompDAO;
import daointerfaces01917.DALException;
import dto01917.ReceptKompDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySQLReceptKompDAOTest {

    MySQLReceptKompDAO receptkompDAO;
    ReceptKompDTO receptKompDTO;

    @BeforeEach
    void setUp() {
        try { new Connector(); }
        catch (InstantiationException e) { e.printStackTrace(); }
        catch (IllegalAccessException e) { e.printStackTrace(); }
        catch (ClassNotFoundException e) { e.printStackTrace(); }
        catch (SQLException e) { e.printStackTrace(); }
        receptkompDAO = new MySQLReceptKompDAO();
    }

    @Test
    void getReceptKompPositiv() {
        try {
            Object actual;
            receptKompDTO = receptkompDAO.getReceptKomp(1,1);

            actual = receptKompDTO.getReceptId();
            assertEquals(1, actual, "Recept ID was " + actual);

            actual = receptKompDTO.getRaavareId();
            assertEquals(1, actual, "Raavare ID was " + actual);

            actual = receptKompDTO.getNomNetto();
            assertEquals(10.0, actual, "NomNetto was " + actual);

            actual = receptKompDTO.getTolerance();
            assertEquals(0.1, actual, "Tolerance was " + actual);
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
            if (!e.getMessage().contains("Recept komponenet med recept ID "+receptID+" og raavare ID "+raavareID+" findes ikke.")) {
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
        int lastid;
        ReceptKompDTO compare;
        try {
            lastid = receptkompDAO.getReceptKompList().size()-1;
            lastid = receptkompDAO.getReceptKompList().get(lastid).getReceptId();
            receptKompDTO = new ReceptKompDTO(lastid+1,1,10,0.1);
            receptkompDAO.createReceptKomp(receptKompDTO);
            compare = receptkompDAO.getReceptKomp(receptKompDTO.getReceptId(),receptKompDTO.getRaavareId());
            assertEquals(receptKompDTO.getReceptId(), compare.getReceptId(), "Recept ID was not "+ compare.getReceptId());
            assertEquals(receptKompDTO.getRaavareId(), compare.getRaavareId(), "Raavare ID was not " + compare.getRaavareId());
            assertEquals(receptKompDTO.getNomNetto(), compare.getNomNetto(), "Nom Netto was not " + compare.getNomNetto());
            assertEquals(receptKompDTO.getTolerance(), compare.getTolerance(), "Tolerance was not " + compare.getTolerance());
        } catch (DALException e) {
            e.printStackTrace();
            fail("Could not create new Raavare");
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
        }catch (DALException e) {
            fail("Invalid recept or raavare id: " + receptID + ", " + raavareID);
        }catch (Exception e) {
            fail("Something went wrong");
        }
    }

    @Test
    void updateReceptKompNegativ() {
        try {
            receptkompDAO.updateReceptKomp(new ReceptKompDTO(9999, 9999, 1, 1));
            fail("Batch existed.");
        }catch (DALException e) {
            if (!e.getMessage().contains("No rows updated in \"Recept komponent\"")) {
                fail(e.getMessage());
            }
        }
    }
}