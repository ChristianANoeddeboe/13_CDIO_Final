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

import controller.RaavareController;

import controller.RaavareBatchController;
import controller.RaavareController;
import dto.DTORaavare;
import dto.DTORaavareBatch;
import exception.DALException;
import interfaces.IRaavareBatchController;
import interfaces.IRaavareController;
import interfaces.IRaavareService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("raavare")
@Produces(MediaType.APPLICATION_JSON)
public class RaavareService implements IRaavareService {
	IRaavareController rController = RaavareController.getInstance();
	IRaavareBatchController rbController = RaavareBatchController.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getRaavareList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<DTORaavare> result = null;
		try {
			result = rController.getRaavreList();
		} catch(DALException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createRaavare(DTORaavare raavare) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		try {
			rController.createRaavare(raavare);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

	@PUT
	public Response updateRaavare(DTORaavare raavare) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		try {
			rController.updateRaavare(raavare);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

	@DELETE
	@Path("{id}")
	public Response deleteRaavare(@PathParam("id") int raavareId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
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
	public Response getRaavareBatchList(@PathParam("id") int raavareId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<DTORaavareBatch> result = null;
		try {
			result = rbController.getRaavareBatchList(raavareId);
		} catch(DALException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("DALException: "+e.getMessage()).build();
		}
        return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("batch")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createRaavareBatch(DTORaavareBatch raavarebatch) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		
		try {
			rbController.createRaavareBatch(raavarebatch);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

	@PUT
	@Path("batch")
	public Response updateRaavareBatch(DTORaavareBatch raavarebatch) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		try {
			rbController.updateRaavareBatch(raavarebatch);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

	@DELETE
	@Path("batch/{id}")
	public Response deleteRaavareBatch(@PathParam("id") int raavarebatch_ID) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		try {
			rbController.deleteRaavareBatch(raavarebatch_ID);
		} catch(DALException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("DALException: "+e.getMessage()).build();
		}
		return Response.ok().build();
	}

}
