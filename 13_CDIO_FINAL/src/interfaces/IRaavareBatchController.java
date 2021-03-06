package interfaces;

import dto.DTORaavareBatch;
import exception.DALException;

import java.util.List;

public interface IRaavareBatchController {

	DTORaavareBatch getRaavareBatch(int rbID)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	List<DTORaavareBatch> getRaavareBatchList()
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	void createRaavareBatch(DTORaavareBatch raavareBatch)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	void updateRaavareBatch(DTORaavareBatch raavareBatch)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	void deleteRaavareBatch(int raavareBatchID)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	List<DTORaavareBatch> getRaavareBatchList(int raavareId)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

}