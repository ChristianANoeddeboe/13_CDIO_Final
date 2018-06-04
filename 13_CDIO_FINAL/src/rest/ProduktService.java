package rest;

import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.DAOProduktBatchKomp;
import dao.DAOProduktBatch;
import dto.DTOProduktBatch;
import dto.DTOProduktBatchKomp;
import exception.DALException;

@Path("produkt")
@Produces(MediaType.APPLICATION_JSON)

public class ProduktService implements IProduktService {
	static DAOProduktBatch dao = new DAOProduktBatch();
	static DAOProduktBatchKomp daoKomp = new DAOProduktBatchKomp();
	
	@Override
	public Response getProduktBatch(int pbId) throws DALException {
		return null;
	}

	@Override
	public Response getProduktBatchList() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response createProduktBatch(DTOProduktBatch produktbatch) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response updateProduktBatch(DTOProduktBatch produktbatch) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response deleteProduktBatch(int pbID) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getProduktBatchKomp(int pbId, int rbId) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getProduktBatchKompList(int pbId) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getProduktBatchKompList() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response createProduktBatchKomp(DTOProduktBatchKomp produktbatchkomponent) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response updateProduktBatchKomp(DTOProduktBatchKomp produktbatchkomponent) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response deleteProduktBatchKomp(int productBatch_ID, int raavareBatch_ID) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}


}
