package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import connector.MySQLConnector;
import exception.DALException;
import interfaces.IDAOOperatoer;
import dto.DTOOperatoer;
import dto.DTOOperatoer.Aktiv;
import logging.LogHandler;
import lombok.extern.java.Log;

@Log
public class DAOOperatoer implements IDAOOperatoer {

    public DAOOperatoer(){
        new LogHandler(log, "DAO");
    }

    //Get operator with specific ID
    public DTOOperatoer getOperatoer(int oprId) throws DALException {
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM operatoer WHERE opr_id = " + oprId); // View
        try {
            if (!rs.first()) throw new DALException("Operatoeren " + oprId + " findes ikke.");
            return new DTOOperatoer(rs.getInt("opr_id"), rs.getString("fornavn"),
                    rs.getString("efternavn"), rs.getString("cpr"),
                    rs.getString("roller"),
                    Aktiv.valueOf(rs.getString("aktiv")));
        } catch (SQLException e) {
            log.severe(e.toString());
            throw new DALException(e);
        }

    }

    public void createOperatoer(DTOOperatoer opr) throws DALException {
        opr.formatCPR();
        if (MySQLConnector.doUpdate("CALL createOperator('" + opr.getCpr() + "','" + opr.getRoles() +
                "','" + opr.getFornavn() + "','" + opr.getEfternavn() + "','aktiv')") == 0) {
            String errMsg = "Couldn't add tuple to \"Operatoer\".";
            log.severe(errMsg);
            throw new DALException(errMsg);
        }
    }

    public void updateOperatoer(DTOOperatoer opr) throws DALException {
        opr.formatCPR();
        if (MySQLConnector.doUpdate("CALL updateOperator(" + opr.getOprId() + ",'" + opr.getCpr() + "','" + opr.getRoles() + "','" + opr.getFornavn() + "','" + opr.getEfternavn() + "','" + opr.getAktiv() + "')") == 0) {
            String errMsg = "No rows updated in \"Operatoer\".";
            log.severe(errMsg);
            throw new DALException(errMsg);
        }
    }

    public List<DTOOperatoer> getOperatoerList() throws DALException {
        List<DTOOperatoer> list = new ArrayList<DTOOperatoer>();
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM operatoer");
        try {
            while (rs.next()) {
                list.add(new DTOOperatoer(rs.getInt("opr_id"), rs.getString("fornavn"),
                        rs.getString("efternavn"), rs.getString("cpr"),
                        rs.getString("roller"),
                        Aktiv.valueOf(rs.getString("aktiv"))));
            }
        } catch (SQLException e) {
            log.severe(e.toString());
            throw new DALException(e);
        }
        return list;
    }

    @Override
    public void deleteOperatoer(int opr_id) throws DALException {
        if (MySQLConnector.doUpdate("CALL deleteOperator('" + opr_id + "','aktiv')") == 0) {
            String errMsg = "Couldn't add tuple to \"Operatoer\".";
            log.severe(errMsg);
            throw new DALException(errMsg);
        }
    }
}

