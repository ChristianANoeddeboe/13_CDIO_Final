package dao;

import connector.MySQLConnector;
import dto.DTOBruger;
import exception.DALException;
import interfaces.IDAOOperatoer;
import org.slf4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOBruger implements IDAOOperatoer {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DAOBruger.class);

    public DAOBruger() {
    }

    //Get operator with specific ID
    public DTOBruger getOperatoer(int oprId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM operatoer WHERE opr_id = " + oprId); // View
        try {
            if (!rs.first()) throw new DALException("Operatoeren " + oprId + " findes ikke.");
            return new DTOBruger(rs.getInt("opr_id"), rs.getString("fornavn"),
                    rs.getString("efternavn"), rs.getString("cpr"),
                    dto.Roller.valueOf(rs.getString("roller")),
                    dto.Aktiv.valueOf(rs.getString("aktiv")));
        } catch (SQLException e) {
            log.warn(e.toString());
            throw new DALException(e);
        }

    }

    public void createOperatoer(DTOBruger opr) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        opr.formatCPR();
        if (MySQLConnector.doUpdate("CALL createOperator('" + opr.getCpr() + "','" + opr.getRoles() +
                "','" + opr.getFornavn() + "','" + opr.getEfternavn() + "','aktiv')") == 0) {
            String errMsg = "Couldn't add tuple to \"Operatoer\".";
            log.warn(errMsg);
            throw new DALException(errMsg);
        }
    }

    public void updateOperatoer(DTOBruger opr) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        opr.formatCPR();
        if (MySQLConnector.doUpdate("CALL updateOperator(" + opr.getOprId() + "," + opr.getCpr() + ",'" + opr.getRoles() + "','" + opr.getFornavn() + "','" + opr.getEfternavn() + "','" + opr.getAktiv() + "')") == 0) {
            String errMsg = "No rows updated in \"Operatoer\".";
            log.warn(errMsg);
            throw new DALException(errMsg);
        }
    }

    public List<DTOBruger> getOperatoerList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        List<DTOBruger> list = new ArrayList<DTOBruger>();
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM operatoer");
        try {
            while (rs.next()) {
                list.add(new DTOBruger(rs.getInt("opr_id"), rs.getString("fornavn"),
                        rs.getString("efternavn"), rs.getString("cpr"),
                        dto.Roller.valueOf(rs.getString("roller")),
                        dto.Aktiv.valueOf(rs.getString("aktiv"))));
            }
        } catch (SQLException e) {
            log.warn(e.toString());
            throw new DALException(e);
        }
        return list;
    }

    @Override
    public void deleteOperatoer(int opr_id) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (MySQLConnector.doUpdate("CALL deleteOperator('" + opr_id + "')") == 0) {
            String errMsg = "Couldn't add tuple to \"Operatoer\".";
            log.warn(errMsg);
            throw new DALException(errMsg);
        }
    }
}

