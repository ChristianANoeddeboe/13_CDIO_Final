package controller;

import dao.ErrorChecking;
import dao.MySQLReceptDAO;
import dto.ReceptDTO;
import dto.ReceptKompDTO;
import exception.DALException;
import lombok.extern.java.Log;

import java.util.List;

@Log
public class ReceptController {
    MySQLReceptDAO dao = new MySQLReceptDAO();

    public ReceptDTO getRecept(int receptID) throws DALException {
        return dao.getRecept(receptID);
    }

    public List<ReceptDTO> getReceptList() throws DALException {
        return dao.getReceptList();
    }

    public void createRecept(ReceptDTO recept) throws DALException {
        validateData(recept);
        dao.createRecept(recept);
    }

    public void updateRecept(ReceptDTO recept) throws DALException {
        validateData(recept);
        dao.updateRecept(recept);
    }

    public void deleteRecept(int receptID) throws DALException{
        dao.deleteRecept(receptID);
    }

    private void validateData(ReceptDTO recept) throws DALException {
        String errMsg;
        errMsg = ErrorChecking.checkStrSize(recept.getReceptNavn());
        throwException(errMsg);
        errMsg = ErrorChecking.checkIntSize(recept.getReceptId());
        throwException(errMsg);
    }

    private void throwException(String errMsg) throws DALException {
        if (errMsg != null) {
            log.severe(errMsg);
            throw new DALException(errMsg);
        }
    }
}
