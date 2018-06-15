package interfaces;

import dto.DTORaavare;
import exception.DALException;

import java.util.List;

public interface IRaavareController {

	DTORaavare getRaavare(int raavareID)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	List<DTORaavare> getRaavreList()
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	void createRaavare(DTORaavare raavare)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	void updateRaavare(DTORaavare raavare)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	void deleteRaavare(int raavareID)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

}