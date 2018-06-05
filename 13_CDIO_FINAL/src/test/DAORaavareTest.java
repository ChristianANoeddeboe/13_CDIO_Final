package test;

import connector.MySQLConnector;
import dao.DAORaavare;
import exception.DALException;
import dto.DTORaavare;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DAORaavareTest {

    DTORaavare testRaavareDTO;
    DAORaavare testRaavareDAO  = new DAORaavare();

    @BeforeAll
    static void setUp() {
        try { new MySQLConnector(); }
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
        testRaavareDTO = new DTORaavare("Smør", "Arla");
        DTORaavare compare;
        int expectedID;
        List<DTORaavare> testList;
        try {
            testList = testRaavareDAO.getRaavareList();
            expectedID = 0;
            testRaavareDAO.createRaavare(testRaavareDTO);
            compare = testRaavareDAO.getRaavare(expectedID);
            assertEquals(expectedID, compare.getRaavareId(), "Batch id was not " + compare.getRaavareId());
            assertEquals(testRaavareDTO.getRaavareNavn(), compare.getRaavareNavn(), "Raavare navn was not " + compare.getRaavareNavn());
            assertEquals(testRaavareDTO.getLeverandoer(), compare.getLeverandoer(), "Leverandoer was not " + compare.getLeverandoer());
        } catch (DALException e) {
            e.printStackTrace();
            fail("Could not create new Raavare");
        }finally {
            try {
                int delID = 0;
                MySQLConnector.doQuery("CALL deleteRaavare('" + delID + "');");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void updateRaavarePositiveNavn() {
        String originalNavn;
        int raavareID = 1;
        try {
            testRaavareDTO = testRaavareDAO.getRaavare(raavareID);
            originalNavn = testRaavareDTO.getRaavareNavn();
            testRaavareDAO.updateRaavare(new DTORaavare(raavareID,"Tester12",testRaavareDTO.getLeverandoer()));
            testRaavareDTO = testRaavareDAO.getRaavare(raavareID);
            assertEquals("Tester12", testRaavareDTO.getRaavareNavn());
            testRaavareDTO.setRaavareNavn(originalNavn);
            testRaavareDAO.updateRaavare(testRaavareDTO);
        }catch (DALException e) {
            fail("Invalid raavare id: " + raavareID);
        }catch (Exception e) {
            fail("Something went wrong");
        }
    }

    @Test
    public void updateRaavarePositiveLenverandoer() {
        String originalLeverandoer;
        int raavareID = 1;
        try {
            testRaavareDTO = testRaavareDAO.getRaavare(raavareID);
            originalLeverandoer = testRaavareDTO.getLeverandoer();
            testRaavareDAO.updateRaavare(new DTORaavare(raavareID,testRaavareDTO.getRaavareNavn(),"Tester12"));
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
            testRaavareDAO.updateRaavare(new DTORaavare(9999,"test","test"));
            fail("Batch existed.");
        }catch (DALException e) {
            if (!e.getMessage().contains("No rows updated in \"Raavare\"")) {
                fail(e.getMessage());
            }
        }
    }
}