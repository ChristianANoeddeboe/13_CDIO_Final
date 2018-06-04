package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import connector.MySQLConnector;
import dao.DAORecept;
import dto.DTORecept;
import exception.DALException;

@Path("recept")
public class ReceptService {
	
	@GET
	@Path("display")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response displayRecept() {
		System.out.println("aa");
		List<DTORecept> tempReceptList = new ArrayList<>();
		DAORecept tempdao = new DAORecept();

		try {
			new MySQLConnector();
			tempReceptList = tempdao.getReceptList();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return Response.ok(tempReceptList,MediaType.APPLICATION_JSON).build();
	}
}
