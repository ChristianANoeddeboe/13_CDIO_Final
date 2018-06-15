package test;

import connector.MySQLConnector;
import controller.RaavareController;
import dto.DTORaavare;
import exception.DALException;
import interfaces.IRaavareController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DAORaavareTest {

    private DTORaavare testRaavareDTO;
    private static IRaavareController testRaavareController;

    @BeforeAll
    static void setUp() {
    	testRaavareController  = RaavareController.getInstance();
    }

    @Test
    void getRaavarePositive() {
        try {
            Object actual;
            testRaavareDTO = testRaavareController.getRaavare(1);

            actual = testRaavareDTO.getRaavareId();
            assertEquals(1, actual, "Raavare ID was " + actual);

            actual = testRaavareDTO.getRaavareNavn();
            assertEquals("dej", actual, "Raavare navn was " + actual);

            actual = testRaavareDTO.getLeverandoer();
            assertEquals("Wawelka", actual, "Leverandoer was " + actual);
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            System.out.println("Query could not be resolved.");
            e.printStackTrace();
        }
    }

    @Test
    void getRaavareNegative() {
        int raavareID = -10;
        try {
            testRaavareDTO = testRaavareController.getRaavare(raavareID);
            fail("Raavare existed");
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            if (!e.getMessage().contains("Raavaren " + raavareID + " findes ikke.")) {
                fail(e.getMessage());
            }
        }
    }

    @Test
    void getRaavareList() {
        try {
            assertFalse(testRaavareController.getRaavreList().isEmpty());
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            System.out.println("Query could not be resolved.");
            e.printStackTrace();
        }
    }

    @Test
    void createRaavarePositive() {
        testRaavareDTO = new DTORaavare(999,"Smør", "Arla");
        DTORaavare compare;
        int expectedID;
        try {
            expectedID = 999;
            testRaavareController.createRaavare(testRaavareDTO);
            compare = testRaavareController.getRaavare(expectedID);
            assertEquals(expectedID, compare.getRaavareId(), "Batch id was not " + compare.getRaavareId());
            assertEquals(testRaavareDTO.getRaavareNavn(), compare.getRaavareNavn(), "Raavare navn was not " + compare.getRaavareNavn());
            assertEquals(testRaavareDTO.getLeverandoer(), compare.getLeverandoer(), "Leverandoer was not " + compare.getLeverandoer());
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            fail("Could not create new Raavare");
        }finally {
            try {
                int delID = 999;
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
            testRaavareDTO = testRaavareController.getRaavare(raavareID);
            originalNavn = testRaavareDTO.getRaavareNavn();
            testRaavareController.updateRaavare(new DTORaavare(raavareID,"Tester12",testRaavareDTO.getLeverandoer()));
            testRaavareDTO = testRaavareController.getRaavare(raavareID);
            assertEquals("Tester12", testRaavareDTO.getRaavareNavn());
            testRaavareDTO.setRaavareNavn(originalNavn);
            testRaavareController.updateRaavare(testRaavareDTO);
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
            testRaavareDTO = testRaavareController.getRaavare(raavareID);
            originalLeverandoer = testRaavareDTO.getLeverandoer();
            testRaavareController.updateRaavare(new DTORaavare(raavareID,testRaavareDTO.getRaavareNavn(),"Tester12"));
            testRaavareDTO = testRaavareController.getRaavare(raavareID);
            assertEquals("Tester12", testRaavareDTO.getLeverandoer());
            testRaavareDTO.setLeverandoer(originalLeverandoer);
            testRaavareController.updateRaavare(testRaavareDTO);
        }catch (DALException e) {
            fail("Invalid raavare id: " + raavareID);
        }catch (Exception e) {
            fail("Something went wrong");
        }
    }

    @Test
    public void updateRaavareNegative() {
        try {
            testRaavareController.updateRaavare(new DTORaavare(9999,"test","test"));
            fail("Batch existed.");
        }catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            if (!e.getMessage().contains("No rows updated in \"Raavare\"")) {
                fail(e.getMessage());
            }
        }
    }
}