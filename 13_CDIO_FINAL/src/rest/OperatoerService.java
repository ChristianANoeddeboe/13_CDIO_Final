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
import dao.DAOOperatoer;
import dto.DTOOperatoer;

import java.sql.SQLException;
import java.util.List;
import exception.DALException;
import interfaces.IOperatoerService;

@Path("operatoer")
@Produces(MediaType.APPLICATION_JSON)

public class OperatoerService implements IOperatoerService {
	
	static OperatoerController controller = new OperatoerController(new DAOOperatoer());
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getOperatoerList() throws DALException {
		try {
			new MySQLConnector();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Instationtion fejl, tjek server log").build();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Illegal access fejl, tjek server log").build();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Klasse ikke fundet, tjek server log").build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database SQL fejl kode: "+e.getErrorCode()+" - "+e.getSQLState()).build();
		}
		
		List<DTOOperatoer> result = null;
		try {
			result = controller.getOperatoerList();
		} catch(DALException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("DALException: "+e.getMessage()).build();
		}
        return Response.ok(result, MediaType.APPLICATION_JSON).build();
	
	}

	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOperatoer(DTOOperatoer opr) throws DALException {
		try {
			new MySQLConnector();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Instationtion fejl, tjek server log").build();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Illegal access fejl, tjek server log").build();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Klasse ikke fundet, tjek server log").build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database SQL fejl kode: "+e.getErrorCode()+" - "+e.getSQLState()).build();
		}
		
		try {
			controller.createOperatoer(opr);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
		
	}

	@Override
	@PUT
	@Path("update")
	public Response updateOperatoer(DTOOperatoer opr) throws DALException {
		try {
			new MySQLConnector();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Instationtion fejl, tjek server log").build();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Illegal access fejl, tjek server log").build();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Klasse ikke fundet, tjek server log").build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database SQL fejl kode: "+e.getErrorCode()+" - "+e.getSQLState()).build();
		}
		
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
	public Response deleteOperatoer(@PathParam("id") int opr_id) throws DALException {
		try {
			new MySQLConnector();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Instationtion fejl, tjek server log").build();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Illegal access fejl, tjek server log").build();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Klasse ikke fundet, tjek server log").build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database SQL fejl kode: "+e.getErrorCode()+" - "+e.getSQLState()).build();
		}
		
		try {
			controller.deleteOperatoer(opr_id);
		} catch(DALException e) {
			return Response.status(Response.Status.OK).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}
	
}
