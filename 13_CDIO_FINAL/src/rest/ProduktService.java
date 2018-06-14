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
import controller.ProduktBatchController;
import controller.ProduktBatchKompController;
import dto.DTOProduktBatch;
import dto.DTOProduktBatchKomp;
import exception.DALException;
import interfaces.IProduktBatchController;
import interfaces.IProduktBatchKompController;
import interfaces.IProduktService;

@Path("produktbatch")
@Produces(MediaType.APPLICATION_JSON)

public class ProduktService implements IProduktService {
	IProduktBatchController controller = ProduktBatchController.getInstance();
	IProduktBatchKompController kompController = ProduktBatchKompController.getInstance();

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getProduktBatchList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {


		List<DTOProduktBatch> result = null;
		try {
			result = controller.getProduktBatchList();
		} catch(DALException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createProduktBatch(DTOProduktBatch produktbatch) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try {
			controller.createProduktBatch(produktbatch);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProduktBatch(DTOProduktBatch produktbatch) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try {
			controller.updateProduktBatch(produktbatch);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

	@DELETE
	@Path("{id}")
	public Response deleteProduktBatch(@PathParam("id") int pbID) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try {
			controller.deleteProduktBatch(pbID);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

	//KOMPONENTER



	@GET
	@Path("komponent/{pbId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getProduktBatchKompList(@PathParam("pbId")int pbId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<DTOProduktBatchKomp> result = null;
		try {
			result = kompController.getProduktBatchKomponentList(pbId);
		} catch(DALException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("komponent")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createProduktBatchKomp(DTOProduktBatchKomp produktbatchkomponent) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try {
			kompController.createProdBatchKomp(produktbatchkomponent);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

	@PUT
	@Path("komponent")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProduktBatchKomp(DTOProduktBatchKomp produktbatchkomponent) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try {
			kompController.updateProdBatchKomp(produktbatchkomponent);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

	@DELETE
	@Path("komponent/{pbid}/{rbid}")
	public Response deleteProduktBatchKomp(@PathParam("pbid") int productBatch_ID, @PathParam("rbid") int raavareBatch_ID) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try {
			kompController.deleteProdBatchKomp(productBatch_ID, raavareBatch_ID);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}


}
