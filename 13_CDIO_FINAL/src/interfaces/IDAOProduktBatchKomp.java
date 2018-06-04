package interfaces;

import java.util.List;

import exception.DALException;
import dto.DTOProduktBatchKomp;

public interface IDAOProduktBatchKomp {
	DTOProduktBatchKomp getProduktBatchKomp(int pbId, int rbId) throws DALException; // Retuner et produkt batch komponent
	List<DTOProduktBatchKomp> getProduktBatchKompList(int pbId) throws DALException;
	List<DTOProduktBatchKomp> getProduktBatchKompList() throws DALException;
	void createProduktBatchKomp(DTOProduktBatchKomp produktbatchkomponent) throws DALException;
	void updateProduktBatchKomp(DTOProduktBatchKomp produktbatchkomponent) throws DALException;
	void deleteProduktBatchKomp(int productBatch_ID, int raavareBatch_ID) throws DALException;
}

