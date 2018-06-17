package controller;

import lombok.extern.java.Log;

import java.util.List;

import dao.DAOBruger;
import dto.DTOBruger;
import exception.DALException;
import interfaces.IBrugerController;
import interfaces.IDAOOperatoer;

@Log
public class BrugerController implements IBrugerController {
	private static IDAOOperatoer dao;
	private static IBrugerController instance;
	
	private BrugerController() {
	}
	
	public static IBrugerController getInstance() {
		if(instance == null) {
			dao = new DAOBruger();
			instance =  new BrugerController();
			return instance;
		}
		return instance;
	}
	
		
	public DTOBruger getBruger(int oprId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return dao.getOperatoer(oprId);
	}
	

	public List<DTOBruger> getBrugerList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		return dao.getOperatoerList();
	}
	

	
	public void createBruger(DTOBruger operatoerDTO) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		validateData(operatoerDTO);
		dao.createOperatoer(operatoerDTO);
	}
	

	public void updateBruger(DTOBruger operatoerDTO) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		validateData(operatoerDTO);
		dao.updateOperatoer(operatoerDTO);
	}
	

	public void deleteBruger(int oprId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		dao.deleteOperatoer(oprId);
	}
	
	private void validateData(DTOBruger operatoer) throws DALException{
		String errMsg;
		errMsg = ErrorChecking.checkId(operatoer.getOprId());
		throwException(errMsg);
		errMsg = ErrorChecking.checkStrSize(operatoer.getFornavn());
		throwException(errMsg);
		errMsg = ErrorChecking.checkStrSize(operatoer.getEfternavn());
		throwException(errMsg);
		errMsg = ErrorChecking.checkCPR(operatoer.getCpr());
		throwException(errMsg);
	}
	
	private void throwException(String errMsg) throws DALException {
		if(errMsg!=null) {
			log.severe(errMsg);
			throw new DALException(errMsg);
		}
	}
}
