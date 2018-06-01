package controller;

import java.util.List;

import dao.ErrorChecking;
import dao.MySQLReceptKompDAO;
import dto.ReceptKompDTO;
import exception.DALException;
import lombok.extern.java.Log;

@Log
public class ReceptKompController {
    MySQLReceptKompDAO dao = new MySQLReceptKompDAO();

    public ReceptKompDTO getReceptKomp(int receptID, int raavarID) throws  DALException{
        return  dao.getReceptKomp(receptID, raavarID);
    }

    public List<ReceptKompDTO> getReceotKompList(int receptID) throws  DALException{
        return  dao.getReceptKompList(receptID);
    }

    public  List<ReceptKompDTO> getRaceptKomList() throws  DALException{
        return  dao.getReceptKompList();
    }

    public void createReceptKomp(ReceptKompDTO receptKompDTO) throws  DALException{
        validateData(receptKompDTO);
        dao.createReceptKomp(receptKompDTO);
    }

    public  void updateReceptKomp(ReceptKompDTO receptKompDTO) throws  DALException{
        validateData(receptKompDTO);
        dao.updateReceptKomp(receptKompDTO);
    }

    public void deleteReceptKomp(int receptKompID, int raavareID) throws DALException{
        dao.deleteReceptKomp(receptKompID, raavareID);
    }

    private void validateData(ReceptKompDTO receptkomponent) throws DALException {
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
