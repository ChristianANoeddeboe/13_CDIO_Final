package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector.MySQLConnector;
import exception.DALException;
import interfaces.IDAOProduktBatch;
import dto.DTOProduktBatch;
import dto.DTOProduktBatch.Status;
import lombok.extern.java.Log;

@Log
public class DAOProduktBatch implements IDAOProduktBatch {

    @Override
    public DTOProduktBatch getProduktBatch(int pbId) throws DALException {
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM produktbatchview WHERE pb_id = " + pbId); // View
        try {
            if (!rs.first()) throw new DALException("Produktbatchet " + pbId + " findes ikke");
            return new DTOProduktBatch(rs.getInt("pb_id"),
                    Status.valueOf(rs.getString("status")), rs.getInt("recept_id"));
        } catch (SQLException e) {
            log.severe(e.toString());
            throw new DALException(e);
        }
    }

    @Override
    public List<DTOProduktBatch> getProduktBatchList() throws DALException {
        List<DTOProduktBatch> list = new ArrayList<DTOProduktBatch>();
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM produktbatchview");
        try {
            while (rs.next()) {
                list.add(new DTOProduktBatch(rs.getInt("pb_id"),
                        Status.valueOf(rs.getString("status")), rs.getInt("recept_id")));
            }
        } catch (SQLException e) {
            log.severe(e.toString());
            throw new DALException(e);
        }
        return list;
    }

    @Override
    public void createProduktBatch(DTOProduktBatch produktbatch) throws DALException {
        if (MySQLConnector.doUpdate("call createProductBatch('"+produktbatch.getStatus() + "'," +
                produktbatch.getReceptId() + ")") == 0) {
            String errMsg = "Couldn't add tuple to \"Produkt batch\".";
            log.severe(errMsg);
            throw new DALException(errMsg);
        }
    }

    @Override
    public void updateProduktBatch(DTOProduktBatch produktbatch) throws DALException {
        if (MySQLConnector.doUpdate("call updateProductBatch(" + produktbatch.getPbId() + ",'" +
                produktbatch.getStatus().toString() + "'," + produktbatch.getReceptId() + ")") == 0) {
            String errMsg = "No rows updated in \"Produkt batch\".";
            log.severe(errMsg);
            throw new DALException(errMsg);
        }
    }

    @Override
    public void deleteProduktBatch(int pbID) throws DALException {
        if (MySQLConnector.doUpdate("call deleteProductBatch(" + pbID + ")") == 0) {
            String errMsg = "No rows updated in \"Produkt batch\".";
            log.severe(errMsg);
            throw new DALException(errMsg);
        }
    }
}
