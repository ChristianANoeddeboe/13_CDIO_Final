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
import controller.RaavareController;
import controller.RaavareBatchController;
import dao.DAORaavare;
import dao.DAORaavareBatch;
import dto.DTORaavare;
import dto.DTORaavareBatch;

import java.sql.SQLException;
import java.util.List;
import exception.DALException;
import interfaces.IRaavareService;

@Path("raavare")
@Produces(MediaType.APPLICATION_JSON)
public class RaavareService implements IRaavareService {
	static RaavareController rController = new RaavareController(new DAORaavare());
	static RaavareBatchController rbController = new RaavareBatchController(new DAORaavareBatch());
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getRaavareList() throws DALException {
		try {
			new MySQLConnector();
		} catch(InstantiationException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Instationtion fejl, tjek server log").build();
		} catch(IllegalAccessException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Illegal access fejl, tjek server log").build();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Klasse ikke fundet, tjek server log").build();
		} catch(SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database SQL fejl kode: "+e.getErrorCode()+" - "+e.getSQLState()).build();
		}
		
		List<DTORaavare> result = null;
		try {
			result = rController.getRaavreList();
		} catch(DALException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createRaavare(DTORaavare raavare) throws DALException {
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
			rController.createRaavare(raavare);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

	@PUT
	@Path("update")
	public Response updateRaavare(DTORaavare raavare) throws DALException {
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
			rController.updateRaavare(raavare);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

	@DELETE
	@Path("{id}")
	public Response deleteRaavare(@PathParam("id") int raavareId) throws DALException {
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
			rController.deleteRaavare(raavareId);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

	@GET
	@Path("batch/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getRaavareBatch(@PathParam("id") int rbId) throws DALException {
		try {
			new MySQLConnector();
		} catch(InstantiationException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Instationtion fejl, tjek server log").build();
		} catch(IllegalAccessException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Illegal access fejl, tjek server log").build();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Klasse ikke fundet, tjek server log").build();
		} catch(SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database SQL fejl kode: "+e.getErrorCode()+" - "+e.getSQLState()).build();
		}
		DTORaavareBatch result = null;
		try {
			result = rbController.getRaavareBatch(rbId);
		} catch(DALException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("batch/list/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getRaavareBatchList() throws DALException {
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
		
		List<DTORaavareBatch> result = null;
		try {
			result = rbController.getRaavareBatchList();
		} catch(DALException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("DALException: "+e.getMessage()).build();
		}
        return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("batch/list/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getRaavareBatchList(@PathParam("id") int raavareId) throws DALException {
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
		
		List<DTORaavareBatch> result = null;
		try {
			result = rbController.getRaavareBatchList(raavareId);
		} catch(DALException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("DALException: "+e.getMessage()).build();
		}
        return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("batch/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createRaavareBatch(DTORaavareBatch raavarebatch) throws DALException {
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
			rbController.createRaavsareBatch(raavarebatch);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

	@PUT
	@Path("batch/update")
	public Response updateRaavareBatch(DTORaavareBatch raavarebatch) throws DALException {
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
			rbController.updateRaavareBatch(raavarebatch);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

	@DELETE
	@Path("batch/{id}")
	public Response deleteRaavareBatch(@PathParam("id") int raavarebatch_ID) throws DALException {
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
			rbController.deleteRaavareBatch(raavarebatch_ID);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

}
