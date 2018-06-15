package interfaces;

import dto.DTOProduktBatch;
import exception.DALException;

import java.util.List;

public interface IDAOProduktBatch {
	DTOProduktBatch getProduktBatch(int pbId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException; // Retuner produkt batch
	List<DTOProduktBatch> getProduktBatchList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException; // Retuner en liste af alle produkt batch
	void createProduktBatch(DTOProduktBatch produktbatch) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException; //Opret et nyt produkt batch
	void updateProduktBatch(DTOProduktBatch produktbatch) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException; //Opdater et eksisterende produkt batch
	void deleteProduktBatch(int pbID) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
}