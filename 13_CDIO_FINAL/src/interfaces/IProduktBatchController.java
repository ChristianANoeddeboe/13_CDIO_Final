package interfaces;

import dto.DTOProduktBatch;
import exception.DALException;

import java.util.List;

public interface IProduktBatchController {

	DTOProduktBatch getProduktBatch(int pbID)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	List<DTOProduktBatch> getProduktBatchList()
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	void createProduktBatch(DTOProduktBatch produktBatch)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	void updateProduktBatch(DTOProduktBatch produktBatch)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	void deleteProduktBatch(int pbID)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

}