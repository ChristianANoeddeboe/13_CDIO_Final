package interfaces;

import java.util.List;

import dto.LeverandoerDTO;
import exception.DALException;

public interface LeverandoerDAO {
	LeverandoerDTO getRaavare(int raavareId) throws DALException;
	List<LeverandoerDTO> getRaavareList() throws DALException;
	void createRaavare(LeverandoerDTO raavare) throws DALException;
	void updateRaavare(LeverandoerDTO raavare) throws DALException;
	void deleteRaavare(int recept_id) throws DALException;
}
