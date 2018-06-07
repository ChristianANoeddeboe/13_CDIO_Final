package test;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import connector.MySQLConnector;
import controller.OperatoerController;
import controller.ReceptController;
import dao.DAOOperatoer;
import dao.DAORecept;
import dto.Aktiv;
import dto.DTOOperatoer;
import dto.DTORecept;
import exception.DALException;

public class OperatoerServiceTest {
	static OperatoerController controller = new OperatoerController(new DAOOperatoer());
	String baseUrl = "http://207.154.253.254:8080/13_CDIO_FINAL/rest/operatoer/";
	
    @BeforeAll
    static void setUp() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    	
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
		Unirest.shutdown();
	}
	
	@Test
	void testGetOperatoerList() {
		try {
			HttpResponse<DTOOperatoer[]> response = Unirest.get(baseUrl+"all").asObject(DTOOperatoer[].class);
			DTOOperatoer[] responseArray = response.getBody();		
			List<DTOOperatoer> sqlResponseArray = controller.getOperatoerList();
			if(responseArray.length == sqlResponseArray.size()) {
				for (int i = 0; i < responseArray.length; i++) {
					if(!(responseArray[i].getCpr() == sqlResponseArray.get(i).getCpr())) {
						fail("The two arrays are not equal");
					}
					if(!(responseArray[i].getFornavn() == sqlResponseArray.get(i).getFornavn())) {
						fail("The two arrays are not equal");
					}
					if(!(responseArray[i].getEfternavn() == sqlResponseArray.get(i).getEfternavn())) {
						fail("The two arrays are not equal");
					}
					if(!(responseArray[i].getAktiv() == sqlResponseArray.get(i).getAktiv())) {
						fail("The two arrays are not equal");
					}
					if(!(responseArray[i].getOprId() == sqlResponseArray.get(i).getOprId())) {
						fail("The two arrays are not equal");
					}
					if(!(responseArray[i].getRoles() == sqlResponseArray.get(i).getRoles())) {
						fail("The two arrays are not equal");
					}
				}
			}else {
				fail("The two arrays are not of the same size");
			}
		} catch (UnirestException e) {
			e.printStackTrace();
			fail("The two arrays are not of the same size");
		} catch (DALException e) {
			e.printStackTrace();
			fail("Database fejl: DALException");

		}
	}

	@Test
	void testCreateOperatoer() {
		int id = 99192;
		String fornavn = "Fornavn";
		String efternavn = "Efternavn";
		String cpr = "1919239923";
		String roles = "";
		Aktiv status = Aktiv.aktiv;
		
		try {
			Unirest.post(baseUrl+"create")
					  .header("Content-Type", "application/json")
					  .body(new DTOOperatoer(id, fornavn, efternavn, cpr, roles, status))
					  .asJson();
			
			HttpResponse<DTOOperatoer> checkResponse = Unirest.get(baseUrl+id)
					  .asObject(DTOOperatoer.class);
			
			DTOOperatoer sqlResponse = controller.getOperatoer(id);
			DTOOperatoer restResponse = checkResponse.getBody();
			System.out.println(sqlResponse.toString());
			if(true) {
				
			} else {
				fail("The two arrays are not of the same size");
			}

		} catch (UnirestException e) {
			e.printStackTrace();
			fail("Unirest");
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	void testUpdateOperatoer() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteOperatoer() {
		fail("Not yet implemented");
	}
}
