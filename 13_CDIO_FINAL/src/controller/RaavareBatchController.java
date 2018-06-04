package controller;

import dto.DTORaavareBatch;
import exception.DALException;
import interfaces.IDAORaavareBatch;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.util.List;

@Log
@AllArgsConstructor
public class RaavareBatchController {
    private IDAORaavareBatch dao;

    public DTORaavareBatch getRaavareBatch(int rbID) throws DALException {
        return dao.getRaavareBatch(rbID);
    }

    public List<DTORaavareBatch> getRaavareBatchList() throws DALException{
        return dao.getRaavareBatchList();
    }

    public void createRaavsareBatch(DTORaavareBatch raavareBatch) throws DALException{
        validateData(raavareBatch);
        dao.createRaavareBatch(raavareBatch);
    }

    public void updateRaavareBatch(DTORaavareBatch raavareBatch) throws  DALException{
        validateData(raavareBatch);
        dao.updateRaavareBatch(raavareBatch);
    }

    public void deleteRaavareBatch(int raavareBatchID) throws DALException{
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
}
