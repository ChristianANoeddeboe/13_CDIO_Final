package controller;

import dto.DTOProduktBatch;
import exception.DALException;
import interfaces.IDAOProduktBatch;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.util.List;

import dao.DAOOperatoer;
import dao.DAOProduktBatch;

@Log
@AllArgsConstructor
public class ProduktBatchController {
    private static IDAOProduktBatch dao;
    private static ProduktBatchController instance;
	
	public static ProduktBatchController getInstance() {
		if(instance == null) {
			dao = new DAOProduktBatch();
			instance =  new ProduktBatchController();
			return instance;
		}
		return instance;
	}
    
    public DTOProduktBatch getProduktBatch(int pbID) throws DALException{
        return dao.getProduktBatch(pbID);
    }

    public List<DTOProduktBatch> getProduktBatchList() throws DALException{
        return dao.getProduktBatchList();
    }

    public void createProduktBatch(DTOProduktBatch produktBatch) throws DALException{
        validateData(produktBatch);
        dao.createProduktBatch(produktBatch);
    }

    public void updateProduktBatch(DTOProduktBatch produktBatch) throws DALException{
        validateData(produktBatch);
        dao.updateProduktBatch(produktBatch);
    }

    public void deleteProduktBatch(int pbID) throws DALException{
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
