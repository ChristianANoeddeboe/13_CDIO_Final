package rest;

import java.util.List;

import dto.DTORaavare;
import dto.DTORaavareBatch;
import exception.DALException;

public interface IRaavareService {
	//Råvare
	DTORaavare getRaavare(int raavareId) throws DALException;
	List<DTORaavare> getRaavareList() throws DALException;
	void createRaavare(DTORaavare raavare) throws DALException;
	void updateRaavare(DTORaavare raavare) throws DALException;
	void deleteRaavare(int recept_id) throws DALException;
	//Råvarebatch
	DTORaavareBatch getRaavareBatch(int rbId) throws DALException;
	DTORaavareBatch getRaavareBatchRaavare(int raavareID) throws DALException;
	List<DTORaavareBatch> getRaavareBatchList() throws DALException;
	List<DTORaavareBatch> getRaavareBatchList(int raavareId) throws DALException;
	void createRaavareBatch(DTORaavareBatch raavarebatch) throws DALException;
	void updateRaavareBatch(DTORaavareBatch raavarebatch) throws DALException;
	void deleteRaavareBatch(int raavarebatch_ID) throws DALException;
}
