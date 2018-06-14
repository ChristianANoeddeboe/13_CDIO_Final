package rest;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import connector.MySQLConnector;
import controller.ReceptController;
import controller.ReceptKompController;
import dto.DTORecept;
import dto.DTOReceptKomp;
import exception.DALException;
import interfaces.IReceptController;
import interfaces.IReceptKompController;
import interfaces.IReceptService;

@Path("recept")
@Produces(MediaType.APPLICATION_JSON)
public class ReceptService implements IReceptService {
	
	IReceptController controller = ReceptController.getInstance();
	IReceptKompController kompController = ReceptKompController.getInstance();
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public Response getReceptList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {	
		List<DTORecept> result = null;
		try {
			result = controller.getReceptList();
		} catch(DALException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("DALException: "+e.getMessage()).build();
		}
        return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public Response createRecept(DTORecept recept) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {		
		try {
			controller.createRecept(recept);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.status(Response.Status.CREATED).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public Response updateRecept(DTORecept recept) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {				
		try {
			controller.updateRecept(recept);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	
	@DELETE
	@Path("{id}")
	@Override
	public Response deleteRecept(@PathParam("id") int id) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {	
		try {
			controller.deleteRecept(id);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	

	
	@GET
	@Path("komponent/{receptId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getReceptKompList(@PathParam("receptId") int receptId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {	
		List<DTOReceptKomp> result = null;
		try {
			result = kompController.getReceptKompList(receptId);
		} catch(DALException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("DALException: "+e.getMessage()).build();
		}
        return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("komponent")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createReceptKomp(DTOReceptKomp receptkomponent) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {	
		try {
			kompController.createReceptKomp(receptkomponent);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

	@PUT
	@Path("komponent")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateReceptKomp(DTOReceptKomp receptkomponent) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		try {
			kompController.updateReceptKomp(receptkomponent);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

	@DELETE
	@Path("komponent/{receptId}/{raavareId}")
	public Response deleteReceptKomp(@PathParam("receptId") int receptId, @PathParam("raavareId") int raavareId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		try {
			kompController.deleteReceptKomp(receptId, raavareId);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}
}
