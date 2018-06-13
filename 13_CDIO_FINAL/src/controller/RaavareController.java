package controller;

import dto.DTORaavare;
import exception.DALException;
import interfaces.IDAORaavare;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.util.List;

import dao.DAORaavare;
import dao.DAORaavareBatch;

@Log
@AllArgsConstructor
public class RaavareController {
	private static IDAORaavare dao;
	private static RaavareController instance;

	public static RaavareController getInstance() {
		if(instance == null) {
			dao = new DAORaavare();
			instance =  new RaavareController();
			return instance;
		}
		return instance;
	}
	public DTORaavare getRaavare(int raavareID) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return dao.getRaavare(raavareID);
	}

	public List<DTORaavare> getRaavreList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return dao.getRaavareList();
	}

	public void createRaavare(DTORaavare raavare) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		validateData(raavare);
		dao.createRaavare(raavare);
	}

	public void updateRaavare(DTORaavare raavare) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		validateData(raavare);
		dao.updateRaavare(raavare);
	}

	public void deleteRaavare(int raavareID) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
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
