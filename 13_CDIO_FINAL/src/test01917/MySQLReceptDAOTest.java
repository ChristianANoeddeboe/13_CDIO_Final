package test01917;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import connector.Connector;
import dao.MySQLReceptDAO;
import daointerfaces01917.DALException;
import dto.ReceptDTO;

class MySQLReceptDAOTest {
	MySQLReceptDAO receptDAO = new MySQLReceptDAO();
	//Test.
	@BeforeAll
	static void setup() {
		try { new Connector(); } 
		catch (InstantiationException e) { e.printStackTrace(); }
		catch (IllegalAccessException e) { e.printStackTrace(); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		catch (SQLException e) { e.printStackTrace(); }
	}
	
	@Test
	void receptDTOTest() {
		String name = "Test1";
		int receptId = 10;
		String expected = receptId+"\t"+name;
		try {
			ReceptDTO recept = receptDAO.getRecept(1);
			if(recept.getReceptId()!=1) fail("Error getRecept: Id doesn't match.");
			
			recept.setReceptId(receptId);
			if(recept.getReceptId()!=10) fail("Error setRecept id.");
			
			recept.setReceptNavn(name);
			if(!recept.getReceptNavn().equals(name)) fail("Error set recept navn.");
			
			if(!recept.toString().equals(expected)) fail("Error toString.");
			
		} catch(DALException e) {
			fail("SQL exception caught");
		}
	}
	
	/* Test whether erroneous gets get caught. 
	 * Input1: Wrong but valid ID.
	 * */
	@Test
	void erroneousGet() {
		boolean caught = false;
		try {
			receptDAO.getRecept(100);
		} catch (DALException e) {
			caught = true;
		}
		finally {
			if(caught) assertTrue(caught);
			else fail("Test erroneousGet: Wrong ID wasn't caught");
		}
	}

	/*Test that getReceptList returns all recepts in correct order.*/
	@Test
	void getReceptListTEST() {
		try {
			List<ReceptDTO> recepts = receptDAO.getReceptList();
			if(recepts.isEmpty()) fail("List is empty");
		} catch(DALException e) {
			fail("getReceptListTEST DALException");
		}
	}
	
	@Test
	void createReceptTest() throws DALException {
		String name = "test";
		ReceptDTO test = new ReceptDTO(name);
		try {
			receptDAO.createRecept(test);
		} catch(DALException e) {
			fail("DALException caught.");
		}
		
		List<ReceptDTO> list = receptDAO.getReceptList();
		ReceptDTO dto = list.get(list.size()-1);
		
		if(dto.getReceptId()!=list.size()-1 && !dto.getReceptNavn().equals(name)) {
			fail("Recept wasns't created with expected values.");
		}	
	}
}
