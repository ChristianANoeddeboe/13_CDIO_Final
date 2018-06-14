package exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InstantiationExceptionMapper implements ExceptionMapper<InstantiationException> {

	@Override
	public Response toResponse(InstantiationException arg0) {
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Instationtion fejl, tjek server log").build();
	}

}
