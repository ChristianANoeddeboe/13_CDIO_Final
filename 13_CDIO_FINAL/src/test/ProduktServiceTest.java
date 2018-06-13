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
import controller.ProduktBatchController;
import controller.ProduktBatchKompController;
import dao.DAOProduktBatch;
import dao.DAOProduktBatchKomp;
import dto.DTOProduktBatch;
import dto.DTOProduktBatchKomp;
import dto.Status;
import exception.DALException;

class ProduktServiceTest {

	ProduktBatchController controller = ProduktBatchController.getInstance();
	ProduktBatchKompController controllerKomp = ProduktBatchKompController.getInstance();
	String baseUrl = "http://207.154.253.254:8080/13_CDIO_FINAL/rest/produktbatch/";
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
	void testGetProduktBatchList() {
		try {
			HttpResponse<DTOProduktBatch[]> response = Unirest.get(baseUrl).asObject(DTOProduktBatch[].class);
			DTOProduktBatch[] responseArray = response.getBody();		
			List<DTOProduktBatch> sqlResponseArray = controller.getProduktBatchList();
			if(responseArray.length == sqlResponseArray.size()) {
				for (int i = 0; i < responseArray.length; i++) {
					if(!(responseArray[i].getReceptId() == sqlResponseArray.get(i).getReceptId() &&
							responseArray[i].getPbId() == sqlResponseArray.get(i).getPbId()&&
							responseArray[i].getStatus().equals(sqlResponseArray.get(i).getStatus()))) {
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
	void testCreateProduktBatch() {
		try {
			Unirest.post(baseUrl)
			.header("Content-Type", "application/json").
			body(new DTOProduktBatch(9999,Status.Klar,1))
			.asJson();
			DTOProduktBatch temp = controller.getProduktBatch(9999);
			if(temp.getPbId() == 9999 && temp.getStatus().equals(Status.Klar) && temp.getReceptId() == 1) {
				controller.deleteProduktBatch(9999);
			}else {
				fail("The created product batch was not found or did not match the one created with rest");
			}
		} catch (UnirestException e) {
			e.printStackTrace();
			fail("UniRestexception");

		} catch (DALException e) {
			e.printStackTrace();
			fail("DalException");

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
	void testUpdateProduktBatch() {
		try {
			Unirest.post(baseUrl)
			.header("Content-Type", "application/json")
			.body(new DTOProduktBatch(9999,Status.Klar,1))
			.asJson();
			DTOProduktBatch temp = controller.getProduktBatch(9999);
			if(temp.getPbId() == 9999 && temp.getStatus() == Status.Klar && temp.getReceptId() == 1) {
				Unirest.put(baseUrl)
				.header("Content-Type", "application/json")
				.body(new DTOProduktBatch(9999,Status.Igang,1))
				.asJson();

				DTOProduktBatch temp2 = controller.getProduktBatch(9999);
				if(temp2.getPbId() == 9999 && temp2.getStatus() == Status.Igang && temp.getReceptId() == 1) {
					controller.deleteProduktBatch(9999);
				}else {
					fail("The product batch was not updated properly");
				}
			}else {
				fail("The created product batch was not found or did not match the one created with rest");
			}

		} catch (UnirestException e) {
			e.printStackTrace();
			fail("UniRestexception");
		} catch (DALException e) {
			e.printStackTrace();
			fail("DalException");
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
	void testDeleteProduktBatch() {
		try {
			Unirest.post(baseUrl)
			.header("Content-Type", "application/json").
			body(new DTOProduktBatch(9999,Status.Klar,1))
			.asJson();	
			DTOProduktBatch temp = controller.getProduktBatch(9999);
			if(temp.getPbId() == 9999 && temp.getStatus().equals(Status.Klar) && temp.getReceptId() == 1) {
				Unirest.delete(baseUrl + "{id}")
				.header("Content-Type", "application/json")
				.routeParam("id", "9999")
				.asJson();
				try {
					controller.getProduktBatch(9999);
				} catch (Exception e) {

				}		
			}else {
				fail("The created productbatch was not found or did not match the one created with rest");
			}
		} catch (UnirestException e) {
			e.printStackTrace();
			fail("UniRestexception");

		} catch (DALException e) {
			e.printStackTrace();
			fail("DalException");
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
	void testGetProduktBatchKompList() {
		try {
			HttpResponse<DTOProduktBatchKomp[]> response = Unirest.get(baseUrl+"komponent/"+1).asObject(DTOProduktBatchKomp[].class);
			DTOProduktBatchKomp[] responseArray = response.getBody();		
			List<DTOProduktBatchKomp> sqlResponseArray = controllerKomp.getProduktBatchKomponentList(1);
			if(responseArray.length == sqlResponseArray.size()) {
				for (int i = 0; i < responseArray.length; i++) {
					if(!(responseArray[i].getPbId() == sqlResponseArray.get(i).getPbId() &&
							responseArray[i].getRbId() == sqlResponseArray.get(i).getRbId()&&
							responseArray[i].getTara() == sqlResponseArray.get(i).getTara()&&
							responseArray[i].getNetto() == sqlResponseArray.get(i).getNetto()&&
							responseArray[i].getOprId() == sqlResponseArray.get(i).getOprId())) {
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
	void testCreateProduktBatchKomp() {
		try {
			Unirest.post(baseUrl)
			.header("Content-Type", "application/json").
			body(new DTOProduktBatch(9999,Status.Klar,1))
			.asJson();
			
			Unirest.post(baseUrl+"komponent").
			header("Content-Type", "application/json").
			body(new DTOProduktBatchKomp(9999, 1, 1, 1, 1)).asJson();
		
			DTOProduktBatchKomp tempKomp = controllerKomp.getProduktBatchKomp(9999, 1);
			if(tempKomp.getNetto() == 1 && tempKomp.getOprId() == 1 && tempKomp.getPbId() == 9999 && tempKomp.getRbId() == 1 && tempKomp.getTara() == 1) {
				controllerKomp.deleteProdBatchKomp(9999, 1);
				controller.deleteProduktBatch(9999);
			}else {
				fail("The created productbatchkomp was not found or did not match the one created with rest");
			}
		} catch (UnirestException e) {
			e.printStackTrace();
			fail("UniRestexception");

		} catch (DALException e) {
			e.printStackTrace();
			fail("DalException");

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
	void testUpdateProduktBatchKomp() {
		try {
			Unirest.post(baseUrl)
			.header("Content-Type", "application/json").
			body(new DTOProduktBatch(9999,Status.Klar,1))
			.asJson();
			
			Unirest.post(baseUrl+"komponent").
			header("Content-Type", "application/json").
			body(new DTOProduktBatchKomp(9999, 1, 1, 1, 1)).asJson();
		
			DTOProduktBatchKomp tempKomp = controllerKomp.getProduktBatchKomp(9999, 1);
			if(tempKomp.getNetto() == 1 && tempKomp.getTara() == 1) {
				Unirest.put(baseUrl + "komponent")
				.header("Content-Type", "application/json")
				.body(new DTOProduktBatchKomp(9999, 1, 2, 2, 1))
				.asJson();
				
				DTOProduktBatchKomp temp2 = controllerKomp.getProduktBatchKomp(9999,1);
				
				if(temp2.getNetto() == 2 && temp2.getTara() == 2) {
					controllerKomp.deleteProdBatchKomp(9999, 1);
					controller.deleteProduktBatch(9999);
				}else {
					fail("The productbatchkomp was not updated properly");
				}
			}else {
				fail("The created productbatchkomp was not found or did not match the one created with rest");
			}

		} catch (UnirestException e) {
			e.printStackTrace();
			fail("UniRestexception");
		} catch (DALException e) {
			e.printStackTrace();
			fail("DalException");
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
	void testDeleteProduktBatchKomp() {
		try {
			Unirest.post(baseUrl)
			.header("Content-Type", "application/json").
			body(new DTOProduktBatch(9999,Status.Klar,1))
			.asJson();
			
			Unirest.post(baseUrl+"komponent").
			header("Content-Type", "application/json").
			body(new DTOProduktBatchKomp(9999, 1, 1, 1, 1)).asJson();
			
			Unirest.delete(baseUrl + "komponent/{id}/{id2}")
			.header("Content-Type", "application/json")
			.routeParam("id", "9999")
			.routeParam("id2", "1")
			.asJson();
				try {
					controllerKomp.getProduktBatchKomp(9999,1);
					
				} catch (Exception e) {
					
				}		
				controller.deleteProduktBatch(9999);
		} catch (UnirestException e) {
			e.printStackTrace();
			fail("UniRestexception");
		} catch (DALException e) {
			e.printStackTrace();
			fail("DALException");
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
