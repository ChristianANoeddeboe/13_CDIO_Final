package controller;

import java.util.List;

import dao.DAOProduktBatch;
import dao.DAOProduktBatchKomp;
import dto.DTOProduktBatchKomp;
import exception.DALException;
import interfaces.IDAOProduktBatchKomp;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Log
@AllArgsConstructor
public class ProduktBatchKompController {
	private static IDAOProduktBatchKomp dao;
	private static ProduktBatchKompController instance;
	
	public static ProduktBatchKompController getInstance() {
		if(instance == null) {
			dao = new DAOProduktBatchKomp();
			instance =  new ProduktBatchKompController();
			return instance;
		}
		return instance;
	}
	
	public DTOProduktBatchKomp getProduktBatchKomp(int pbId, int rbId) throws DALException{
		return dao.getProduktBatchKomp(pbId, rbId);
	}
	
	public List<DTOProduktBatchKomp> getProduktBatchKomponentList(int pbId) throws DALException{
		return dao.getProduktBatchKompList(pbId);
	}
	
	public List<DTOProduktBatchKomp> getProduktBatchKomponentList() throws DALException{
		return dao.getProduktBatchKompList();
	}
	
	public void createProdBatchKomp(DTOProduktBatchKomp prodBatchKomp) throws DALException{
		validateData(prodBatchKomp);
		dao.createProduktBatchKomp(prodBatchKomp);
	}
	
	public void updateProdBatchKomp(DTOProduktBatchKomp prodBatchKomp) throws DALException{
		validateData(prodBatchKomp);
		dao.updateProduktBatchKomp(prodBatchKomp);
	}
	
	public void deleteProdBatchKomp(int pbId, int rbId) throws DALException{
		dao.deleteProduktBatchKomp(pbId, rbId);
	}
	
	private void validateData(DTOProduktBatchKomp produktBatchKomp) throws DALException {
		String errMsg;
		errMsg = ErrorChecking.checkIntSize(produktBatchKomp.getPbId());
		throwException(errMsg);
		errMsg = ErrorChecking.checkIntSize(produktBatchKomp.getOprId());
		throwException(errMsg);
		errMsg = ErrorChecking.checkIntSize(produktBatchKomp.getRbId());
		throwException(errMsg);
	}
	
	private void throwException(String errMsg) throws DALException{
		if(errMsg!=null) {
			log.severe(errMsg);
			throw new DALException(errMsg);
		}
	}
}
