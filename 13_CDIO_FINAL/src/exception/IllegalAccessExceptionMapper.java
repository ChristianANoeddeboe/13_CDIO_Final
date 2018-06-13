package exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
@Provider
public class IllegalAccessExceptionMapper implements ExceptionMapper<IllegalAccessException> {
	@Override
	public Response toResponse(IllegalAccessException arg0) {
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Illegal access fejl, tjek server log").build();
	}
}
