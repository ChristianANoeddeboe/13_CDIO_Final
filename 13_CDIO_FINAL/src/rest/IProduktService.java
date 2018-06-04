package rest;

import java.util.List;

import dto.DTOProduktBatch;
import dto.DTOProduktBatchKomp;
import exception.DALException;

public interface IProduktService {
	//Produkt batch
	DTOProduktBatch getProduktBatch(int pbId) throws DALException; // Retuner produkt batch
	List<DTOProduktBatch> getProduktBatchList() throws DALException; // Retuner en liste af alle produkt batch
	void createProduktBatch(DTOProduktBatch produktbatch) throws DALException; //Opret et nyt produkt batch
	void updateProduktBatch(DTOProduktBatch produktbatch) throws DALException; //Opdater et eksisterende produkt batch
	void deleteProduktBatch(int pbID) throws DALException;
	//Produkt batch komponent
	DTOProduktBatchKomp getProduktBatchKomp(int pbId, int rbId) throws DALException; // Retuner et produkt batch komponent
	List<DTOProduktBatchKomp> getProduktBatchKompList(int pbId) throws DALException;
	List<DTOProduktBatchKomp> getProduktBatchKompList() throws DALException;
	void createProduktBatchKomp(DTOProduktBatchKomp produktbatchkomponent) throws DALException;
	void updateProduktBatchKomp(DTOProduktBatchKomp produktbatchkomponent) throws DALException;
	void deleteProduktBatchKomp(int productBatch_ID, int raavareBatch_ID) throws DALException;
}
