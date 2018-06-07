package rest;

import javax.ws.rs.core.Response;

import dto.DTOOperatoer;
import exception.DALException;

public interface IOperatoerService {
	Response getOperatoerList() throws DALException;
	Response createOperatoer(DTOOperatoer opr) throws DALException;
	Response updateOperatoer(DTOOperatoer opr) throws DALException;
	Response deleteOperatoer(int opr_id) throws DALException;
}
