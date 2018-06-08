package rest;

import java.sql.SQLException;
import java.util.ArrayList;
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
import javax.ws.rs.core.Response.Status;

import connector.MySQLConnector;
import controller.ReceptController;
import controller.ReceptKompController;
import dao.DAORecept;
import dao.DAOReceptKomp;
import dto.DTORecept;
import dto.DTOReceptKomp;
import exception.DALException;
import interfaces.IReceptService;

@Path("recept")
@Produces(MediaType.APPLICATION_JSON)
public class ReceptService implements IReceptService {
	
	static ReceptController controller = new ReceptController(new DAORecept());
	static ReceptKompController kompController = new ReceptKompController(new DAOReceptKomp());
	
	@GET
	@Path("all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public Response getReceptList() throws DALException {
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
		
		List<DTORecept> result = null;
		try {
			result = controller.getReceptList();
		} catch(DALException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("DALException: "+e.getMessage()).build();
		}
        return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public Response createRecept(DTORecept recept) throws DALException {		
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
			controller.createRecept(recept);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.status(Response.Status.CREATED).build();
	}
	
	@PUT
	@Path("update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public Response updateRecept(DTORecept recept) throws DALException {		
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
			controller.updateRecept(recept);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	
	@DELETE
	@Path("{id}")
	@Override
	public Response deleteRecept(@PathParam("id") int id) throws DALException {	
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
			controller.deleteRecept(id);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	
	@GET
	@Path("komponent/{receptId}/{raavareId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getReceptKomp(@PathParam("receptId") int receptId,@PathParam("raavareId") int raavareId) throws DALException {
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
		DTOReceptKomp result = null;
		try {
			result = kompController.getReceptKomp(receptId, raavareId);
		} catch(DALException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}
	
	
	@GET
	@Path("komponent/list/{receptId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getReceptKompList(@PathParam("receptId") int receptId) throws DALException {
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
		
		List<DTOReceptKomp> result = null;
		try {
			result = kompController.getReceptKompList(receptId);
		} catch(DALException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("DALException: "+e.getMessage()).build();
		}
        return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("komponent/list/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getReceptKompList() throws DALException {
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
		
		List<DTOReceptKomp> result = null;
		try {
			result = kompController.getReceptKompList();
		} catch(DALException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("DALException: "+e.getMessage()).build();
		}
        return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("komponent/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createReceptKomp(DTOReceptKomp receptkomponent) throws DALException {
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
			kompController.createReceptKomp(receptkomponent);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

	@PUT
	@Path("komponent/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateReceptKomp(DTOReceptKomp receptkomponent) throws DALException {
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
			kompController.updateReceptKomp(receptkomponent);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

	@DELETE
	@Path("komponent/{receptId}/{raavareId}")
	public Response deleteReceptKomp(@PathParam("receptId") int receptId, @PathParam("raavareId") int raavareId) throws DALException {
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
			kompController.deleteReceptKomp(receptId, raavareId);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}
}
