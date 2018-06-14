package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import connector.MySQLConnector;
import controller.ProduktBatchController;
import exception.DALException;
import interfaces.IProduktBatchController;
import dto.DTOProduktBatch;
import dto.Status;

class DAOProduktBatchtest {
    static IProduktBatchController prodBatch;
    static DTOProduktBatch initialProdBatch;

    /**
     * @throws Exception
     */
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        prodBatch = ProduktBatchController.getInstance();
        initialProdBatch = prodBatch.getProduktBatch(1);
    }
    /**
     * Testing a valid prod batch
     */
    @Test
    void testGetProduktBatchValid() {
        boolean valid = true;
        try {
            DTOProduktBatch prodBatchReturn = prodBatch.getProduktBatch(1);
            if (prodBatchReturn.getPbId() != initialProdBatch.getPbId()) {
                valid = false;
            }
            if (prodBatchReturn.getReceptId() != initialProdBatch.getReceptId()) {
                valid = false;
            }
            if (prodBatchReturn.getStatus() != initialProdBatch.getStatus()) {
                valid = false;
            }
        } catch (DALException e) {
            valid = false;
        } catch (Exception e) {
            fail("Something went wrong testing a valid product batch");
        }
        assertTrue(valid);
    }

    /**
     * Testing a invalid prod batch
     */
    @Test
    void testGetProduktBatchInvalid() {
        boolean valid = true;
        try {
			prodBatch.getProduktBatch(1234);
        } catch (DALException e) {
            valid = false;
        } catch (Exception e) {
            fail("something went wrong testing an invalid product batch");
        }
        assertFalse(valid);
    }

    /**
     * Testing that we can get more than one product batch
     */
    @Test
    void testGetProduktBatchList() {
        try {
            List<DTOProduktBatch> list = prodBatch.getProduktBatchList();
            assertTrue(list.size() > 0);
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            fail("Something went wrong fecthing product batch list in the test");
        }
    }

    /**
     *
     */
    @Test
    void testCreateProduktBatch() {
        try {
            prodBatch.createProduktBatch(new DTOProduktBatch(1000, Status.Igang, 1));
        } catch (Exception e) {
            fail("Something went wrong creating a product batch");
        }finally {
            try {
                int delID = prodBatch.getProduktBatchList().get(prodBatch.getProduktBatchList().size()-1).getPbId();
                MySQLConnector.doQuery("CALL deleteProductBatch('" + delID + "');");
            } catch (Exception e) {
                 e.printStackTrace();
            }
        }
    }

    /**
     * Updating a valid product batch
     */
    @Test
    void testUpdateProduktBatchValid() {
        boolean valid = true;
        try {
            prodBatch.updateProduktBatch(new DTOProduktBatch(1, Status.Igang, 1));
        } catch (DALException e) {
            valid = false;
            fail("Invalid pb id");
        } catch (Exception e) {
            valid = false;
            fail("Something went wrong updating a valid product batch");
        }
        assertTrue(valid);
    }

    /**
     * Updating a invalid product batch
     */
    @Test
    void testUpdateProduktBatchInValid() {
        boolean valid = true;
        try {
            prodBatch.updateProduktBatch(new DTOProduktBatch(9999, Status.Igang, 1));
        } catch (DALException e) {
            valid = false;
        } catch (Exception e) {
            valid = false;
            fail("Something went wrong updating a invalid product batch");
        }
        assertFalse(valid);
    }

}
