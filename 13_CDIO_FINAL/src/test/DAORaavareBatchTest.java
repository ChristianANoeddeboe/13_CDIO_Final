package test;

import connector.MySQLConnector;
import dao.DAORaavareBatch;
import exception.DALException;
import dto.DTORaavareBatch;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class DAORaavareBatchTest {

    private DAORaavareBatch testRaavareBatchDAO;
    private DTORaavareBatch testRaavareBatchDTO;

    @BeforeEach
    public void setUp() throws Exception {
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

        testRaavareBatchDAO = new DAORaavareBatch();
    }

    @Test
    public void getRaavareBatchPositive() {
        try {
            testRaavareBatchDTO = testRaavareBatchDAO.getRaavareBatch(1);
            assertEquals(1, testRaavareBatchDTO.getRbId());
            assertEquals(1, testRaavareBatchDTO.getRaavareId());
            assertEquals(1000, testRaavareBatchDTO.getMaengde(), 0.5);
        } catch (DALException e) {
            System.out.println("Query could not be resolved.");
            e.printStackTrace();
        }
    }

    @Test
    public void getRaavareBatchNegative() {
        int rbId = -1;
        try {
            testRaavareBatchDTO = testRaavareBatchDAO.getRaavareBatch(rbId);
            fail(testRaavareBatchDTO.toString());
        } catch (DALException e) {
            if (!e.getMessage().contains("RaavareBatchen " + rbId + " findes ikke.")) {
                fail(e.getMessage());
            }
        }
    }

    @Test
    public void getRaavareBatchList() {
        try {
            assertFalse(testRaavareBatchDAO.getRaavareBatchList().isEmpty());

        } catch (DALException e) {
            System.out.println("Query could not be resolved.");
            e.printStackTrace();
        }
    }

    @Test
    public void createRaavareBatchPositive() {
        testRaavareBatchDTO = new DTORaavareBatch(1, 10.5);
        DTORaavareBatch compare;
        int expectedID;
        List<DTORaavareBatch> testList;
        try {
            testRaavareBatchDAO.createRaavareBatch(testRaavareBatchDTO);
            testList = testRaavareBatchDAO.getRaavareBatchList();
            expectedID = testList.get((testList.size()-1 )).getRbId();
            compare = testRaavareBatchDAO.getRaavareBatch(expectedID);
            assertEquals(expectedID, compare.getRbId(), "Batch id was not same");
            assertEquals(testRaavareBatchDTO.getRaavareId(), compare.getRaavareId(), "raavare id was not same");
            assertEquals(testRaavareBatchDTO.getMaengde(), compare.getMaengde(), "Maengde was not same");
        } catch (DALException e) {
            e.printStackTrace();
            fail("Could not create new Raavare Batch");
        }finally {
            try {
                testList = testRaavareBatchDAO.getRaavareBatchList();
                expectedID = testList.get((testList.size()-1 )).getRbId();
                MySQLConnector.doQuery("CALL deleteRaavareBatch('" + expectedID + "');");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void updateRaavareBatchPositive() {
        double originalmaengde;
        try {
            testRaavareBatchDTO = testRaavareBatchDAO.getRaavareBatch(1);
            originalmaengde = testRaavareBatchDTO.getMaengde();
            testRaavareBatchDAO.updateRaavareBatch(new DTORaavareBatch(1, testRaavareBatchDTO.getRaavareId(), 1000));
            testRaavareBatchDTO = testRaavareBatchDAO.getRaavareBatch(1);
            assertEquals(1000, testRaavareBatchDTO.getMaengde());
            testRaavareBatchDTO.setMaengde(originalmaengde);
            testRaavareBatchDAO.updateRaavareBatch(testRaavareBatchDTO);
        } catch (DALException e) {
            fail("Invalid raavare id");
        } catch (Exception e) {
            fail("Something went wrong");
        }
    }

    @Test
    public void updateRaavareBatchNegative() {
        try {
            testRaavareBatchDAO.updateRaavareBatch(new DTORaavareBatch(9999, 1, 100));
            fail("Batch existed.");
        } catch (DALException e) {
            if (!e.getMessage().contains("No rows updated in \"Raavare batch\"")) {
                fail(e.getMessage());
            }
        }
    }
}