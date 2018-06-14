package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import connector.MySQLConnector;
import controller.ProduktBatchKompController;
import exception.DALException;
import interfaces.IProduktBatchKompController;
import dto.DTOProduktBatchKomp;

class DAOProduktBatchKomponentTest {
    static IProduktBatchKompController testOperatoerController;
    static DTOProduktBatchKomp initialProdBatchKomp;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        testOperatoerController = ProduktBatchKompController.getInstance();
        initialProdBatchKomp = testOperatoerController.getProduktBatchKomp(1, 1);
    }

    @AfterAll
    static void tearDown() {
        try {
            MySQLConnector.doQuery("CALL deleteProductBatchKomp ('" + 1 + "', '" + 10 + "');");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetProduktBatchKompValid() {
        boolean valid = true;
        try {
            DTOProduktBatchKomp prodBatchKompReturn = testOperatoerController.getProduktBatchKomp(1, 1);
            if (prodBatchKompReturn.getPbId() != initialProdBatchKomp.getPbId()) {
                valid = false;
            }
            if (prodBatchKompReturn.getRbId() != initialProdBatchKomp.getRbId()) {
                valid = false;
            }
            if (prodBatchKompReturn.getNetto() != initialProdBatchKomp.getNetto()) {
                valid = false;
            }
            if (prodBatchKompReturn.getOprId() != initialProdBatchKomp.getOprId()) {
                valid = false;
            }
        } catch (DALException e) {
            valid = false;
        } catch (Exception e) {
            fail("Something went wrong testing a valid product batch komponent");
        }
        assertTrue(valid);
    }

    @Test
    void testGetProduktBatchKompInvalid() {
        boolean valid = true;
        try {
            testOperatoerController.getProduktBatchKomp(9999, 9999);
        } catch (DALException e) {
            valid = false;
        } catch (Exception e) {
            fail("Something went wrong testing a valid product batch komponent");
        }
        assertFalse(valid);
    }

    @Test
    void testGetProduktBatchKompListInt() {
        try {
            List<DTOProduktBatchKomp> list = testOperatoerController.getProduktBatchKomponentList(1);
            assertTrue(list.size() > 0);
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            fail("Something went wrong fecthing product batch komp list in the test with pb id = 1");
        }
    }

    @Test
    void testGetProduktBatchKompList() {
        try {
            List<DTOProduktBatchKomp> list = testOperatoerController.getProduktBatchKomponentList();
            assertTrue(list.size() > 0);
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            fail("Something went wrong fecthing product batch komp list in the test");
        }
    }

    @Test
    void testCreateProduktBatchKomp() {
        try {
            testOperatoerController.createProdBatchKomp(new DTOProduktBatchKomp(1, 6, 1.0, 1.0, 1));
        } catch (Exception e) {
            fail("Something went wrong creating a product batch component");
        }finally {
            try{
                testOperatoerController.deleteProdBatchKomp(1,6);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Test
    void testUpdateProduktBatchKompValid() {
        boolean valid = true;
        try {
            testOperatoerController.updateProdBatchKomp(new DTOProduktBatchKomp(1, 1, 2, 2, 1));
        } catch (DALException e) {
            valid = false;
            fail("Invalid parameters");
        } catch (Exception e) {
            valid = false;
            fail("Something went wrong updating a valid prod batch");
        }
        assertTrue(valid);
    }

    @Test
    void testUpdateProduktBatchKompInvalid() {
        boolean valid = true;
        try {
            testOperatoerController.updateProdBatchKomp(new DTOProduktBatchKomp(99999, 9999, 99, 9999, 1));
        } catch (DALException e) {
            valid = false;
        } catch (Exception e) {
            valid = false;
            fail("Something went wrong updating a invalid product batch");
        }
        assertFalse(valid);
    }

}
