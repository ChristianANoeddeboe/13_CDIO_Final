package rest;

import controller.BrugerController;
import dto.DTOBruger;
import exception.DALException;
import interfaces.IBrugerController;
import interfaces.IBrugerService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("bruger")
@Produces(MediaType.APPLICATION_JSON)

public class BrugerService implements IBrugerService {

	IBrugerController controller = BrugerController.getInstance();
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getBrugerList() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
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
	public Response createBruger(DTOBruger bruger) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		try {
			controller.createBruger(bruger);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();

	}

	@Override
	@PUT
	public Response updateBruger(DTOBruger bruger) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
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
	public Response deleteBruger(@PathParam("id") int bruger_id) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		try {
			controller.deleteBruger(bruger_id);
		} catch(DALException e) {
			return Response.status(Response.Status.OK).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

}
