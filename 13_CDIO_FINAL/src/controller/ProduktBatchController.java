package controller;

import dao.DAOProduktBatch;
import dto.DTOProduktBatch;
import exception.DALException;
import interfaces.IDAOProduktBatch;
import interfaces.IProduktBatchController;

import java.util.List;
import java.util.logging.Logger;

public class ProduktBatchController implements IProduktBatchController {
    private static final Logger log = Logger.getLogger(ProduktBatchController.class.getName());
    private static IDAOProduktBatch dao;
    private static IProduktBatchController instance;
	private ProduktBatchController() {
		
	}
	public static IProduktBatchController getInstance() {
		if(instance == null) {
			dao = new DAOProduktBatch();
			instance =  new ProduktBatchController();
			return instance;
		}
		return instance;
	}
    
    public DTOProduktBatch getProduktBatch(int pbID) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
        return dao.getProduktBatch(pbID);
    }

    public List<DTOProduktBatch> getProduktBatchList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
        return dao.getProduktBatchList();
    }

    public void createProduktBatch(DTOProduktBatch produktBatch) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
        validateData(produktBatch);
        dao.createProduktBatch(produktBatch);
    }

    public void updateProduktBatch(DTOProduktBatch produktBatch) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
        validateData(produktBatch);
        dao.updateProduktBatch(produktBatch);
    }

    public void deleteProduktBatch(int pbID) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
        dao.deleteProduktBatch(pbID);
    }

    private void validateData(DTOProduktBatch produktBatch) throws DALException {
        String errMsg;
        errMsg = ErrorChecking.checkIntSize(produktBatch.getPbId());
        throwException(errMsg);
        errMsg = ErrorChecking.checkIntSize(produktBatch.getReceptId());
        throwException(errMsg);
        errMsg = ErrorChecking.checkStatus(produktBatch.getStatus());
        throwException(errMsg);
    }

    private void throwException(String errMsg) throws DALException {
        if (errMsg != null) {
            log.severe(errMsg);
            throw new DALException(errMsg);
        }
    }
}
