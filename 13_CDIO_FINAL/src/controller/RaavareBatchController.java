package controller;

import dao.MySQLRaavareBatchDAO;
import dto.RaavareBatchDTO;
import exception.DALException;
import interfaces.ProduktBatchDAO;
import interfaces.RaavareBatchDAO;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.util.List;

@Log
@AllArgsConstructor
public class RaavareBatchController {
    private RaavareBatchDAO dao;

    public RaavareBatchDTO getRaavareBatch(int rbID) throws DALException {
        return dao.getRaavareBatch(rbID);
    }

    public List<RaavareBatchDTO> getRaavareBatchList() throws DALException{
        return dao.getRaavareBatchList();
    }

    public void createRaavsareBatch(RaavareBatchDTO raavareBatch) throws DALException{
        validateData(raavareBatch);
        dao.createRaavareBatch(raavareBatch);
    }

    public void updateRaavareBatch(RaavareBatchDTO raavareBatch) throws  DALException{
        validateData(raavareBatch);
        dao.updateRaavareBatch(raavareBatch);
    }

    public void deleteRaavareBatch(int raavareBatchID) throws DALException{
        dao.deleteRaavareBatch(raavareBatchID);
    }

    private void validateData(RaavareBatchDTO raavareBatch) throws DALException{
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
