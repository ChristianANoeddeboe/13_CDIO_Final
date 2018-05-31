package interfaces;

import java.util.List;

import daointerfaces01917.DALException;
import dto.RaavareDTO;

public interface RaavareDAO {
	RaavareDTO getRaavare(int raavareId) throws DALException;
	List<RaavareDTO> getRaavareList() throws DALException;
	void createRaavare(RaavareDTO raavare) throws DALException;
	void updateRaavare(RaavareDTO raavare) throws DALException;
	void deleteRaavare(int recept_id) throws DALException;
}
