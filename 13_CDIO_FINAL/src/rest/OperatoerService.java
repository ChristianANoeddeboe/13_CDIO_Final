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

import connector.MySQLConnector;
import controller.*;
import dto.DTOOperatoer;

import java.sql.SQLException;
import java.util.List;
import exception.DALException;
import interfaces.IOperatoerService;

@Path("operatoer")
@Produces(MediaType.APPLICATION_JSON)

public class OperatoerService implements IOperatoerService {

	OperatoerController controller = OperatoerController.getInstance();
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getOperatoerList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<DTOOperatoer> result = null;
		try {
			result = controller.getOperatoerList();
		} catch(DALException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok(result, MediaType.APPLICATION_JSON).build();

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOperatoer(DTOOperatoer opr) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try {
			controller.createOperatoer(opr);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();

	}

	@Override
	@PUT
	public Response updateOperatoer(DTOOperatoer opr) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try {
			controller.updateOperatoer(opr);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();

	}

	@Override
	@DELETE
	@Path("{id}")
	public Response deleteOperatoer(@PathParam("id") int opr_id) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try {
			controller.deleteOperatoer(opr_id);
		} catch(DALException e) {
			return Response.status(Response.Status.OK).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

}
