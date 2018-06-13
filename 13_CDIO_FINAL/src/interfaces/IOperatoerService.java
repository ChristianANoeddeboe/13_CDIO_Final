package interfaces;

import java.sql.SQLException;

import javax.ws.rs.core.Response;

import dto.DTOOperatoer;
import exception.DALException;

public interface IOperatoerService {
	Response getOperatoerList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	Response createOperatoer(DTOOperatoer opr) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	Response updateOperatoer(DTOOperatoer opr) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	Response deleteOperatoer(int opr_id) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
}
