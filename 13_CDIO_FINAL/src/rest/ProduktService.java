package rest;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import connector.MySQLConnector;
import dao.DAOProduktBatchKomp;
import dao.DAOProduktBatch;
import dto.DTOOperatoer;
import dto.DTOProduktBatch;
import dto.DTOProduktBatchKomp;
import exception.DALException;

@Path("produkt")
@Produces(MediaType.APPLICATION_JSON)

public class ProduktService implements IProduktService {
	static DAOProduktBatch dao = new DAOProduktBatch();
	static DAOProduktBatchKomp daoKomp = new DAOProduktBatchKomp();
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getProduktBatch(@PathParam("id") int pbId) throws DALException {
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
		DTOProduktBatch result = null;
		try {
			result = dao.getProduktBatch(pbId);
		} catch(DALException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}
	

	@Override
	public Response getProduktBatchList() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response createProduktBatch(DTOProduktBatch produktbatch) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response updateProduktBatch(DTOProduktBatch produktbatch) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response deleteProduktBatch(int pbID) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getProduktBatchKomp(int pbId, int rbId) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getProduktBatchKompList(int pbId) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getProduktBatchKompList() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response createProduktBatchKomp(DTOProduktBatchKomp produktbatchkomponent) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response updateProduktBatchKomp(DTOProduktBatchKomp produktbatchkomponent) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response deleteProduktBatchKomp(int productBatch_ID, int raavareBatch_ID) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}


}
