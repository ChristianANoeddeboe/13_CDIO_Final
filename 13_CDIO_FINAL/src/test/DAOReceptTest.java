package test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import dao.DAORecept;
import exception.DALException;
import dto.DTORecept;

class DAOReceptTest {
    DAORecept receptDAO = new DAORecept();

    //Test.
    @BeforeAll
    static void setup() {

    }

    @Test
    void receptDTOTest() {
        String name = "Test1";
        int receptId = 10;
        String expected = "{\"receptId\":\"10\",\"receptNavn\": \"Test1\"}";
        try {
            DTORecept recept = receptDAO.getRecept(1);
            if (recept.getReceptId() != 1) fail("Error getRecept: Id doesn't match.");

            recept.setReceptId(receptId);
            if (recept.getReceptId() != 10) fail("Error setRecept id.");

            recept.setReceptNavn(name);
            if (!recept.getReceptNavn().equals(name)) fail("Error set recept navn.");

            if (!recept.toString().equals(expected)) fail("Error toString.");

        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
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
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            caught = true;
        } finally {
            if (caught) assertTrue(caught);
            else fail("Test erroneousGet: Wrong ID wasn't caught");
        }
    }

    /*Test that getReceptList returns all recepts in correct order.*/
    @Test
    void getReceptListTEST() {
        try {
            List<DTORecept> recepts = receptDAO.getReceptList();
            if (recepts.isEmpty()) fail("List is empty");
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            fail("getReceptListTEST DALException");
        }
    }

    @Test
    void createReceptTest() throws DALException {
        String name = "test";
        DTORecept test = new DTORecept(name);
        try {
            receptDAO.createRecept(test);
        } catch (DALException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            fail("Exception caught.");
        }

        List<DTORecept> list;
		try {
			list = receptDAO.getReceptList();
			DTORecept dto = list.get(list.size() - 1);
	        
	        if (dto.getReceptId() != list.size() - 1 && !dto.getReceptNavn().equals(name)) {
	            fail("Recept wasns't created with expected values.");
	        }
	        
	        try {
	        	for (DTORecept dtoRecept : list) {
					if(dtoRecept.getReceptNavn().equals("test")) {
						receptDAO.deleteRecept(dtoRecept.getReceptId());
						break;
					}
				}
	        } catch (DALException e) {
	        	fail("DALException caught.");
	        }
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			fail("Exception caught.");
		}
        
    }
}
