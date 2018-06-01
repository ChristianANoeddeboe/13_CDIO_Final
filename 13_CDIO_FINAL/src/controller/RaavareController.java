package controller;

import dao.MySQLRaavareDAO;
import dto.RaavareDTO;
import exception.DALException;
import interfaces.RaavareDAO;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.util.List;

@Log
@AllArgsConstructor
public class RaavareController {
    private RaavareDAO dao;

    public RaavareDTO getRaavare(int raavareID) throws DALException {
        return dao.getRaavare(raavareID);
    }

    public List<RaavareDTO> getRaavreList() throws DALException {
        return dao.getRaavareList();
    }

    public void createRaavare(RaavareDTO raavare) throws DALException {
        validateData(raavare);
        dao.createRaavare(raavare);
    }

    public void updateRaavare(RaavareDTO raavare) throws DALException{
        validateData(raavare);
        dao.updateRaavare(raavare);
    }

    public void deleteRaavare(int raavareID) throws DALException{
        dao.deleteRaavare(raavareID);
    }

    private void validateData(RaavareDTO raavare) throws DALException {
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
