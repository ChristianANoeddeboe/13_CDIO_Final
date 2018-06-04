package interfaces;

import java.util.List;

import exception.DALException;
import dto.DTOOperatoer;

public interface IDAOOperatoer {
	DTOOperatoer getOperatoer(int oprId) throws DALException;
	List<DTOOperatoer> getOperatoerList() throws DALException;
	void createOperatoer(DTOOperatoer opr) throws DALException;
	void updateOperatoer(DTOOperatoer opr) throws DALException;
	void deleteOperatoer(int opr_id) throws DALException;
}
