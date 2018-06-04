package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import connector.MySQLConnector;
import dao.DAOProductBatchKomponent;
import exception.DALException;
import dto.DTOProduktBatchKomp;

class MySQLProduktBatchKomponentDAOTest {
	static DAOProductBatchKomponent prodBatchKomp;
	static DTOProduktBatchKomp initialProdBatchKomp;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		try { new MySQLConnector(); }
		catch (InstantiationException e) { e.printStackTrace(); }
		catch (IllegalAccessException e) { e.printStackTrace(); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		catch (SQLException e) { e.printStackTrace(); }
		prodBatchKomp = new DAOProductBatchKomponent();
		initialProdBatchKomp = prodBatchKomp.getProduktBatchKomp(1,1);
	}

	@Test
	void testGetProduktBatchKompValid() {
		boolean valid = true;
		try {
			DTOProduktBatchKomp prodBatchKompReturn = prodBatchKomp.getProduktBatchKomp(1, 1);
			if(prodBatchKompReturn.getPbId() != initialProdBatchKomp.getPbId()) { valid = false;}
			if(prodBatchKompReturn.getRbId() != initialProdBatchKomp.getRbId()) { valid = false;}
			if(prodBatchKompReturn.getNetto() != initialProdBatchKomp.getNetto()) { valid = false;}
			if(prodBatchKompReturn.getOprId() != initialProdBatchKomp.getOprId()) { valid = false;}
		} catch (DALException e) {
			valid = false;
		}catch (Exception e) {
			fail("Something went wrong testing a valid product batch komponent");
		}
		assertTrue(valid);
	}
	
	@Test
	void testGetProduktBatchKompInvalid() {
		boolean valid = true;
		try {
			DTOProduktBatchKomp prodBatchKompReturn = prodBatchKomp.getProduktBatchKomp(9999, 9999);
		} catch (DALException e) {
			valid = false;
		}catch (Exception e) {
			fail("Something went wrong testing a valid product batch komponent");
		}
		assertFalse(valid);
	}

	@Test
	void testGetProduktBatchKompListInt() {
		try {
			List<DTOProduktBatchKomp> list = prodBatchKomp.getProduktBatchKompList(1);
			assertTrue(list.size() >0);
		} catch (DALException e) {
			fail("Something went wrong fecthing product batch komp list in the test with pb id = 1");
		}
	}

	@Test
	void testGetProduktBatchKompList() {
		try {
			List<DTOProduktBatchKomp> list = prodBatchKomp.getProduktBatchKompList();
			assertTrue(list.size() >0);
		} catch (DALException e) {
			fail("Something went wrong fecthing product batch komp list in the test");
		}
	}

	@Test
	void testCreateProduktBatchKomp() {
		int lastid;
		try {
			prodBatchKomp.createProduktBatchKomp(new DTOProduktBatchKomp(1, 10, 1.0, 1.0, 1));
		} catch (Exception e) {
			fail("Something went wrong creating a product batch component");
		}
	}

	@Test
	void testUpdateProduktBatchKompValid() {
		boolean valid = true;
		try {
			prodBatchKomp.updateProduktBatchKomp(new DTOProduktBatchKomp(1, 1, 2, 2, 1));
		}catch (DALException e) {
			valid = false;
			fail("Invalid parameters");
		}catch (Exception e) {
			valid = false;
			fail("Something went wrong updating a valid prod batch");
		}
		assertTrue(valid);
	}
	
	@Test
	void testUpdateProduktBatchKompInvalid() {
		boolean valid = true;
		try {
			prodBatchKomp.updateProduktBatchKomp(new DTOProduktBatchKomp(99999, 9999, 99, 9999, 1));
		}catch (DALException e) {
			valid = false;
		}catch (Exception e) {
			valid = false;
			fail("Something went wrong updating a invalid product batch");
		}
		assertFalse(valid);
	}

}
