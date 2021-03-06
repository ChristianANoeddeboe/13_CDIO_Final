package interfaces;

import dto.DTORecept;
import exception.DALException;

import java.util.List;

public interface IDAORecept {
	DTORecept getRecept(int receptId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	List<DTORecept> getReceptList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	void createRecept(DTORecept recept) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	void updateRecept(DTORecept recept) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	void deleteRecept(int input_id) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
}
