package interfaces;

import java.util.List;

import daointerfaces01917.DALException;
import dto.RaavareBatchDTO;

public interface RaavareBatchDAO {
	RaavareBatchDTO getRaavareBatch(int rbId) throws DALException;
	List<RaavareBatchDTO> getRaavareBatchList() throws DALException;
	List<RaavareBatchDTO> getRaavareBatchList(int raavareId) throws DALException;
	void createRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException;
	void updateRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException;
	void deleteRaavareBatch(int raavarebatch_ID) throws DALException;
	
}

