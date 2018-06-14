package interfaces;

import java.util.List;

import dto.DTOReceptKomp;
import exception.DALException;

public interface IReceptKompController {

	DTOReceptKomp getReceptKomp(int receptID, int raavarID)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	List<DTOReceptKomp> getReceptKompList(int receptID)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	List<DTOReceptKomp> getReceptKompList()
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	void createReceptKomp(DTOReceptKomp receptKompDTO)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	void updateReceptKomp(DTOReceptKomp receptKompDTO)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	void deleteReceptKomp(int receptKompID, int raavareID)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

}