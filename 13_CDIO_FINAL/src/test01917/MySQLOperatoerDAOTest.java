package test01917;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import connector.MySQLConnector;
import dao.DAOOperatoer;
import dto.DTOOperatoer;
import dto.DTOOperatoer.Aktiv;
import exception.DALException;

class MySQLOperatoerDAOTest {
	static DAOOperatoer operatoer;
	static DTOOperatoer initialOperatoer;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		try { new MySQLConnector(); }
		catch (InstantiationException e) { e.printStackTrace(); }
		catch (IllegalAccessException e) { e.printStackTrace(); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		catch (SQLException e) { e.printStackTrace(); }
		operatoer = new DAOOperatoer();
		initialOperatoer = operatoer.getOperatoer(1);
	}

	@Test
	void testGetOperatoerValid() {
		boolean valid = true;
		try {
			DTOOperatoer operatoerReturn = operatoer.getOperatoer(1);
			if(!operatoerReturn.getAktiv().equals(initialOperatoer.getAktiv())) { valid = false;}
			if(!operatoerReturn.getCpr().equals(initialOperatoer.getCpr())) {valid = false;}
			if(!operatoerReturn.getEfternavn().equals(initialOperatoer.getEfternavn())) {valid = false;}
			if(!operatoerReturn.getFornavn().equals(initialOperatoer.getFornavn())) {valid = false;}
			if(operatoerReturn.getOprId() != initialOperatoer.getOprId()) {valid = false;}
			if(!operatoerReturn.getPassword().equals(initialOperatoer.getPassword())) {valid = false;}
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
			DTOOperatoer operatoerReturn = operatoer.getOperatoer(Integer.MAX_VALUE);
			if(!operatoerReturn.getAktiv().equals(initialOperatoer.getAktiv())) { valid = false;}
			if(!operatoerReturn.getCpr().equals(initialOperatoer.getCpr())) {valid = false;}
			if(!operatoerReturn.getEfternavn().equals(initialOperatoer.getEfternavn())) {valid = false;}
			if(!operatoerReturn.getFornavn().equals(initialOperatoer.getFornavn())) {valid = false;}
			if(operatoerReturn.getOprId() != initialOperatoer.getOprId()) {valid = false;}
			if(!operatoerReturn.getPassword().equals(initialOperatoer.getPassword())) {valid = false;}
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
			operatoer.createOperatoer(new DTOOperatoer("test", "testEfternavn", "123456-1210", "password", "Administrator", Aktiv.aktiv));
		}catch (Exception e) {
			fail("Something went wrong creating an operator");
		}	}

	@Test
	void testUpdateOperatoerValid() {
		boolean valid = true;
		try {
			operatoer.updateOperatoer(new DTOOperatoer(1,"testUpdated", "testEfternavn", "123456-1234", "password", "Administrator", Aktiv.aktiv));
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
			operatoer.updateOperatoer(new DTOOperatoer(Integer.MAX_VALUE,"testUpdated", "testEfternavn", "123456-1234", "password", "Administrator", Aktiv.aktiv));
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
			List<DTOOperatoer> list = operatoer.getOperatoerList();
			assertTrue(list.size() >0);
		} catch (DALException e) {
			fail("Something went wrong fecthing operatoer list in the test");
		}
	}

}
