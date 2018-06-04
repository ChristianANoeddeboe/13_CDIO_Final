package rest;

import java.util.List;

import dto.DTOOperatoer;
import exception.DALException;

public interface IOperatoerService {
	DTOOperatoer getOperatoer(int oprId) throws DALException;
	List<DTOOperatoer> getOperatoerList() throws DALException;
	void createOperatoer(DTOOperatoer opr) throws DALException;
	void updateOperatoer(DTOOperatoer opr) throws DALException;
	void deleteOperatoer(int opr_id) throws DALException;
}
