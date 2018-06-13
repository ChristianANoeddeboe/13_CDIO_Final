package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import dto.Roller;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import connector.MySQLConnector;
import controller.OperatoerController;
import dao.DAOOperatoer;
import dto.DTOOperatoer;
import dto.Aktiv;
import exception.DALException;

class DAOOperatoerTest {
	static OperatoerController testOperatoerController;
	static DTOOperatoer initialOperatoer;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		testOperatoerController = OperatoerController.getInstance();
		initialOperatoer = testOperatoerController.getOperatoer(1);
	}

	@Test
	void testGetOperatoerValid() {
		boolean valid = true;
		try {
			DTOOperatoer operatoerReturn = testOperatoerController.getOperatoer(1);
			if(!operatoerReturn.getAktiv().equals(initialOperatoer.getAktiv())) { valid = false;}
			if(!operatoerReturn.getCpr().equals(initialOperatoer.getCpr())) {valid = false;}
			if(!operatoerReturn.getEfternavn().equals(initialOperatoer.getEfternavn())) {valid = false;}
			if(!operatoerReturn.getFornavn().equals(initialOperatoer.getFornavn())) {valid = false;}
			if(operatoerReturn.getOprId() != initialOperatoer.getOprId()) {valid = false;}
			if(!operatoerReturn.getRoles().equals(initialOperatoer.getRoles())) {valid = false;}
		} catch (DALException e) {
			valid = false;
		}catch (Exception e) {
			fail("Something went wrong testing a valid operatoer");
		}
		assertTrue(valid);
	}

	
	
	@Test
	void testGetOperatoerInvalid() {
		boolean valid = true;
		try {
			DTOOperatoer operatoerReturn = testOperatoerController.getOperatoer(Integer.MAX_VALUE);
			if(!operatoerReturn.getAktiv().equals(initialOperatoer.getAktiv())) { valid = false;}
			if(!operatoerReturn.getCpr().equals(initialOperatoer.getCpr())) {valid = false;}
			if(!operatoerReturn.getEfternavn().equals(initialOperatoer.getEfternavn())) {valid = false;}
			if(!operatoerReturn.getFornavn().equals(initialOperatoer.getFornavn())) {valid = false;}
			if(operatoerReturn.getOprId() != initialOperatoer.getOprId()) {valid = false;}
			if(!operatoerReturn.getRoles().equals(initialOperatoer.getRoles())) {valid = false;}
		} catch (DALException e) {
			valid = false;
		}catch (Exception e) {
			fail("Something went wrong testing a invalid operatoer");
		}
		assertFalse(valid);
	}
	
	
	@Test
	void testCreateOperatoer() {
		try {
			testOperatoerController.createOperatoer(new DTOOperatoer(999,"test", "testEfternavn", "1234561210", Roller.Administrator, Aktiv.aktiv));
		}catch (Exception e) {
			fail("Something went wrong creating an operator");
		}finally {
			try {
				MySQLConnector.doQuery("CALL adminDeleteOperator('" + 1234561210 + "');");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	void testUpdateOperatoerValid() {
		boolean valid = true;
		try {
			testOperatoerController.updateOperatoer(new DTOOperatoer(1,"testUpdated", "testEfternavn", "7070707007", Roller.Administrator, Aktiv.aktiv));
		}catch (DALException e) {
			valid = false;
			fail("Invalid parameters");
		}catch (Exception e) {
			valid = false;
			fail("Something went wrong updating a valid operator");
		}
		assertTrue(valid);
	}
	
	@Test
	void testUpdateOperatoerInvalid() {
		boolean valid = true;
		try {
			testOperatoerController.updateOperatoer(new DTOOperatoer(Integer.MAX_VALUE,"testUpdated", "testEfternavn", "123456-1234", Roller.Administrator, Aktiv.aktiv));
		}catch (DALException e) {
			valid = false;
		}catch (Exception e) {
			valid = false;
			fail("Something went wrong updating a in valid operator");
		}
		assertFalse(valid);
	}

	@Test
	void testGetOperatoerList() {
		try {
			List<DTOOperatoer> list = testOperatoerController.getOperatoerList();
			assertTrue(list.size() >0);
		} catch (DALException e) {
			fail("Something went wrong fecthing operatoer list in the test");
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
