package controller;

import dao.DAOProduktBatchKomp;
import dto.DTOProduktBatchKomp;
import exception.DALException;
import interfaces.IDAOProduktBatchKomp;
import interfaces.IProduktBatchKompController;

import java.util.List;
import java.util.logging.Logger;

public class ProduktBatchKompController implements IProduktBatchKompController {
    private static final Logger log = Logger.getLogger(ProduktBatchKompController.class.getName());
    private static IDAOProduktBatchKomp dao;
	private static IProduktBatchKompController instance;
	private ProduktBatchKompController() {
	}
	public static IProduktBatchKompController getInstance() {
		if(instance == null) {
			dao = new DAOProduktBatchKomp();
			instance =  new ProduktBatchKompController();
			return instance;
		}
		return instance;
	}
	
	public DTOProduktBatchKomp getProduktBatchKomp(int pbId, int rbId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		return dao.getProduktBatchKomp(pbId, rbId);
	}
	
	public List<DTOProduktBatchKomp> getProduktBatchKomponentList(int pbId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		return dao.getProduktBatchKompList(pbId);
	}
	
	public List<DTOProduktBatchKomp> getProduktBatchKomponentList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		return dao.getProduktBatchKompList();
	}
	
	public void createProdBatchKomp(DTOProduktBatchKomp prodBatchKomp) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		validateData(prodBatchKomp);
		dao.createProduktBatchKomp(prodBatchKomp);
	}
	
	public void updateProdBatchKomp(DTOProduktBatchKomp prodBatchKomp) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		validateData(prodBatchKomp);
		dao.updateProduktBatchKomp(prodBatchKomp);
	}
	
	public void deleteProdBatchKomp(int pbId, int rbId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
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
