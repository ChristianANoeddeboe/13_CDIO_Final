package dao;

import connector.MySQLConnector;
import dto.DTOProduktBatch;
import dto.Status;
import exception.DALException;
import interfaces.IDAOProduktBatch;
import org.slf4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOProduktBatch implements IDAOProduktBatch {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DAOProduktBatch.class);

    public DAOProduktBatch() {
    }

    @Override
    public DTOProduktBatch getProduktBatch(int pbId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM produktbatchview WHERE pb_id = " + pbId); // View
        try {
            if (!rs.first()) throw new DALException("Produktbatchet " + pbId + " findes ikke");
            return new DTOProduktBatch(rs.getInt("pb_id"),
                    Status.valueOf(rs.getString("status")), rs.getInt("recept_id"));
        } catch (SQLException e) {
            log.warn(e.toString());
            throw new DALException(e);
        }
    }

    @Override
    public List<DTOProduktBatch> getProduktBatchList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        List<DTOProduktBatch> list = new ArrayList<DTOProduktBatch>();
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM produktbatchview");
        try {
            while (rs.next()) {
                list.add(new DTOProduktBatch(rs.getInt("pb_id"),
                        Status.valueOf(rs.getString("status")), rs.getInt("recept_id")));
            }
        } catch (SQLException e) {
            log.warn(e.toString());
            throw new DALException(e);
        }
        return list;
    }

    @Override
    public void createProduktBatch(DTOProduktBatch produktbatch) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (MySQLConnector.doUpdate("call createProductBatch("+produktbatch.getPbId()+",'"+produktbatch.getStatus() + "'," +
                produktbatch.getReceptId() + ")") == 0) {
            String errMsg = "Couldn't add tuple to \"Produkt batch\".";
            log.warn(errMsg);
            throw new DALException(errMsg);
        }
    }

    @Override
    public void updateProduktBatch(DTOProduktBatch produktbatch) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (MySQLConnector.doUpdate("call updateProductBatch(" + produktbatch.getPbId() + ",'" +
                produktbatch.getStatus().toString() + "'," + produktbatch.getReceptId() + ")") == 0) {
            String errMsg = "No rows updated in \"Produkt batch\".";
            log.warn(errMsg);
            throw new DALException(errMsg);
        }
    }

    @Override
    public void deleteProduktBatch(int pbID) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (MySQLConnector.doUpdate("call deleteProductBatch(" + pbID + ")") == 0) {
            String errMsg = "No rows updated in \"Produkt batch\".";
            log.warn(errMsg);
            throw new DALException(errMsg);
        }
    }
}
