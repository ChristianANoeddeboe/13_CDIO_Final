package test;

import connector.MySQLConnector;
import controller.RaavareBatchController;
import exception.DALException;
import dto.DTORaavareBatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DAORaavareBatchTest {

    private RaavareBatchController testRaavareBatchController;
    private DTORaavareBatch testRaavareBatchDTO;

    @BeforeEach
    public void setUp() throws Exception {
        testRaavareBatchController =  RaavareBatchController.getInstance();
    }

    @Test
    public void getRaavareBatchPositive() {
        try {
            testRaavareBatchDTO = testRaavareBatchController.getRaavareBatch(1);
            assertEquals(1, testRaavareBatchDTO.getRbId());
            assertEquals(1, testRaavareBatchDTO.getRaavareId());
            assertEquals(102, testRaavareBatchDTO.getMaengde(), 0.5);
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            System.out.println("Query could not be resolved.");
            e.printStackTrace();
        }
    }

    @Test
    public void getRaavareBatchNegative() {
        int rbId = -1;
        try {
            testRaavareBatchDTO = testRaavareBatchController.getRaavareBatch(rbId);
            fail(testRaavareBatchDTO.toString());
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            if (!e.getMessage().contains("RaavareBatchen " + rbId + " findes ikke.")) {
                fail(e.getMessage());
            }
        }
    }

    @Test
    public void getRaavareBatchList() {
        try {
            assertFalse(testRaavareBatchController.getRaavareBatchList().isEmpty());

        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            System.out.println("Query could not be resolved.");
            e.printStackTrace();
        }
    }

    @Test
    public void createRaavareBatchPositive() {
        testRaavareBatchDTO = new DTORaavareBatch(999,1, 10.5);
        DTORaavareBatch compare;
        int expectedID;
        try {
            testRaavareBatchController.createRaavareBatch(testRaavareBatchDTO);
            expectedID = 999;
            compare = testRaavareBatchController.getRaavareBatch(expectedID);
            assertEquals(expectedID, compare.getRbId(), "Batch id was not same");
            System.out.println(compare.toString());
            System.out.println((testRaavareBatchDTO.toString()));
            assertEquals(testRaavareBatchDTO.getRaavareId(), compare.getRaavareId(), "raavare id was not same");
            assertEquals(testRaavareBatchDTO.getMaengde(), compare.getMaengde(), "Maengde was not same");
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            fail("Could not create new Raavare Batch");
        }finally {
            try {
            	testRaavareBatchController.getRaavareBatchList();
                MySQLConnector.doQuery("CALL deleteRaavareBatch('" + 999 + "');");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void updateRaavareBatchPositive() {
        double originalmaengde;
        try {
            testRaavareBatchDTO = testRaavareBatchController.getRaavareBatch(1);
            originalmaengde = testRaavareBatchDTO.getMaengde();
            testRaavareBatchController.updateRaavareBatch(new DTORaavareBatch(1, testRaavareBatchDTO.getRaavareId(), 1000));
            testRaavareBatchDTO = testRaavareBatchController.getRaavareBatch(1);
            assertEquals(1000, testRaavareBatchDTO.getMaengde());
            testRaavareBatchDTO.setMaengde(originalmaengde);
            testRaavareBatchController.updateRaavareBatch(testRaavareBatchDTO);
        } catch (DALException e) {
            fail("Invalid raavare id");
        } catch (Exception e) {
            fail("Something went wrong");
        }
    }

    @Test
    public void updateRaavareBatchNegative() {
        try {
            testRaavareBatchController.updateRaavareBatch(new DTORaavareBatch(9999, 1, 100));
            fail("Batch existed.");
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            if (!e.getMessage().contains("No rows updated in \"Raavare batch\"")) {
                fail(e.getMessage());
            }
        }
    }
}