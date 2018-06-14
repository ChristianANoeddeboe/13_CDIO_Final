package interfaces;

import java.util.List;

import exception.DALException;
import dto.DTORaavare;

public interface IDAORaavare {
	DTORaavare getRaavare(int raavareId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	List<DTORaavare> getRaavareList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	void createRaavare(DTORaavare raavare) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	void updateRaavare(DTORaavare raavare) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	void deleteRaavare(int recept_id) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
}
