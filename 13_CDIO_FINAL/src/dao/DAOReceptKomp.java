package dao;

import connector.MySQLConnector;
import dto.DTOReceptKomp;
import exception.DALException;
import interfaces.IDAOReceptKomp;
import org.slf4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOReceptKomp implements IDAOReceptKomp {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DAOReceptKomp.class);

    public DAOReceptKomp() {
    }

    @Override
    public DTOReceptKomp getReceptKomp(int receptId, int raavareId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM receptKompView WHERE recept_id=" + receptId +
                " AND raavare_id=" + raavareId);
        try {
            if (!rs.first()) throw new DALException("Recept komponenet med recept ID " + receptId
                    + " og raavare ID " + raavareId + " findes ikke.");
            return new DTOReceptKomp(rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance"));
        } catch (SQLException e) {
            log.warn(e.toString());
            throw new DALException(e);
        }
    }

    @Override
    public List<DTOReceptKomp> getReceptKompList(int receptId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        List<DTOReceptKomp> list = new ArrayList<DTOReceptKomp>();
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM receptKompView WHERE recept_id=" + receptId);

        try {
            while (rs.next()) {
                list.add(new DTOReceptKomp(rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance")));
            }
            return list;
        } catch (SQLException e) {
            log.warn(e.toString());
            throw new DALException(e);
        }
    }

    @Override
    public List<DTOReceptKomp> getReceptKompList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        List<DTOReceptKomp> list = new ArrayList<DTOReceptKomp>();
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM receptKompView WHERE recept_id");

        try {
            while (rs.next()) {
                list.add(new DTOReceptKomp(rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance")));
            }
            return list;
        } catch (SQLException e) {
            log.warn(e.toString());
            throw new DALException(e);
        }
    }

    @Override
    public void createReceptKomp(DTOReceptKomp receptkomponent) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (receptkomponent.getNomNetto() <= 0 || receptkomponent.getTolerance() <= 0) {
            log.warn("Netto or tolerance was less than or equal to 0");
            throw new DALException("Netto or tolerance was less than or equal to 0");
        }
        if (MySQLConnector.doUpdate("CALL createReceptkomponent(" + receptkomponent.getReceptId() + ", " + receptkomponent.getRaavareId() + ", " + receptkomponent.getNomNetto() + ", " + receptkomponent.getTolerance() + ");") == 0) {
            String errMsg = "Couldn't add tuple to \"Recept komponent\".";
            log.warn(errMsg);
            throw new DALException(errMsg);
        }
    }

    @Override
    public void updateReceptKomp(DTOReceptKomp receptkomponent) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (MySQLConnector.doUpdate("CALL updateReceptkomponent(" + receptkomponent.getReceptId() + ", " + receptkomponent.getRaavareId() + ", " + receptkomponent.getNomNetto() + ", " + receptkomponent.getTolerance() + ");") == 0) {
            String errMsg = "No rows updated in \"Recept komponent\".";
            log.warn(errMsg);
            throw new DALException(errMsg);
        }
    }

    @Override
    public void deleteReceptKomp(int recept_id, int raavare_id) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (MySQLConnector.doUpdate("CALL deleteReceptKomp(" + recept_id + ", " + raavare_id + ");") == 0) {
            String errMsg = "No rows updated in \"Recept komponent\".";
            log.warn(errMsg);
            throw new DALException(errMsg);
        }
    }
}
