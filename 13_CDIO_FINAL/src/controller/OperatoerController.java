package controller;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.util.List;

import dto.DTOOperatoer;
import exception.DALException;
import interfaces.IDAOOperatoer;

@Log
@AllArgsConstructor
public class OperatoerController {
	private IDAOOperatoer dao;
	
	public DTOOperatoer getOperatoer(int oprId) throws DALException {
		return dao.getOperatoer(oprId);
	}
	
	public List<DTOOperatoer> getOperatoerList() throws DALException{
		return dao.getOperatoerList();
	}
	
	public void createOperatoer(DTOOperatoer operatoerDTO) throws DALException {
		validateData(operatoerDTO);
		dao.createOperatoer(operatoerDTO);
	}
	
	public void updateOperatoer(DTOOperatoer operatoerDTO) throws DALException{
		validateData(operatoerDTO);
		dao.updateOperatoer(operatoerDTO);
	}
	
	public void deleteOperatoer(int oprId) throws DALException {
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
