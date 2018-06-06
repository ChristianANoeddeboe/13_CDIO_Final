package dao;

import connector.MySQLConnector;
import exception.DALException;
import interfaces.IDAORaavareBatch;
import dto.DTORaavareBatch;
import lombok.extern.java.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log
public class DAORaavareBatch implements IDAORaavareBatch {

    @Override
    public DTORaavareBatch getRaavareBatch(int rbId) throws DALException {
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM raavarebatchview WHERE rb_id = " + rbId);
        try {
            if (!rs.first()) throw new DALException("RaavareBatchen " + rbId + " findes ikke.");
            return new DTORaavareBatch(rs.getInt("rb_id"), rs.getInt("raavare_id"),
                    rs.getDouble("maengde"));
        } catch (SQLException e) {
            log.severe(e.toString());
            throw new DALException(e);
        }
    }


    @Override
    public List<DTORaavareBatch> getRaavareBatchList() throws DALException {
        List<DTORaavareBatch> list = new ArrayList<>();
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM raavarebatchview");
        try {
            while (rs.next()) {
                list.add(new DTORaavareBatch(rs.getInt("rb_id"), rs.getInt("raavare_id"),
                        rs.getDouble("maengde")));
            }
        } catch (SQLException e) {
            log.severe(e.toString());
            throw new DALException(e);
        }
        return list;
    }

    @Override
    public List<DTORaavareBatch> getRaavareBatchList(int raavareId) throws DALException {
        List<DTORaavareBatch> list = new ArrayList<>();
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM raavarebatchview WHERE raavare_id = " + raavareId);
        try {
            while (rs.next()) {
                list.add(new DTORaavareBatch(rs.getInt("rb_id"), rs.getInt("raavare_id"),
                        rs.getDouble("maengde")));
            }
        } catch (SQLException e) {
            log.severe(e.toString());
            throw new DALException(e);
        }
        return list;
    }

    @Override
    public void createRaavareBatch(DTORaavareBatch raavarebatch) throws DALException {
        if (MySQLConnector.doUpdate("call createRaavareBatch(" + raavarebatch.getRbId()+ ","+raavarebatch.getRaavareId() + "," +
                raavarebatch.getMaengde() + ")") == 0) {
            String errMsg = "Couldn't add tuple to \"Raavare batch\".";
            log.severe(errMsg);
            throw new DALException(errMsg);
        }
    }

    @Override
    public void updateRaavareBatch(DTORaavareBatch raavarebatch) throws DALException {
        if (MySQLConnector.doUpdate("call updateRaavareBatch(" + raavarebatch.getRbId() + "," +
                raavarebatch.getRaavareId() + "," + raavarebatch.getMaengde() + ")") == 0) {
            String errMsg = "No rows updated in \"Raavare batch\".";
            log.severe(errMsg);
            throw new DALException(errMsg);
        }
    }

    @Override
    public void deleteRaavareBatch(int raavarebatch_ID) throws DALException {
        if (MySQLConnector.doUpdate("call deleteRaavareBatch(" + raavarebatch_ID + ")") == 0) {
            String errMsg = "No rows updated in \"Raavare batch\".";
            log.severe(errMsg);
            throw new DALException(errMsg);
        }
    }
}
