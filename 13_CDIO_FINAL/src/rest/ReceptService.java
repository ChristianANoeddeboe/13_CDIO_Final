package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import connector.MySQLConnector;
import dao.DAORecept;
import dto.DTORecept;
import dto.DTOReceptKomp;
import exception.DALException;

@Path("recept")
public class ReceptService implements IReceptService {
	


	@Override
	public Response getRecept(int receptId) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@GET
	@Path("display")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getReceptList() throws DALException {
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

	@Override
	public Response createRecept(DTORecept recept) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response updateRecept(DTORecept recept) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@DELETE
	@Path("{id}")
	@Override
	public Response deleteRecept(@PathParam("id") int id) throws DALException {
		DAORecept tempdao = new DAORecept();

		try {
			new MySQLConnector();
			tempdao.deleteRecept(id);
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity("Constraints issues").build();
		}
		return Response.ok().build();
	}

	@Override
	public Response getReceptKomp(int receptId, int raavareId) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getReceptKompList(int receptId) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getReceptKompList() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response createReceptKomp(DTOReceptKomp receptkomponent) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response updateReceptKomp(DTOReceptKomp receptkomponent) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response deleteReceptKomp(int recept_id, int raavare_id) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}
}
