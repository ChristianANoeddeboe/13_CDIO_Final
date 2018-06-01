package controller;

import java.util.List;

import dao.MySQLProduktBatchDAO;
import dto.ProduktBatchDTO;
import exception.DALException;
import lombok.extern.java.Log;

@Log
public class ProduktBatchController {
MySQLProduktBatchDAO dao = new MySQLProduktBatchDAO();
 
 public ProduktBatchDTO getProduktBatch(int pbId) throws DALException {
	return dao.getProduktBatch(pbId);
 }
	
 public List<ProduktBatchDTO> getProduktBatchList() throws DALException {
	return dao.getProduktBatchList();	
 }

 public void createProduktBatch(ProduktBatchDTO produktbatch) throws DALException {			
 	validateData(produktbatch);		 
	dao.createProduktBatch(produktbatch);			
 }

 public void updateProduktBatch(ProduktBatchDTO produktbatch) throws DALException {
	validateData(produktbatch);
	dao.updateProduktBatch(produktbatch);
 }

public void deleteProduktBatch(int pbID) throws DALException {
	dao.deleteProduktBatch(pbID);			
}
	
private void validateData(ProduktBatchDTO produktBatchDTO) throws DALException {
    String errMsg;
    errMsg = ErrorChecking.checkIntSize( produktBatchDTO.getPbId() );
    throwException(errMsg);
    errMsg = ErrorChecking.checkIntSize( produktBatchDTO.getReceptId() );
    throwException(errMsg);
    
    //possible enum check here
    //errMsg = ErrorChecking.checkEnum( produktBatchDTO.getStatus() );
    //throwException(errMsg);
}

private void throwException(String errMsg) throws DALException {
    if(errMsg != null){
       log.severe(errMsg);
       throw new DALException(errMsg);
    }
}

}
