package interfaces;

import java.util.List;

import exception.DALException;
import dto.DTORaavareBatch;

public interface IDAORaavareBatch {
	DTORaavareBatch getRaavareBatch(int rbId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	List<DTORaavareBatch> getRaavareBatchList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	List<DTORaavareBatch> getRaavareBatchList(int raavareId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	void createRaavareBatch(DTORaavareBatch raavarebatch) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	void updateRaavareBatch(DTORaavareBatch raavarebatch) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	void deleteRaavareBatch(int raavarebatch_ID) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	
}

