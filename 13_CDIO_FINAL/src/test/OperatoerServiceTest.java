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
import controller.BrugerController;
import dao.DAOBruger;
import dto.Aktiv;
import dto.DTOBruger;
import dto.Roller;
import exception.DALException;
import interfaces.IBrugerController;

public class OperatoerServiceTest {
	IBrugerController controller = BrugerController.getInstance();
	String baseUrl = "http://207.154.253.254:8080/13_CDIO_FINAL/rest/bruger/";

	//Testdata
	int id = 1;
	String fornavn = "Fornavn";
	String efternavn = "Efternavn";
	String cpr = "1919239950";
	Roller roles = Roller.Administrator;
	Aktiv status = Aktiv.aktiv;

	@BeforeAll
	static void setUp() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
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
	void testGetBrugerList() {
		try {
			HttpResponse<DTOBruger[]> response = Unirest.get(baseUrl).asObject(DTOBruger[].class);
			DTOBruger[] responseArray = response.getBody();		
			List<DTOBruger> sqlResponseArray = controller.getBrugerList();
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

		} catch (InstantiationException e) {
			e.printStackTrace();
			fail("InstantationException");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("IllegalAccessException");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail("ClassNotFoundException");
		}
	}

	@Test
	void testBrugerOperatoer() {
		try {
			Unirest.post(baseUrl)
			.header("Content-Type", "application/json")
			.body(new DTOBruger(id, fornavn, efternavn, cpr, roles, status))
			.asJson();

			List<DTOBruger> sqlResponse = controller.getBrugerList();
			for(int i = 0; i >= sqlResponse.size(); i++) {
				if(sqlResponse.get(i).getCpr().equals(cpr)) {
					id = sqlResponse.get(i).getOprId();
					DTOBruger opr = controller.getBruger(id);
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
		} catch (InstantiationException e) {
			e.printStackTrace();
			fail("InstantationException");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("IllegalAccessException");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail("ClassNotFoundException");
		}
	}


	@Test
	void testUpdateBruger() {
		try {
			Unirest.post(baseUrl)
			.header("Content-Type", "application/json")
			.body(new DTOBruger(id, fornavn, efternavn, cpr, roles, status))
			.asJson();

			List<DTOBruger> sqlResponse = controller.getBrugerList();
			for(int i = 0; i >= sqlResponse.size(); i++) {
				if(sqlResponse.get(i).getCpr().equals(cpr)) {
					id = sqlResponse.get(i).getOprId();
					DTOBruger opr = controller.getBruger(id);

					Unirest.post(baseUrl)
					.header("Content-Type", "application/json")
					.body(new DTOBruger(opr.getOprId(), "NytFornavn", "NytEfternavn", opr.getCpr(), opr.getRoles(), opr.getAktiv()))
					.asJson();
				}
			}
		} catch (UnirestException e) {
			e.printStackTrace();
			fail("Unirest");
		} catch (DALException e) {
			fail("DALExeption");
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
			fail("InstantationException");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("IllegalAccessException");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail("ClassNotFoundException");
		}

		try {
			List<DTOBruger> sqlResponse = controller.getBrugerList();
			for(int i = 0; i >= sqlResponse.size(); i++) {
				if(sqlResponse.get(i).getCpr().equals(cpr)) {
					id = sqlResponse.get(i).getOprId();
					DTOBruger opr = controller.getBruger(id);

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
		} catch (InstantiationException e) {
			e.printStackTrace();
			fail("InstantationException");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail("IllegalAccessException");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail("ClassNotFoundException");
		}

	}
}
