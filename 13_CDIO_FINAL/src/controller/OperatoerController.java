package controller;

import lombok.extern.java.Log;

import java.util.List;

import dao.MySQLOperatoerDAO;
import dto.OperatoerDTO;
import exception.DALException;

@Log
public class OperatoerController {
	MySQLOperatoerDAO dao = new MySQLOperatoerDAO();
	
	public OperatoerDTO getOperatoer(int oprId) throws DALException {
		return dao.getOperatoer(oprId);
	}
	
	public List<OperatoerDTO> getOperatoerList() throws DALException{
		return dao.getOperatoerList();
	}
	
	public void createOperatoer(OperatoerDTO operatoerDTO) throws DALException {
		validateData(operatoerDTO);
		dao.createOperatoer(operatoerDTO);
	}
	
	public void updateOperatoer(OperatoerDTO operatoerDTO) throws DALException{
		validateData(operatoerDTO);
		dao.updateOperatoer(operatoerDTO);
	}
	
	public void deleteOperatoer(int oprId) throws DALException {
		dao.deleteOperatoer(oprId);
	}
	
	private void validateData(OperatoerDTO operatoer) throws DALException{
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
