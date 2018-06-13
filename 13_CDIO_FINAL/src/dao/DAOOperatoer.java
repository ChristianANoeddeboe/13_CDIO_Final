package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import connector.MySQLConnector;
import exception.DALException;
import interfaces.IDAOOperatoer;
import dto.DTOOperatoer;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class DAOOperatoer implements IDAOOperatoer {

    @Override
    public DTOOperatoer getOperatoer(int oprId) throws DALException {
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM operatoer WHERE opr_id = " + oprId); // View
        try {
            if (!rs.first()) throw new DALException("Operatoeren " + oprId + " findes ikke.");
            return new DTOOperatoer(rs.getInt("opr_id"), rs.getString("fornavn"),
                    rs.getString("efternavn"), rs.getString("cpr"),
                    dto.Roller.valueOf(rs.getString("roller")),
                    dto.Aktiv.valueOf(rs.getString("aktiv")));
        } catch (SQLException e) {
            log.warn(e.toString());
            throw new DALException(e);
        }
    }

    @Override
    public void createOperatoer(DTOOperatoer opr) throws DALException {
        opr.formatCPR();
        if (MySQLConnector.doUpdate("CALL createOperator('" + opr.getCpr() + "','" + opr.getRoles() +
                "','" + opr.getFornavn() + "','" + opr.getEfternavn() + "','aktiv')") == 0) {
            String errMsg = "Couldn't add tuple to \"Operatoer\".";
            log.warn(errMsg);
            throw new DALException(errMsg);
        }
    }

    @Override
    public void updateOperatoer(DTOOperatoer opr) throws DALException {
        opr.formatCPR();
        if (MySQLConnector.doUpdate("CALL updateOperator(" + opr.getOprId() + "," + opr.getCpr() + ",'" + opr.getRoles() + "','" + opr.getFornavn() + "','" + opr.getEfternavn() + "','" + opr.getAktiv() + "')") == 0) {
            String errMsg = "No rows updated in \"Operatoer\".";
            log.warn(errMsg);
            throw new DALException(errMsg);
        }
    }

    @Override
    public List<DTOOperatoer> getOperatoerList() throws DALException {
        List<DTOOperatoer> list = new ArrayList<DTOOperatoer>();
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM operatoer");
        try {
            while (rs.next()) {
                list.add(new DTOOperatoer(rs.getInt("opr_id"), rs.getString("fornavn"),
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
    public void deleteOperatoer(int opr_id) throws DALException {
        if (MySQLConnector.doUpdate("CALL deleteOperator('" + opr_id + "')") == 0) {
            String errMsg = "Couldn't add tuple to \"Operatoer\".";
            log.warn(errMsg);
            throw new DALException(errMsg);
        }
    }
}

