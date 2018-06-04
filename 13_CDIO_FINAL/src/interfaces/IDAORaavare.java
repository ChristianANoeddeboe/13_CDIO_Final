package interfaces;

import java.util.List;

import exception.DALException;
import dto.DTORaavare;

public interface IDAORaavare {
	DTORaavare getRaavare(int raavareId) throws DALException;
	List<DTORaavare> getRaavareList() throws DALException;
	void createRaavare(DTORaavare raavare) throws DALException;
	void updateRaavare(DTORaavare raavare) throws DALException;
	void deleteRaavare(int recept_id) throws DALException;
}
