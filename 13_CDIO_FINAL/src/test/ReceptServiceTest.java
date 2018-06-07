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
import controller.ReceptKompController;
import dao.DAORecept;
import dao.DAOReceptKomp;
import dto.DTORecept;
import dto.DTOReceptKomp;
import exception.DALException;

class ReceptServiceTest {
	static ReceptController controller = new ReceptController(new DAORecept());
	static ReceptKompController controllerKomp = new ReceptKompController(new DAOReceptKomp());
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
			fail("UniRestexception");
			e.printStackTrace();
		} catch (DALException e) {
			fail("DalException");
			e.printStackTrace();
		}

	}
	@Test
	void testCreateRecept() {
		try {
			Unirest.post(baseUrl + "create")
			.header("Content-Type", "application/json").
			body(new DTORecept(9999, "Test"))
			.asJson();
			DTORecept temp = controller.getRecept(9999);
			if(temp.getReceptId() == 9999 && temp.getReceptNavn().equals("Test")) {
				controller.deleteRecept(9999);
			}else {
				fail("The created recept was not found or did not match the one created with rest");
			}
		} catch (UnirestException e) {
			e.printStackTrace();
			fail("UniRestexception");

		} catch (DALException e) {
			e.printStackTrace();
			fail("DalException");

		}

	}
	@Test
	void testUpdateRecept() {
		try {
			Unirest.post(baseUrl + "create")
			.header("Content-Type", "application/json")
			.body(new DTORecept(9999, "Test"))
			.asJson();
			DTORecept temp = controller.getRecept(9999);
			if(temp.getReceptId() == 9999 && temp.getReceptNavn().equals("Test")) {
				Unirest.put(baseUrl + "update")
				.header("Content-Type", "application/json")
				.body(new DTORecept(9999, "Test2"))
				.asJson();

				DTORecept temp2 = controller.getRecept(9999);
				if(temp2.getReceptNavn().equals("Test2") && temp2.getReceptId() == 9999) {
					controller.deleteRecept(9999);
				}else {
					fail("The recept was not updater properly");
				}
			}else {
				fail("The created recept was not found or did not match the one created with rest");
			}

		} catch (UnirestException e) {
			e.printStackTrace();
			fail("UniRestexception");
		} catch (DALException e) {
			e.printStackTrace();
			fail("DalException");
		}
	}
	@Test
	void testDeleteRecept() {
		try {
			Unirest.post(baseUrl + "create")
			.header("Content-Type", "application/json").
			body(new DTORecept(9999, "Test"))
			.asJson();	
			DTORecept temp = controller.getRecept(9999);
			if(temp.getReceptId() == 9999 && temp.getReceptNavn().equals("Test")) {
				Unirest.delete(baseUrl + "{id}")
				.header("Content-Type", "application/json")
				.routeParam("id", "9999")
				.asJson();
				try {
					controller.getRecept(9999);
				} catch (Exception e) {

				}		
			}else {
				fail("The created recept was not found or did not match the one created with rest");
			}
		} catch (UnirestException e) {
			e.printStackTrace();
			fail("UniRestexception");

		} catch (DALException e) {
			e.printStackTrace();
			fail("DalException");

		}
	}


	@Test
	void testGetReceptKompList() {
		try {
			HttpResponse<DTOReceptKomp[]> response = Unirest.get(baseUrl+"komponent/list/"+1).asObject(DTOReceptKomp[].class);
			DTOReceptKomp[] responseArray = response.getBody();		
			List<DTOReceptKomp> sqlResponseArray = controllerKomp.getReceptKompList(1);
			if(responseArray.length == sqlResponseArray.size()) {
				for (int i = 0; i < responseArray.length; i++) {
					if(!(responseArray[i].getReceptId() == sqlResponseArray.get(i).getReceptId() &&
							responseArray[i].getRaavareId() == sqlResponseArray.get(i).getRaavareId()&&
							responseArray[i].getNomNetto() == sqlResponseArray.get(i).getNomNetto()&&
							responseArray[i].getTolerance() == sqlResponseArray.get(i).getTolerance())) {
						fail("The two arrays are not equal");
					}
				}
			}else {
				fail("The two arrays are not of the same size");
			}
		} catch (UnirestException e) {
			fail("UniRestexception");
			e.printStackTrace();
		} catch (DALException e) {
			fail("DalException");
			e.printStackTrace();
		}
	}

	@Test
	void testCreateReceptKomp() {
		try {
			Unirest.post(baseUrl + "create")
			.header("Content-Type", "application/json").
			body(new DTORecept(9999, "Test"))
			.asJson();
			
			Unirest.post(baseUrl+"komponent/create").
			header("Content-Type", "application/json").
			body(new DTOReceptKomp(9999, 1, 1, 1)).asJson();
		
			DTOReceptKomp tempKomp = controllerKomp.getReceptKomp(9999, 1);
			if(tempKomp.getTolerance() == 1 && tempKomp.getNomNetto() == 1) {
				controllerKomp.deleteReceptKomp(9999, 1);
				controller.deleteRecept(9999);
			}else {
				fail("The created receptKomp was not found or did not match the one created with rest");
			}
		} catch (UnirestException e) {
			e.printStackTrace();
			fail("UniRestexception");

		} catch (DALException e) {
			e.printStackTrace();
			fail("DalException");

		}
	}

	@Test
	void testUpdateReceptKomp() {
		try {
			Unirest.post(baseUrl + "create")
			.header("Content-Type", "application/json")
			.body(new DTORecept(9999, "Test"))
			.asJson();
			
			Unirest.post(baseUrl+"komponent/create")
			.header("Content-Type", "application/json")
			.body(new DTOReceptKomp(9999, 1, 1, 1))
			.asJson();
		
			DTOReceptKomp tempKomp = controllerKomp.getReceptKomp(9999, 1);
			if(tempKomp.getTolerance() == 1 && tempKomp.getNomNetto() == 1) {
				Unirest.put(baseUrl + "komponent/update")
				.header("Content-Type", "application/json")
				.body(new DTOReceptKomp(9999, 1, 2, 2))
				.asJson();

				DTOReceptKomp temp2 = controllerKomp.getReceptKomp(9999,1);
				
				if(temp2.getNomNetto() == 2 && temp2.getTolerance() == 2) {
					controllerKomp.deleteReceptKomp(9999, 1);
					controller.deleteRecept(9999);
				}else {
					fail("The receptKomp was not updater properly");
				}
			}else {
				fail("The created receptKomp was not found or did not match the one created with rest");
			}

		} catch (UnirestException e) {
			e.printStackTrace();
			fail("UniRestexception");
		} catch (DALException e) {
			e.printStackTrace();
			fail("DalException");
		}
	}

	@Test
	void testDeleteReceptKomp() {
		try {
			Unirest.post(baseUrl + "create")
			.header("Content-Type", "application/json").
			body(new DTORecept(9999, "Test"))
			.asJson();
			
			Unirest.post(baseUrl+"komponent/create").
			header("Content-Type", "application/json").
			body(new DTOReceptKomp(9999, 1, 1, 1)).asJson();
			
			Unirest.delete(baseUrl + "komponent/{id}/{id2}")
			.header("Content-Type", "application/json")
			.routeParam("id", "9999")
			.routeParam("id2", "1")
			.asJson();
				try {
					controllerKomp.getReceptKomp(9999,1);
					controller.deleteRecept(9999);
				} catch (Exception e) {

				}		
			
		} catch (UnirestException e) {
			e.printStackTrace();
			fail("UniRestexception");

		}
	}

}
