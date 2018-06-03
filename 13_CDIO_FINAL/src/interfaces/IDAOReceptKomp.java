package interfaces;

import java.util.List;

import exception.DALException;
import dto.DTOReceptKomp;

public interface IDAOReceptKomp {
	DTOReceptKomp getReceptKomp(int receptId, int raavareId) throws DALException;
	List<DTOReceptKomp> getReceptKompList(int receptId) throws DALException;
	List<DTOReceptKomp> getReceptKompList() throws DALException;
	void createReceptKomp(DTOReceptKomp receptkomponent) throws DALException;
	void updateReceptKomp(DTOReceptKomp receptkomponent) throws DALException;
	void deleteReceptKomp(int recept_id, int raavare_id) throws DALException;
	
}
