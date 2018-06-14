package rest;

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

import controller.*;
import dto.DTOBruger;

import java.sql.SQLException;
import java.util.List;
import exception.DALException;
import interfaces.IBrugerController;
import interfaces.IBrugerService;

@Path("bruger")
@Produces(MediaType.APPLICATION_JSON)

public class BrugerService implements IBrugerService {

	IBrugerController controller = BrugerController.getInstance();
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getBrugerList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<DTOBruger> result = null;
		try {
			result = controller.getBrugerList();
		} catch(DALException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok(result, MediaType.APPLICATION_JSON).build();

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createBruger(DTOBruger bruger) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try {
			controller.createBruger(bruger);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();

	}

	@Override
	@PUT
	public Response updateBruger(DTOBruger bruger) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try {
			controller.updateBruger(bruger);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();

	}

	@Override
	@DELETE
	@Path("{id}")
	public Response deleteBruger(@PathParam("id") int bruger_id) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try {
			controller.deleteBruger(bruger_id);
		} catch(DALException e) {
			return Response.status(Response.Status.OK).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

}
