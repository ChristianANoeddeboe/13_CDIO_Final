package interfaces;

import java.util.List;

import dto.DTOLeverandoer;
import exception.DALException;

public interface IDAOLeverandoer {
	DTOLeverandoer getRaavare(int raavareId) throws DALException;
	List<DTOLeverandoer> getRaavareList() throws DALException;
	void createRaavare(DTOLeverandoer raavare) throws DALException;
	void updateRaavare(DTOLeverandoer raavare) throws DALException;
	void deleteRaavare(int recept_id) throws DALException;
}
