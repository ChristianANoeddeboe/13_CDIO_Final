package interfaces;

import java.util.List;

import exception.DALException;
import dto.DTORecept;

public interface IDAORecept {
	DTORecept getRecept(int receptId) throws DALException;
	List<DTORecept> getReceptList() throws DALException;
	void createRecept(DTORecept recept) throws DALException;
	void updateRecept(DTORecept recept) throws DALException;
	void deleteRecept(int input_id) throws DALException;
}
