package controller;

import dto.DTORaavare;
import exception.DALException;
import interfaces.IDAORaavare;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.util.List;

@Log
@AllArgsConstructor
public class RaavareController {
    private IDAORaavare dao;

    public DTORaavare getRaavare(int raavareID) throws DALException {
        return dao.getRaavare(raavareID);
    }

    public List<DTORaavare> getRaavreList() throws DALException {
        return dao.getRaavareList();
    }

    public void createRaavare(DTORaavare raavare) throws DALException {
        validateData(raavare);
        dao.createRaavare(raavare);
    }

    public void updateRaavare(DTORaavare raavare) throws DALException{
        validateData(raavare);
        dao.updateRaavare(raavare);
    }

    public void deleteRaavare(int raavareID) throws DALException{
        dao.deleteRaavare(raavareID);
    }

    private void validateData(DTORaavare raavare) throws DALException {
        String errMsg;
        errMsg = ErrorChecking.checkIntSize(raavare.getRaavareId());
        throwException(errMsg);
        errMsg = ErrorChecking.checkStrSize(raavare.getRaavareNavn());
        throwException(errMsg);
        errMsg = ErrorChecking.checkStrSize(raavare.getLeverandoer());
        throwException(errMsg);
    }

    private void throwException(String errMsg) throws DALException {
        if (errMsg != null) {
            log.severe(errMsg);
            throw new DALException(errMsg);
        }
    }
}
