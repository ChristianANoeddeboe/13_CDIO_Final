package test01917;

import connector01917.Connector;
import daoimpl01917.MySQLRaavareDAO;
import daointerfaces01917.DALException;
import dto01917.RaavareDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySQLRaavareDAOTest {

    RaavareDTO testRaavareDTO;
    MySQLRaavareDAO testRaavareDAO  = new MySQLRaavareDAO();

    @BeforeAll
    static void setUp() {
        try { new Connector(); }
        catch (InstantiationException e) { e.printStackTrace(); }
        catch (IllegalAccessException e) { e.printStackTrace(); }
        catch (ClassNotFoundException e) { e.printStackTrace(); }
        catch (SQLException e) { e.printStackTrace(); }
    }

    @Test
    void getRaavarePositive() {
        try {
            Object actual;
            testRaavareDTO = testRaavareDAO.getRaavare(1);

            actual = testRaavareDTO.getRaavareId();
            assertEquals(1, actual, "Raavare ID was " + actual);

            actual = testRaavareDTO.getRaavareNavn();
            assertEquals("dej", actual, "Raavare navn was " + actual);

            actual = testRaavareDTO.getLeverandoer();
            assertEquals("Wawelka", actual, "Leverandoer was " + actual);
        } catch (DALException e) {
            System.out.println("Query could not be resolved.");
            e.printStackTrace();
        }
    }

    @Test
    void getRaavareNegative() {
        int raavareID = -10;
        try {
            testRaavareDTO = testRaavareDAO.getRaavare(raavareID);
            fail("Raavare existed");
        } catch (DALException e) {
            if (!e.getMessage().contains("Raavaren " + raavareID + " findes ikke.")) {
                fail(e.getMessage());
            }
        }
    }

    @Test
    void getRaavareList() {
        try {
            assertFalse(testRaavareDAO.getRaavareList().isEmpty());
        } catch (DALException e) {
            System.out.println("Query could not be resolved.");
            e.printStackTrace();
        }
    }

    @Test
    void createRaavarePositive() {
        testRaavareDTO = new RaavareDTO("Smør", "Arla");
        RaavareDTO compare;
        int expectedID;
        List<RaavareDTO> testList;
        try {
            testList = testRaavareDAO.getRaavareList();
            expectedID = testList.get(testList.size() - 1).getRaavareId() + 1;
            testRaavareDAO.createRaavare(testRaavareDTO);
            compare = testRaavareDAO.getRaavare(expectedID);
            assertEquals(expectedID, compare.getRaavareId(), "Batch id was not " + compare.getRaavareId());
            assertEquals(testRaavareDTO.getRaavareNavn(), compare.getRaavareNavn(), "Raavare navn was not " + compare.getRaavareNavn());
            assertEquals(testRaavareDTO.getLeverandoer(), compare.getLeverandoer(), "Leverandoer was not " + compare.getLeverandoer());
        } catch (DALException e) {
            e.printStackTrace();
            fail("Could not create new Raavare");
        }
    }

    @Test
    public void updateRaavarePositive() {
        String originalLeverandoer;
        int raavareID = 1;
        try {
            testRaavareDTO = testRaavareDAO.getRaavare(raavareID);
            originalLeverandoer = testRaavareDTO.getLeverandoer();
            testRaavareDAO.updateRaavare(new RaavareDTO(raavareID,testRaavareDTO.getRaavareNavn(),"Tester12"));
            testRaavareDTO = testRaavareDAO.getRaavare(raavareID);
            assertEquals("Tester12", testRaavareDTO.getLeverandoer());
            testRaavareDTO.setLeverandoer(originalLeverandoer);
            testRaavareDAO.updateRaavare(testRaavareDTO);
        }catch (DALException e) {
            fail("Invalid raavare id: " + raavareID);
        }catch (Exception e) {
            fail("Something went wrong");
        }
    }

    @Test
    public void updateRaavareNegative() {
        try {
            testRaavareDAO.updateRaavare(new RaavareDTO(9999,"test","test"));
            fail("Batch existed.");
        }catch (DALException e) {
            if (!e.getMessage().contains("No rows updated in \"Raavare\"")) {
                fail(e.getMessage());
            }
        }
    }
}