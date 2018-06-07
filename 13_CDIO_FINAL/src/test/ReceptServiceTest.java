package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

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

import dto.DTORecept;

class ReceptServiceTest {
	
	String baseUrl = "http://207.154.253.254:8080/13_CDIO_FINAL/rest/recept/";
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
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
		
		System.out.println("a");
		try {
			Options.refresh();			
			System.out.println("a");
			HttpResponse<DTORecept[]> response = Unirest.get(baseUrl+"all").asObject(DTORecept[].class);
			System.out.println("a");
		} catch (UnirestException e) {
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
