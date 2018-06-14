package interfaces;

import java.util.List;

import exception.DALException;
import dto.DTOBruger;

public interface IDAOOperatoer {
	DTOBruger getOperatoer(int oprId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	List<DTOBruger> getOperatoerList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	void createOperatoer(DTOBruger opr) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	void updateOperatoer(DTOBruger opr) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	void deleteOperatoer(int opr_id) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
}
