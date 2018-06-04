package interfaces;

import java.util.List;

import exception.DALException;
import dto.DTOProduktBatch;

public interface IDAOProduktBatch {
	DTOProduktBatch getProduktBatch(int pbId) throws DALException; // Retuner produkt batch
	List<DTOProduktBatch> getProduktBatchList() throws DALException; // Retuner en liste af alle produkt batch
	void createProduktBatch(DTOProduktBatch produktbatch) throws DALException; //Opret et nyt produkt batch
	void updateProduktBatch(DTOProduktBatch produktbatch) throws DALException; //Opdater et eksisterende produkt batch
	void deleteProduktBatch(int pbID) throws DALException;
}