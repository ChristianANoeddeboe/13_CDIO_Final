package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector.MySQLConnector;
import exception.DALException;
import interfaces.ReceptDAO;
import dto.ReceptDTO;
import lombok.extern.java.Log;

@Log
public class MySQLReceptDAO implements ReceptDAO {

    /*Return a single recept based on the recept ID.*/
    @Override
    public ReceptDTO getRecept(int receptId) throws DALException {
        //Select the entry from receptView that matches the ID.
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM receptView WHERE recept_id=" + receptId);

        try {
            if (!rs.first()) throw new DALException("Recept " + receptId + " findes ikke.");
            return new ReceptDTO(rs.getInt("recept_id"), rs.getString("recept_navn"));
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    /*Return the entire lists of recepts.*/
    @Override
    public List<ReceptDTO> getReceptList() throws DALException {
        List<ReceptDTO> list = new ArrayList<ReceptDTO>();
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM receptView");

        try {
            while (rs.next()) {
                list.add(new ReceptDTO(rs.getInt("recept_id"), rs.getString("recept_navn")));
            }
            return list;
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public void createRecept(ReceptDTO recept) throws DALException {
        validateData(recept);
        if (MySQLConnector.doUpdate("CALL createRecept('" + recept.getReceptNavn() + "');") == 0) {
            throw new DALException("Couldn't add tuple to \"Recept\".");
        }
    }

    @Override
    public void updateRecept(ReceptDTO recept) throws DALException {
        validateData(recept);
        if (MySQLConnector.doUpdate("CALL updateRecept(" + recept.getReceptId() + ", '" + recept.getReceptNavn() + "');") == 0) {
            throw new DALException("No rows updated in \"Recept\".");
        }
    }

    @Override
    public void deleteRecept(int receptID) throws DALException {
        if (MySQLConnector.doUpdate("CALL deleteRecept(" + receptID + "');") == 0) {
            throw new DALException("No rows deleted in \"Recept\".");
        }
    }

    private void validateData(ReceptDTO recept) throws DALException {
        String errMsg;
        errMsg = ErrorChecking.checkStrSize(recept.getReceptNavn());
        throwException(errMsg);
        errMsg = ErrorChecking.checkIntSize(recept.getReceptId());
        throwException(errMsg);
    }

    private void throwException(String errMsg) throws DALException {
        if(errMsg != null){
            log.severe(errMsg);
            throw new DALException(errMsg);
        }
    }
}
