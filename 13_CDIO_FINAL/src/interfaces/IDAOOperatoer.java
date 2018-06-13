package interfaces;

import java.util.List;

import exception.DALException;
import dto.DTOOperatoer;

public interface IDAOOperatoer {
	DTOOperatoer getOperatoer(int oprId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	List<DTOOperatoer> getOperatoerList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	void createOperatoer(DTOOperatoer opr) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	void updateOperatoer(DTOOperatoer opr) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	void deleteOperatoer(int opr_id) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
}
