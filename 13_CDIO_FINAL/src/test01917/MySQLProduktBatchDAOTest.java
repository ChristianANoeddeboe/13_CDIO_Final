/**
 * 
 */
package test01917;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import connector.MySQLConnector;
import dao.DAOProduktBatch;
import exception.DALException;
import dto.DTOProduktBatch;
import dto.DTOProduktBatch.Status;

class MySQLProduktBatchDAOTest {
	static DAOProduktBatch prodBatch;
	static DTOProduktBatch initialProdBatch;
	/**
	 * @throws Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		try { new MySQLConnector(); }
		catch (InstantiationException e) { e.printStackTrace(); }
		catch (IllegalAccessException e) { e.printStackTrace(); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		catch (SQLException e) { e.printStackTrace(); }
		prodBatch = new DAOProduktBatch();
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
			if(prodBatchReturn.getPbId() != initialProdBatch.getPbId()) { valid = false;}
			if(prodBatchReturn.getReceptId() != initialProdBatch.getReceptId()) {valid = false;}
			if(prodBatchReturn.getStatus() != initialProdBatch.getStatus()) { valid = false;}
		} catch (DALException e) {
			valid = false;
		}catch (Exception e) {
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
			DTOProduktBatch prodBatchReturn = prodBatch.getProduktBatch(1234);
		}catch (DALException e) {
			valid = false;
		}catch (Exception e) {
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
			assertTrue(list.size() >0);
		} catch (DALException e) {
			fail("Something went wrong fecthing product batch list in the test");
		}
	}

	/**
	 * 
	 */
	@Test
	void testCreateProduktBatch() {
		try {
			prodBatch.createProduktBatch(new DTOProduktBatch(Status.Igang, 1));
		}catch (Exception e) {
			fail("Something went wrong creating a product batch");
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
		}catch (DALException e) {
			valid = false;
			fail("Invalid pb id");
		}catch (Exception e) {
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
		}catch (DALException e) {
			valid = false;
		}catch (Exception e) {
			valid = false;
			fail("Something went wrong updating a invalid product batch");
		}
		assertFalse(valid);
	}

}
