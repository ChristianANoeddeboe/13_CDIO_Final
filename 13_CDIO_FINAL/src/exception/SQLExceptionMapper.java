package exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.sql.SQLException;
@Provider
public class SQLExceptionMapper implements ExceptionMapper<SQLException> {
	@Override
	public Response toResponse(SQLException arg0) {
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database SQL fejl kode: "+arg0.getErrorCode()+" - "+arg0.getSQLState()).build();
	}
}
