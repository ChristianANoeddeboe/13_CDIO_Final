package interfaces;

import java.util.List;

import exception.DALException;
import dto.ReceptDTO;

public interface ReceptDAO {
	ReceptDTO getRecept(int receptId) throws DALException;
	List<ReceptDTO> getReceptList() throws DALException;
	void createRecept(ReceptDTO recept) throws DALException;
	void updateRecept(ReceptDTO recept) throws DALException;
	void deleteRecept(int input_id) throws DALException;
}
