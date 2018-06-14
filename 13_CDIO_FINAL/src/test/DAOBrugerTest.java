package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import dto.Roller;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import connector.MySQLConnector;
import controller.BrugerController;
import dao.DAOBruger;
import dto.DTOBruger;
import dto.Aktiv;
import exception.DALException;
import interfaces.IBrugerController;

class DAOBrugerTest {
	static IBrugerController testBrugerController;
	static DTOBruger initialBruger;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		testBrugerController = BrugerController.getInstance();
		initialBruger = testBrugerController.getBruger(1);
	}

	@Test
	void testGetBrugerValid() {
		boolean valid = true;
		try {
			DTOBruger brugerReturn = testBrugerController.getBruger(1);
			if(!brugerReturn.getAktiv().equals(initialBruger.getAktiv())) { valid = false;}
			if(!brugerReturn.getCpr().equals(initialBruger.getCpr())) {valid = false;}
			if(!brugerReturn.getEfternavn().equals(initialBruger.getEfternavn())) {valid = false;}
			if(!brugerReturn.getFornavn().equals(initialBruger.getFornavn())) {valid = false;}
			if(brugerReturn.getOprId() != initialBruger.getOprId()) {valid = false;}
			if(!brugerReturn.getRoles().equals(initialBruger.getRoles())) {valid = false;}
		} catch (DALException e) {
			valid = false;
		}catch (Exception e) {
			fail("Something went wrong testing a valid bruger");
		}
		assertTrue(valid);
	}

	
	
	@Test
	void testGetBrugerInvalid() {
		boolean valid = true;
		try {
			DTOBruger brugerReturn = testBrugerController.getBruger(Integer.MAX_VALUE);
			if(!brugerReturn.getAktiv().equals(initialBruger.getAktiv())) { valid = false;}
			if(!brugerReturn.getCpr().equals(initialBruger.getCpr())) {valid = false;}
			if(!brugerReturn.getEfternavn().equals(initialBruger.getEfternavn())) {valid = false;}
			if(!brugerReturn.getFornavn().equals(initialBruger.getFornavn())) {valid = false;}
			if(brugerReturn.getOprId() != initialBruger.getOprId()) {valid = false;}
			if(!brugerReturn.getRoles().equals(initialBruger.getRoles())) {valid = false;}
		} catch (DALException e) {
			valid = false;
		}catch (Exception e) {
			fail("Something went wrong testing a invalid bruger");
		}
		assertFalse(valid);
	}
	
	
	@Test
	void testCreateBruger() {
		try {
			testBrugerController.createBruger(new DTOBruger(999,"test", "testEfternavn", "1234561210", Roller.Administrator, Aktiv.aktiv));
		}catch (Exception e) {
			fail("Something went wrong creating an bruger");
		}finally {
			try {
				MySQLConnector.doQuery("CALL adminDeleteOperator('" + 1234561210 + "');");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	void testUpdateBrugerValid() {
		boolean valid = true;
		try {
			testBrugerController.updateBruger(new DTOBruger(1,"testUpdated", "testEfternavn", "7070707007", Roller.Administrator, Aktiv.aktiv));
		}catch (DALException e) {
			valid = false;
			fail("Invalid parameters");
		}catch (Exception e) {
			valid = false;
			fail("Something went wrong updating a valid bruger");
		}
		assertTrue(valid);
	}
	
	@Test
	void testUpdateBrugerInvalid() {
		boolean valid = true;
		try {
			testBrugerController.updateBruger(new DTOBruger(Integer.MAX_VALUE,"testUpdated", "testEfternavn", "123456-1234", Roller.Administrator, Aktiv.aktiv));
		}catch (DALException e) {
			valid = false;
		}catch (Exception e) {
			valid = false;
			fail("Something went wrong updating a invalid bruger");
		}
		assertFalse(valid);
	}

	@Test
	void testGetBrugerList() {
		try {
			List<DTOBruger> list = testBrugerController.getBrugerList();
			assertTrue(list.size() >0);
		} catch (DALException e) {
			fail("Something went wrong fecthing bruger list in the test");
		} catch (InstantiationException e) {
			fail("InstantationException");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			fail("InstantationException");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			fail("InstantationException");
			e.printStackTrace();
		}
	}

}