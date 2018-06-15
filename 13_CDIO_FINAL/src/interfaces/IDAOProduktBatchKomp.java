package interfaces;

import dto.DTOProduktBatchKomp;
import exception.DALException;

import java.util.List;

public interface IDAOProduktBatchKomp {
	DTOProduktBatchKomp getProduktBatchKomp(int pbId, int rbId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException; // Retuner et produkt batch komponent
	List<DTOProduktBatchKomp> getProduktBatchKompList(int pbId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	List<DTOProduktBatchKomp> getProduktBatchKompList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	void createProduktBatchKomp(DTOProduktBatchKomp produktbatchkomponent) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	void updateProduktBatchKomp(DTOProduktBatchKomp produktbatchkomponent) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	void deleteProduktBatchKomp(int productBatch_ID, int raavareBatch_ID) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
}

