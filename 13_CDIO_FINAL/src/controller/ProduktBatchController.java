package controller;

import dao.MySQLProduktBatchDAO;
import dto.ProduktBatchDTO;
import exception.DALException;
import interfaces.ProduktBatchDAO;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.util.List;

@Log
@AllArgsConstructor
public class ProduktBatchController {
    private ProduktBatchDAO dao;

    public ProduktBatchDTO getProductBatch(int pbID) throws DALException{
        return dao.getProduktBatch(pbID);
    }

    public List<ProduktBatchDTO> getProductBatchList() throws DALException{
        return dao.getProduktBatchList();
    }

    public void createProductBatch(ProduktBatchDTO produktBatch) throws DALException{
        validateData(produktBatch);
        dao.createProduktBatch(produktBatch);
    }

    public void updateProductBatch(ProduktBatchDTO produktBatch) throws DALException{
        validateData(produktBatch);
        dao.updateProduktBatch(produktBatch);
    }

    public void delteProductBatch(int pbID) throws DALException{
        dao.deleteProduktBatch(pbID);
    }

    private void validateData(ProduktBatchDTO produktBatch) throws DALException {
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
