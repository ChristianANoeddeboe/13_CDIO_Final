package rest;

import java.util.List;

import javax.ws.rs.core.Response;

import dto.DTORaavare;
import dto.DTORaavareBatch;
import exception.DALException;

public interface IRaavareService {
	//Råvare
	DTORaavare getRaavare(int raavareId) throws DALException;
	List<DTORaavare> getRaavareList() throws DALException;
	Response createRaavare(DTORaavare raavare) throws DALException;
	Response updateRaavare(DTORaavare raavare) throws DALException;
	Response deleteRaavare(int recept_id) throws DALException;
	//Råvarebatch
	DTORaavareBatch getRaavareBatch(int rbId) throws DALException;
	DTORaavareBatch getRaavareBatchRaavare(int raavareID) throws DALException;
	List<DTORaavareBatch> getRaavareBatchList() throws DALException;
	List<DTORaavareBatch> getRaavareBatchList(int raavareId) throws DALException;
	Response createRaavareBatch(DTORaavareBatch raavarebatch) throws DALException;
	Response updateRaavareBatch(DTORaavareBatch raavarebatch) throws DALException;
	Response deleteRaavareBatch(int raavarebatch_ID) throws DALException;
}
