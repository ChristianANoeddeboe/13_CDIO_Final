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
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import connector.MySQLConnector;
import controller.OperatoerController;
import dao.DAOOperatoer;
import dto.Aktiv;
import dto.DTOOperatoer;
import dto.Roller;
import exception.DALException;

public class OperatoerServiceTest {
	static OperatoerController controller = new OperatoerController(new DAOOperatoer());
	String baseUrl = "http://207.154.253.254:8080/13_CDIO_FINAL/rest/operatoer/";

	//Testdata
	int id = 1;
	String fornavn = "Fornavn";
	String efternavn = "Efternavn";
	String cpr = "1919239950";
	Roller roles = Roller.Administrator;
	Aktiv status = Aktiv.aktiv;

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
					if(!(responseArray[i].getCpr().equals(sqlResponseArray.get(i).getCpr()))) {
						fail("The two arrays are not equal");
					}
					if(!(responseArray[i].getFornavn().equals(sqlResponseArray.get(i).getFornavn()))) {
						fail("The two arrays are not equal");
					}
					if(!(responseArray[i].getEfternavn().equals(sqlResponseArray.get(i).getEfternavn()))) {
						fail("The two arrays are not equal");
					}
					if(!(responseArray[i].getAktiv().equals(sqlResponseArray.get(i).getAktiv()))) {
						fail("The two arrays are not equal");
					}
					if(!(responseArray[i].getOprId() == sqlResponseArray.get(i).getOprId())) {
						fail("The two arrays are not equal");
					}
					if(!(responseArray[i].getRoles().equals(sqlResponseArray.get(i).getRoles()))) {
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
		try {
			Unirest.post(baseUrl+"create")
			.header("Content-Type", "application/json")
			.body(new DTOOperatoer(id, fornavn, efternavn, cpr, roles, status))
			.asJson();

			List<DTOOperatoer> sqlResponse = controller.getOperatoerList();
			for(int i = 0; i >= sqlResponse.size(); i++) {
				if(sqlResponse.get(i).getCpr().equals(cpr)) {
					id = sqlResponse.get(i).getOprId();
					DTOOperatoer opr = controller.getOperatoer(id);
					if(!(opr.getFornavn().equals(fornavn))) {
						fail("Not same value");
					}
					if(!(opr.getEfternavn().equals(efternavn))) {
						fail("Not same value");
					}
					if(!(opr.getCpr().equals(cpr))) {
						fail("Not same value");
					}
					if(!(opr.getRoles() == roles)) {
						fail("Not same value");
					}
					if(!(opr.getAktiv() == status)) {
						fail("Not same value");
					}
					break;
				}
			}
			MySQLConnector.doQuery("CALL adminDeleteOperator(" + cpr + ");");
		} catch (UnirestException e) {
			e.printStackTrace();
			fail("Unirest");
		} catch (DALException e) {
			e.printStackTrace();
			fail("DALExeption");
		}
	}


	@Test
	void testUpdateOperatoer() {
		try {
			Unirest.post(baseUrl+"create")
			.header("Content-Type", "application/json")
			.body(new DTOOperatoer(id, fornavn, efternavn, cpr, roles, status))
			.asJson();

			List<DTOOperatoer> sqlResponse = controller.getOperatoerList();
			for(int i = 0; i >= sqlResponse.size(); i++) {
				if(sqlResponse.get(i).getCpr().equals(cpr)) {
					id = sqlResponse.get(i).getOprId();
					DTOOperatoer opr = controller.getOperatoer(id);

					Unirest.post(baseUrl+"update")
					.header("Content-Type", "application/json")
					.body(new DTOOperatoer(opr.getOprId(), "NytFornavn", "NytEfternavn", opr.getCpr(), opr.getRoles(), opr.getAktiv()))
					.asJson();
				}
			}
		} catch (UnirestException e) {
			e.printStackTrace();
			fail("Unirest");
		} catch (DALException e) {
			fail("DALExeption");
			e.printStackTrace();
		}

		try {
			List<DTOOperatoer> sqlResponse = controller.getOperatoerList();
			for(int i = 0; i >= sqlResponse.size(); i++) {
				if(sqlResponse.get(i).getCpr().equals(cpr)) {
					id = sqlResponse.get(i).getOprId();
					DTOOperatoer opr = controller.getOperatoer(id);

					if(!(opr.getFornavn().equals("NytFornavn"))) {
						fail("Not same value");
					}
					if(!(opr.getEfternavn().equals("NytEfternavn"))) {
						fail("Not same value");
					}
					if(!(opr.getRoles() == Roller.Administrator)) {
						fail("Not same value");
					}
					if(!(opr.getAktiv() == Aktiv.aktiv)) {
						fail("Not same value");
					}
					break;
				}
			}
			MySQLConnector.doQuery("CALL adminDeleteOperator(" + cpr + ");");
		} catch (DALException e) {
			fail("DALExeption");
			e.printStackTrace();
		}

	}
}
