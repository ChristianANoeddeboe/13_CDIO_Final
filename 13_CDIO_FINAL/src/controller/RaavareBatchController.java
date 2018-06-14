package controller;

import dto.DTORaavareBatch;
import exception.DALException;
import interfaces.IDAORaavareBatch;
import interfaces.IRaavareBatchController;
import lombok.extern.java.Log;
import java.util.List;
import dao.DAORaavareBatch;

@Log
public class RaavareBatchController implements IRaavareBatchController {
    private static IDAORaavareBatch dao;
    private static IRaavareBatchController instance;
	private RaavareBatchController() {
	}
	public static IRaavareBatchController getInstance() {
		if(instance == null) {
			dao = new DAORaavareBatch();
			instance =  new RaavareBatchController();
			return instance;
		}
		return instance;
	}
    public DTORaavareBatch getRaavareBatch(int rbID) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        return dao.getRaavareBatch(rbID);
    }

    public List<DTORaavareBatch> getRaavareBatchList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
        return dao.getRaavareBatchList();
    }

    public void createRaavareBatch(DTORaavareBatch raavareBatch) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
        validateData(raavareBatch);
        dao.createRaavareBatch(raavareBatch);
    }

    public void updateRaavareBatch(DTORaavareBatch raavareBatch) throws  DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
        validateData(raavareBatch);
        dao.updateRaavareBatch(raavareBatch);
    }

    public void deleteRaavareBatch(int raavareBatchID) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
        dao.deleteRaavareBatch(raavareBatchID);
    }

    private void validateData(DTORaavareBatch raavareBatch) throws DALException{
        String errMsg;
        errMsg = ErrorChecking.checkIntSize(raavareBatch.getRbId());
        throwException(errMsg);
        errMsg = ErrorChecking.checkIntSize(raavareBatch.getRaavareId());
        throwException(errMsg);
        errMsg = ErrorChecking.checkNumberOfDecimals(raavareBatch.getMaengde());
        throwException(errMsg);
    }

    private void throwException(String errMsg) throws DALException {
        if (errMsg != null) {
            log.severe(errMsg);
            throw new DALException(errMsg);
        }
    }
    
    public List<DTORaavareBatch> getRaavareBatchList(int raavareId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
    	return dao.getRaavareBatchList(raavareId);
    }
}
