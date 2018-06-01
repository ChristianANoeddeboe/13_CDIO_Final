package controller;

import dao.MySQLRaavareBatchDAO;
import dto.RaavareBatchDTO;
import exception.DALException;
import lombok.extern.java.Log;

import java.util.List;

@Log
public class RaavareBatchController {
    MySQLRaavareBatchDAO dao = new MySQLRaavareBatchDAO();

    public RaavareBatchDTO getRaavareBatch(int rbID) throws DALException {
        return dao.getRaavareBatch(rbID);
    }

    public List<RaavareBatchDTO> getRaavareBatchList() throws DALException{
        return dao.getRaavareBatchList();
    }

    public void createRaavareBatch(RaavareBatchDTO raavareBatch) throws DALException{
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
        errMsg = ErrorChecking.checkNomNetto(raavareBatch.getMaengde());
        throwException(errMsg);
    }


    private void throwException(String errMsg) throws DALException {
        if (errMsg != null) {
            log.severe(errMsg);
            throw new DALException(errMsg);
        }
    }
}
