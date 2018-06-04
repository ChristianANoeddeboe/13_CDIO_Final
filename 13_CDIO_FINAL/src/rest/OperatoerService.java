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
import javax.ws.rs.core.Response.Status;

import connector.MySQLConnector;
import dao.DAOOperatoer;
import dto.DTOOperatoer;

import java.sql.SQLException;
import java.util.List;
import exception.DALException;

@Path("operatoer")
@Produces(MediaType.APPLICATION_JSON)

public class OperatoerService implements IOperatoerService {
	static DAOOperatoer dao = new DAOOperatoer();
	
	@GET
	@Path("id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DTOOperatoer getOperatoer(@PathParam("id") int oprId) throws DALException {
		return dao.getOperatoer(oprId);
	}

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getOperatoerList() throws DALException {
		List<DTOOperatoer> result = null;
        try {
        	new MySQLConnector();
            result = dao.getOperatoerList();
        } catch(IndexOutOfBoundsException e){
	   		return Response.status(Response.Status.BAD_GATEWAY).entity("Brugeren eksisterer ikke").build();
        } catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return Response.ok(result, MediaType.APPLICATION_JSON).build();
	
	}

	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOperatoer(DTOOperatoer opr) throws DALException {
		return null;
		
	}

	@Override
	@PUT
	@Path("update")
	public Response updateOperatoer(DTOOperatoer opr) throws DALException {
		return null;
		
	}

	@Override
	@DELETE
	@Path("delete")
	public Response deleteOperatoer(int opr_id) throws DALException {
		return null;
		
	}
	
}
