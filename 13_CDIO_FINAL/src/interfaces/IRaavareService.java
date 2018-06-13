package interfaces;

import javax.ws.rs.core.Response;

import dto.DTORaavare;
import dto.DTORaavareBatch;
import exception.DALException;

public interface IRaavareService {
	//Råvare
	Response getRaavareList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	Response createRaavare(DTORaavare raavare) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	Response updateRaavare(DTORaavare raavare) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	Response deleteRaavare(int raavareId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	//Råvarebatch
	Response getRaavareBatchList(int raavareId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	Response createRaavareBatch(DTORaavareBatch raavarebatch) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	Response updateRaavareBatch(DTORaavareBatch raavarebatch) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	Response deleteRaavareBatch(int raavarebatch_ID) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
}
