package interfaces;

import java.util.List;

import daointerfaces01917.DALException;
import dto.ProduktBatchDTO;

public interface ProduktBatchDAO {
	ProduktBatchDTO getProduktBatch(int pbId) throws DALException; // Retuner produkt batch
	List<ProduktBatchDTO> getProduktBatchList() throws DALException; // Retuner en liste af alle produkt batch
	void createProduktBatch(ProduktBatchDTO produktbatch) throws DALException; //Opret et nyt produkt batch
	void updateProduktBatch(ProduktBatchDTO produktbatch) throws DALException; //Opdater et eksisterende produkt batch
	void deleteProduktBatch(int pbID) throws DALException;
}