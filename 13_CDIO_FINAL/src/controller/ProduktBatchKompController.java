package controller;

import java.util.List;

import dao.MySQLProductBatchKomponentDAO;
import dto.ProduktBatchKompDTO;
import exception.DALException;
import interfaces.ProduktBatchKompDAO;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Log
@AllArgsConstructor
public class ProduktBatchKompController {
	private ProduktBatchKompDAO dao;
	
	public ProduktBatchKompDTO getProduktBatchKomp(int pbId, int rbId) throws DALException{
		return dao.getProduktBatchKomp(pbId, rbId);
	}
	
	public List<ProduktBatchKompDTO> getProduktBatchKomponentList(int pbId) throws DALException{
		return dao.getProduktBatchKompList(pbId);
	}
	
	public List<ProduktBatchKompDTO> getProduktBatchKomponentList() throws DALException{
		return dao.getProduktBatchKompList();
	}
	
	public void createProdBatchKomp(ProduktBatchKompDTO prodBatchKomp) throws DALException{
		validateData(prodBatchKomp);
		dao.createProduktBatchKomp(prodBatchKomp);
	}
	
	public void updateProdBatchKomp(ProduktBatchKompDTO prodBatchKomp) throws DALException{
		validateData(prodBatchKomp);
		dao.updateProduktBatchKomp(prodBatchKomp);
	}
	
	public void deleteProdBatchKomp(int pbId, int rbId) throws DALException{
		dao.deleteProduktBatchKomp(pbId, rbId);
	}
	
	private void validateData(ProduktBatchKompDTO produktBatchKomp) throws DALException {
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
