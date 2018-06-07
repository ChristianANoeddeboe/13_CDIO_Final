package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector.MySQLConnector;
import exception.DALException;
import interfaces.IDAORecept;
import dto.DTORecept;
import logging.LogHandler;
import lombok.extern.java.Log;

@Log
public class DAORecept implements IDAORecept {

    public DAORecept(){
        new LogHandler(log, "DAO");
    }

    /*Return a single recept based on the recept ID.*/
    @Override
    public DTORecept getRecept(int receptId) throws DALException {
        //Select the entry from receptView that matches the ID.
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM receptView WHERE recept_id=" + receptId);

        try {
            if (!rs.first()) throw new DALException("Recept " + receptId + " findes ikke.");
            return new DTORecept(rs.getInt("recept_id"), rs.getString("recept_navn"));
        } catch (SQLException e) {
            log.severe(e.toString());
            log.getHandlers()[0].close();
            throw new DALException(e);
        }
    }

    /*Return the entire lists of recepts.*/
    @Override
    public List<DTORecept> getReceptList() throws DALException {
        List<DTORecept> list = new ArrayList<DTORecept>();
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM receptView");

        try {
            while (rs.next()) {
                list.add(new DTORecept(rs.getInt("recept_id"), rs.getString("recept_navn")));
            }
            return list;
        } catch (SQLException e) {
            log.severe(e.toString());
            log.getHandlers()[0].close();
            throw new DALException(e);
        }
    }

    @Override
    public void createRecept(DTORecept recept) throws DALException {
        if (MySQLConnector.doUpdate("CALL createRecept("+recept.getReceptId()+ ",'" + recept.getReceptNavn() + "');") == 0) {
            String errMsg = "Couldn't add tuple to \"Recept\".";
            log.severe(errMsg);
            log.getHandlers()[0].close();
            throw new DALException(errMsg);
        }
    }

    @Override
    public void updateRecept(DTORecept recept) throws DALException {
        if (MySQLConnector.doUpdate("CALL updateRecept(" + recept.getReceptId() + ", '" + recept.getReceptNavn() + "');") == 0) {
            String errMsg = "No rows updated in \"Recept\".";
            log.severe(errMsg);
            log.getHandlers()[0].close();
            throw new DALException(errMsg);
        }
    }

    @Override
    public void deleteRecept(int receptID) throws DALException {
        if (MySQLConnector.doUpdate("CALL deleteRecept(" + receptID + ");") == 0) {
            String eerMsg = "No rows deleted in \"Recept\".";
            log.severe(eerMsg);
            log.getHandlers()[0].close();
            throw new DALException(eerMsg);
        }
    }
}
