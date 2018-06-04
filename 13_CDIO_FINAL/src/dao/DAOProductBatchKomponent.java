package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector.MySQLConnector;
import exception.DALException;
import interfaces.IDAOProduktBatchKomp;
import dto.DTOProduktBatchKomp;
import lombok.extern.java.Log;

@Log
public class DAOProductBatchKomponent implements IDAOProduktBatchKomp {

    @Override
    public DTOProduktBatchKomp getProduktBatchKomp(int pbId, int rbId) throws DALException {
        try {
            ResultSet rs = MySQLConnector.doQuery("SELECT * FROM produktbatchkomp WHERE pb_id =" + pbId + " AND rb_id =" + rbId);
            if (!rs.first())
                throw new DALException("Produktbatch komponentet med produkt batch id: " + pbId + " og raavare batch id: " + rbId + " blev ikke fundet");
            return new DTOProduktBatchKomp(rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("opr_id"));
        } catch (SQLException e) {
            log.severe(e.toString());
            throw new DALException(e);
        }
    }

    @Override
    public List<DTOProduktBatchKomp> getProduktBatchKompList(int pbId) throws DALException {
        List<DTOProduktBatchKomp> list = new ArrayList<DTOProduktBatchKomp>();
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM produktbatchkomp where pb_id=" + pbId);
        try {
            while (rs.next()) {
                list.add(new DTOProduktBatchKomp(rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("opr_id")));

            }
        } catch (SQLException e) {
            log.severe(e.toString());
            throw new DALException(e);
        }
        return list;
    }

    @Override
    public List<DTOProduktBatchKomp> getProduktBatchKompList() throws DALException {
        List<DTOProduktBatchKomp> list = new ArrayList<DTOProduktBatchKomp>();
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM produktbatchkomp");
        try {
            while (rs.next()) {
                list.add(new DTOProduktBatchKomp(rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("opr_id")));

            }
        } catch (SQLException e) {
            log.severe(e.toString());
            throw new DALException(e);
        }
        return list;
    }

    @Override
    public void createProduktBatchKomp(DTOProduktBatchKomp produktbatchkomponent) throws DALException {
        if (MySQLConnector.doUpdate("CALL createProduktBatchKomp(" + produktbatchkomponent.getPbId() + "," + produktbatchkomponent.getRbId() + "," + produktbatchkomponent.getTara() + ""
                + "," + produktbatchkomponent.getNetto() + "," + produktbatchkomponent.getOprId() + ")") == 0) {
            String errMsg = "Couldn't add tuple to \"Produkt batch komponent\".";
            log.severe(errMsg);
            throw new DALException(errMsg);
        }
    }

    @Override
    public void updateProduktBatchKomp(DTOProduktBatchKomp produktbatchkomponent) throws DALException {
        if (MySQLConnector.doUpdate("CALL updateProduktBatchKomp(" + produktbatchkomponent.getPbId() + "," + produktbatchkomponent.getRbId() + ","
                + "" + produktbatchkomponent.getTara() + "," + produktbatchkomponent.getNetto() + "," + produktbatchkomponent.getOprId() + ")") == 0) {
            String errMsg = "No rows updated in \"Produkt batch komponent\".";
            log.severe(errMsg);
            throw new DALException(errMsg);
        }
    }

    @Override
    public void deleteProduktBatchKomp(int productBatch_ID, int raavareBatch_ID) throws DALException {
        if (MySQLConnector.doUpdate("CALL deleteProduktBatchKomp(" + productBatch_ID + "," + raavareBatch_ID + ")") == 0) {
            String errMsg = "No rows updated in \"Produkt batch komponent\".";
            log.severe(errMsg);
            throw new DALException(errMsg);
        }
    }
}