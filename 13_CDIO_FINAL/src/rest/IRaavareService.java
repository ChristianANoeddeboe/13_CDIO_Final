package rest;

import javax.ws.rs.core.Response;

import dto.DTORaavare;
import dto.DTORaavareBatch;
import exception.DALException;

public interface IRaavareService {
	//Råvare
	Response getRaavare(int raavareId) throws DALException;
	Response getRaavareList() throws DALException;
	Response createRaavare(DTORaavare raavare) throws DALException;
	Response updateRaavare(DTORaavare raavare) throws DALException;
	Response deleteRaavare(int raavareId) throws DALException;
	//Råvarebatch
	Response getRaavareBatch(int rbId) throws DALException;
	Response getRaavareBatchList() throws DALException;
	Response getRaavareBatchList(int raavareId) throws DALException;
	Response createRaavareBatch(DTORaavareBatch raavarebatch) throws DALException;
	Response updateRaavareBatch(DTORaavareBatch raavarebatch) throws DALException;
	Response deleteRaavareBatch(int raavarebatch_ID) throws DALException;
}
