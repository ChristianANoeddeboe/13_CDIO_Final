package interfaces;

import java.util.List;

import exception.DALException;
import dto.DTORaavareBatch;

public interface IDAORaavareBatch {
	DTORaavareBatch getRaavareBatch(int rbId) throws DALException;
	DTORaavareBatch getRaavareBatchRaavare(int raavareID) throws DALException;
	List<DTORaavareBatch> getRaavareBatchList() throws DALException;
	List<DTORaavareBatch> getRaavareBatchList(int raavareId) throws DALException;
	void createRaavareBatch(DTORaavareBatch raavarebatch) throws DALException;
	void updateRaavareBatch(DTORaavareBatch raavarebatch) throws DALException;
	void deleteRaavareBatch(int raavarebatch_ID) throws DALException;
	
}

