package interfaces;

import dto.DTOReceptKomp;
import exception.DALException;

import java.util.List;

public interface IDAOReceptKomp {
	DTOReceptKomp getReceptKomp(int receptId, int raavareId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	List<DTOReceptKomp> getReceptKompList(int receptId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	List<DTOReceptKomp> getReceptKompList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	void createReceptKomp(DTOReceptKomp receptkomponent) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	void updateReceptKomp(DTOReceptKomp receptkomponent) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	void deleteReceptKomp(int recept_id, int raavare_id) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	
}
