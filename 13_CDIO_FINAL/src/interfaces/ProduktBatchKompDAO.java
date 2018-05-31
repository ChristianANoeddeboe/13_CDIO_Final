package interfaces;

import java.util.List;

import exception.DALException;
import dto.ProduktBatchKompDTO;

public interface ProduktBatchKompDAO {
	ProduktBatchKompDTO getProduktBatchKomp(int pbId, int rbId) throws DALException; // Retuner et produkt batch komponent
	List<ProduktBatchKompDTO> getProduktBatchKompList(int pbId) throws DALException;
	List<ProduktBatchKompDTO> getProduktBatchKompList() throws DALException;
	void createProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent) throws DALException;
	void updateProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent) throws DALException;	
	void deleteProduktBatchKomp(int productBatch_ID, int raavareBatch_ID) throws DALException;
}

