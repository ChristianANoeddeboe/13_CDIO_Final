package controller;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.util.List;

import dao.DAOOperatoer;
import dto.DTOOperatoer;
import exception.DALException;
import interfaces.IDAOOperatoer;

@Log
public class OperatoerController {
	private static IDAOOperatoer dao;
	private static OperatoerController instance;
	private OperatoerController() {
	}
	public static OperatoerController getInstance() {
		if(instance == null) {
			dao = new DAOOperatoer();
			instance =  new OperatoerController();
			return instance;
		}
		return instance;
	}
	
	
	public DTOOperatoer getOperatoer(int oprId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return dao.getOperatoer(oprId);
	}
	
	public List<DTOOperatoer> getOperatoerList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		return dao.getOperatoerList();
	}
	
	public void createOperatoer(DTOOperatoer operatoerDTO) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		validateData(operatoerDTO);
		dao.createOperatoer(operatoerDTO);
	}
	
	public void updateOperatoer(DTOOperatoer operatoerDTO) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		validateData(operatoerDTO);
		dao.updateOperatoer(operatoerDTO);
	}
	
	public void deleteOperatoer(int oprId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		dao.deleteOperatoer(oprId);
	}
	
	private void validateData(DTOOperatoer operatoer) throws DALException{
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
