package controller;

import java.util.List;

import dto.DTOReceptKomp;
import exception.DALException;
import interfaces.IDAOReceptKomp;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Log
@AllArgsConstructor
public class ReceptKompController {
    private IDAOReceptKomp dao;

    public DTOReceptKomp getReceptKomp(int receptID, int raavarID) throws  DALException{
        return  dao.getReceptKomp(receptID, raavarID);
    }

    public List<DTOReceptKomp> getReceotKompList(int receptID) throws  DALException{
        return  dao.getReceptKompList(receptID);
    }

    public  List<DTOReceptKomp> getRaceptKomList() throws  DALException{
        return  dao.getReceptKompList();
    }

    public void createReceptKomp(DTOReceptKomp receptKompDTO) throws  DALException{
        validateData(receptKompDTO);
        dao.createReceptKomp(receptKompDTO);
    }

    public  void updateReceptKomp(DTOReceptKomp receptKompDTO) throws  DALException{
        validateData(receptKompDTO);
        dao.updateReceptKomp(receptKompDTO);
    }

    public void deleteReceptKomp(int receptKompID, int raavareID) throws DALException{
        dao.deleteReceptKomp(receptKompID, raavareID);
    }

    private void validateData(DTOReceptKomp receptkomponent) throws DALException {
        String errMsg;
        errMsg = ErrorChecking.checkIntSize(receptkomponent.getRaavareId());
        throwException(errMsg);
        errMsg = ErrorChecking.checkIntSize(receptkomponent.getReceptId());
        throwException(errMsg);
    }

    private void throwException(String errMsg) throws DALException {
        if(errMsg != null){
            log.severe(errMsg);
            throw new DALException(errMsg);
        }
    }
}
