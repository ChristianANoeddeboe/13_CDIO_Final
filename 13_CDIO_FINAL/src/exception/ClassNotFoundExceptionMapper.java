package exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
@Provider
public class ClassNotFoundExceptionMapper implements ExceptionMapper<ClassNotFoundException> {
	@Override
	public Response toResponse(ClassNotFoundException arg0) {
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Klasse ikke fundet, tjek server log").build();
	}
}
