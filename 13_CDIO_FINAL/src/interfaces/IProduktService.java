package interfaces;

import javax.ws.rs.core.Response;

import dto.DTOProduktBatch;
import dto.DTOProduktBatchKomp;
import exception.DALException;

public interface IProduktService {
	//Produkt batch
	Response getProduktBatchList() throws DALException; // Retuner en liste af alle produkt batch
	Response createProduktBatch(DTOProduktBatch produktbatch) throws DALException; //Opret et nyt produkt batch
	Response updateProduktBatch(DTOProduktBatch produktbatch) throws DALException; //Opdater et eksisterende produkt batch
	Response deleteProduktBatch(int pbID) throws DALException;
	//Produkt batch komponent
	Response getProduktBatchKompList(int pbId) throws DALException;
	Response createProduktBatchKomp(DTOProduktBatchKomp produktbatchkomponent) throws DALException;
	Response updateProduktBatchKomp(DTOProduktBatchKomp produktbatchkomponent) throws DALException;
	Response deleteProduktBatchKomp(int productBatch_ID, int raavareBatch_ID) throws DALException;
}
