package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.http.options.Options;

import connector.MySQLConnector;
import controller.ReceptController;
import dao.DAORecept;
import dto.DTORecept;
import exception.DALException;

class ReceptServiceTest {
	static ReceptController controller = new ReceptController(new DAORecept());
	String baseUrl = "http://207.154.253.254:8080/13_CDIO_FINAL/rest/recept/";
	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		new MySQLConnector();
		
		Unirest.setObjectMapper(new ObjectMapper() {
			private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
			= new com.fasterxml.jackson.databind.ObjectMapper();

			public <T> T readValue(String value, Class<T> valueType) {
				try {
					return jacksonObjectMapper.readValue(value, valueType);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

			public String writeValue(Object value) {
				try {
					return jacksonObjectMapper.writeValueAsString(value);
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void testGetReceptList() {		
		try {
			HttpResponse<DTORecept[]> response = Unirest.get(baseUrl+"all").asObject(DTORecept[].class);
			DTORecept[] responseArray = response.getBody();		
			List<DTORecept> sqlResponseArray = controller.getReceptList();
			if(responseArray.length == sqlResponseArray.size()) {
				for (int i = 0; i < responseArray.length; i++) {
					if(!(responseArray[i].getReceptId() == sqlResponseArray.get(i).getReceptId() &&
							responseArray[i].getReceptNavn().equals(sqlResponseArray.get(i).getReceptNavn()))) {
						fail("The two arrays are not equal");
					}
				}
			}else {
				fail("The two arrays are not of the same size");
			}
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Test
	void testCreateRecept() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateRecept() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteRecept() {
		fail("Not yet implemented");
	}

	@Test
	void testGetReceptKomp() {
		fail("Not yet implemented");
	}

	@Test
	void testGetReceptKompListInt() {
		fail("Not yet implemented");
	}

	@Test
	void testGetReceptKompList() {
		fail("Not yet implemented");
	}

	@Test
	void testCreateReceptKomp() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateReceptKomp() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteReceptKomp() {
		fail("Not yet implemented");
	}

}
