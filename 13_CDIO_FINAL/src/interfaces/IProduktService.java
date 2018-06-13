package interfaces;

import java.sql.SQLException;

import javax.ws.rs.core.Response;

import dto.DTOProduktBatch;
import dto.DTOProduktBatchKomp;
import exception.DALException;

public interface IProduktService {
	//Produkt batch
	Response getProduktBatchList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException; // Retuner en liste af alle produkt batch
	Response createProduktBatch(DTOProduktBatch produktbatch) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException; //Opret et nyt produkt batch
	Response updateProduktBatch(DTOProduktBatch produktbatch) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException; //Opdater et eksisterende produkt batch
	Response deleteProduktBatch(int pbID) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	//Produkt batch komponent
	Response getProduktBatchKompList(int pbId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	Response createProduktBatchKomp(DTOProduktBatchKomp produktbatchkomponent) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	Response updateProduktBatchKomp(DTOProduktBatchKomp produktbatchkomponent) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	Response deleteProduktBatchKomp(int productBatch_ID, int raavareBatch_ID) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
}
