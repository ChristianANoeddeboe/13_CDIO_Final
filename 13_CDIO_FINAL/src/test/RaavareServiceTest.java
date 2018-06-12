package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import connector.MySQLConnector;
import controller.RaavareBatchController;
import controller.RaavareController;
import dao.DAORaavare;
import dao.DAORaavareBatch;
import dto.DTORaavare;
import dto.DTORaavareBatch;
import exception.DALException;

class RaavareServiceTest {

	static RaavareController controller = new RaavareController(new DAORaavare());
	static RaavareBatchController controllerKomp = new RaavareBatchController(new DAORaavareBatch());
	String baseUrl = "http://207.154.253.254:8080/13_CDIO_FINAL/rest/raavare/";
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
	void testGetRaavareList() {
		try {
			HttpResponse<DTORaavare[]> response = Unirest.get(baseUrl).asObject(DTORaavare[].class);
			DTORaavare[] responseArray = response.getBody();		
			List<DTORaavare> sqlResponseArray = controller.getRaavreList();
			if(responseArray.length == sqlResponseArray.size()) {
				for (int i = 0; i < responseArray.length; i++) {
					if(!(responseArray[i].getLeverandoer().equals(sqlResponseArray.get(i).getLeverandoer()) &&
							responseArray[i].getRaavareNavn().equals(sqlResponseArray.get(i).getRaavareNavn())&&
							responseArray[i].getRaavareId() == sqlResponseArray.get(i).getRaavareId())) {
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
	void testCreateRaavare() {
		try {
			Unirest.post(baseUrl)
			.header("Content-Type", "application/json").
			body(new DTORaavare(9999,"Test","TestLeverandoer"))
			.asJson();
			DTORaavare temp = controller.getRaavare(9999);
			if(temp.getRaavareId() == 9999 && temp.getRaavareNavn().equals("Test") && temp.getLeverandoer().equals("TestLeverandoer")) {
				controller.deleteRaavare(9999);
			}else {
				fail("The created raavare was not found or did not match the one created with rest");
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
	void testUpdateRaavare() {
		try {
			Unirest.post(baseUrl)
			.header("Content-Type", "application/json").
			body(new DTORaavare(9999,"Test","TestLeverandoer"))
			.asJson();
			DTORaavare temp = controller.getRaavare(9999);
			if(temp.getRaavareId() == 9999 && temp.getRaavareNavn().equals("Test") && temp.getLeverandoer().equals("TestLeverandoer")) {
				Unirest.put(baseUrl)
				.header("Content-Type", "application/json")
				.body(new DTORaavare(9999,"TestUpdate","TestLUpdate"))
				.asJson();

				DTORaavare temp2 = controller.getRaavare(9999);
				if(temp2.getRaavareNavn().equals("TestUpdate") && temp2.getLeverandoer().equals("TestLUpdate") && temp2.getRaavareId() == 9999) {
					controller.deleteRaavare(9999);
				}else {
					fail("The raavare was not updater properly");
				}
			}else {
				fail("The created raavare was not found or did not match the one created with rest");
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
	void testDeleteRaavare() {
		try {
			Unirest.post(baseUrl)
			.header("Content-Type", "application/json").
			body(new DTORaavare(9999,"Test","TestLeverandoer"))
			.asJson();
			DTORaavare temp = controller.getRaavare(9999);
			if(temp.getRaavareId() == 9999 && temp.getRaavareNavn().equals("Test") && temp.getLeverandoer().equals("TestLeverandoer")) {
				Unirest.delete(baseUrl + "{id}")
				.header("Content-Type", "application/json")
				.routeParam("id", "9999")
				.asJson();
				try {
					controller.getRaavare(9999);
				} catch (Exception e) {

				}		
			}else {
				fail("The created raavare was not found or did not match the one created with rest");
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
	void testGetRaavareBatchList() {
		try {
			HttpResponse<DTORaavareBatch[]> response = Unirest.get(baseUrl+"batch/"+1).asObject(DTORaavareBatch[].class);
			DTORaavareBatch[] responseArray = response.getBody();		
			List<DTORaavareBatch> sqlResponseArray = controllerKomp.getRaavareBatchList(1);
			if(responseArray.length == sqlResponseArray.size()) {
				for (int i = 0; i < responseArray.length; i++) {
					if(!(responseArray[i].getMaengde() == sqlResponseArray.get(i).getMaengde() &&
							responseArray[i].getRaavareId() == sqlResponseArray.get(i).getRaavareId()&&
							responseArray[i].getRbId() == sqlResponseArray.get(i).getRbId())) {
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
	void testCreateRaavareBatch() {
		try {
			Unirest.post(baseUrl)
			.header("Content-Type", "application/json").
			body(new DTORaavare(9999,"Test","TestLeverandoer"))
			.asJson();
			
			Unirest.post(baseUrl+"batch").
			header("Content-Type", "application/json").
			body(new DTORaavareBatch(9999, 1, 1000)).asJson();
		
			DTORaavareBatch tempKomp = controllerKomp.getRaavareBatch(9999);
			if(tempKomp.getMaengde() == 1000 && tempKomp.getRaavareId() == 1) {
				controllerKomp.deleteRaavareBatch(9999);
				controller.deleteRaavare(9999);
			}else {
				fail("The created raavareBatch was not found or did not match the one created with rest");
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
	void testUpdateRaavareBatch() {
		try {
			Unirest.post(baseUrl)
			.header("Content-Type", "application/json").
			body(new DTORaavare(9999,"Test","TestLeverandoer"))
			.asJson();
			
			Unirest.post(baseUrl+"batch").
			header("Content-Type", "application/json").
			body(new DTORaavareBatch(9999, 1, 1000)).asJson();
		
			DTORaavareBatch tempKomp = controllerKomp.getRaavareBatch(9999);
			if(tempKomp.getMaengde() == 1000 && tempKomp.getRaavareId() == 1) {
				Unirest.put(baseUrl + "batch")
				.header("Content-Type", "application/json")
				.body(new DTORaavareBatch(9999, 1, 100))
				.asJson();

				DTORaavareBatch temp2 = controllerKomp.getRaavareBatch(9999);
				
				if(temp2.getMaengde() == 100 && temp2.getRaavareId() == 1 && temp2.getRbId() == 9999) {
					controllerKomp.deleteRaavareBatch(9999);
					controller.deleteRaavare(9999);
				}else {
					fail("The raavareBatch was not updater properly");
				}
			}else {
				fail("The created raavareBatch was not found or did not match the one created with rest");
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
	void testDeleteRaavareBatch() {
		try {
			Unirest.post(baseUrl)
			.header("Content-Type", "application/json").
			body(new DTORaavare(9999,"Test","TestLeverandoer"))
			.asJson();
			
			Unirest.post(baseUrl+"batch").
			header("Content-Type", "application/json").
			body(new DTORaavareBatch(9999, 1, 1000)).asJson();
			
			Unirest.delete(baseUrl + "batch/{id}/")
			.header("Content-Type", "application/json")
			.routeParam("id", "9999")
			.asJson();
				try {
					controllerKomp.getRaavareBatch(9999);
				} catch (Exception e) {
					
				}		
				controller.deleteRaavare(9999);
		} catch (UnirestException e) {
			e.printStackTrace();
			fail("UniRestexception");

		} catch (DALException e) {
			e.printStackTrace();
			fail("Dalexception");
		}
	}

}
