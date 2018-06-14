package controller;

import dto.DTORecept;
import exception.DALException;
import interfaces.IDAORecept;
import interfaces.IReceptController;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.util.List;

import dao.DAORaavare;
import dao.DAORecept;

@Log
public class ReceptController implements IReceptController {
    private static IDAORecept dao;
    private static IReceptController instance;
    private ReceptController() {
	}
	public static IReceptController getInstance() {
		if(instance == null) {
			dao = new DAORecept();
			instance =  new ReceptController();
			return instance;
		}
		return instance;
	}
    public DTORecept getRecept(int receptID) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        return dao.getRecept(receptID);
    }

    public List<DTORecept> getReceptList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        return dao.getReceptList();
    }

    public void createRecept(DTORecept recept) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        validateData(recept);
        dao.createRecept(recept);
    }

    public void updateRecept(DTORecept recept) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        validateData(recept);
        dao.updateRecept(recept);
    }

    public void deleteRecept(int receptID) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
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
