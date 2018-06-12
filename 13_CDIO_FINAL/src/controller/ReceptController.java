package controller;

import dto.DTORecept;
import exception.DALException;
import interfaces.IDAORecept;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.util.List;

import dao.DAORaavare;
import dao.DAORecept;

@Log
@AllArgsConstructor
public class ReceptController {
    private static IDAORecept dao;
    private static ReceptController instance;

	public static ReceptController getInstance() {
		if(instance == null) {
			dao = new DAORecept();
			instance =  new ReceptController();
			return instance;
		}
		return instance;
	}
    public DTORecept getRecept(int receptID) throws DALException {
        return dao.getRecept(receptID);
    }

    public List<DTORecept> getReceptList() throws DALException {
        return dao.getReceptList();
    }

    public void createRecept(DTORecept recept) throws DALException {
        validateData(recept);
        dao.createRecept(recept);
    }

    public void updateRecept(DTORecept recept) throws DALException {
        validateData(recept);
        dao.updateRecept(recept);
    }

    public void deleteRecept(int receptID) throws DALException{
        dao.deleteRecept(receptID);
    }

    private void validateData(DTORecept recept) throws DALException {
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
