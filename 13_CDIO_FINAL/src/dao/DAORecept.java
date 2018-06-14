package dao;

import connector.MySQLConnector;
import dto.DTORecept;
import exception.DALException;
import interfaces.IDAORecept;
import org.slf4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAORecept implements IDAORecept {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DAORecept.class);

    public DAORecept() {
    }

    @Override
    public DTORecept getRecept(int receptId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        //Select the entry from receptView that matches the ID.
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM receptView WHERE recept_id=" + receptId);

        try {
            if (!rs.first()) throw new DALException("Recept " + receptId + " findes ikke.");
            return new DTORecept(rs.getInt("recept_id"), rs.getString("recept_navn"));
        } catch (SQLException e) {
            log.warn(e.toString());
            throw new DALException(e);
        }
    }

    @Override
    public List<DTORecept> getReceptList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        List<DTORecept> list = new ArrayList<DTORecept>();
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM receptView");

        try {
            while (rs.next()) {
                list.add(new DTORecept(rs.getInt("recept_id"), rs.getString("recept_navn")));
            }
            return list;
        } catch (SQLException e) {
            log.warn(e.toString());
            throw new DALException(e);
        }
    }

    @Override
    public void createRecept(DTORecept recept) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (MySQLConnector.doUpdate("CALL createRecept("+recept.getReceptId()+ ",'" + recept.getReceptNavn() + "');") == 0) {
            String errMsg = "Couldn't add tuple to \"Recept\".";
            log.warn(errMsg);
            throw new DALException(errMsg);
        }
    }

    @Override
    public void updateRecept(DTORecept recept) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (MySQLConnector.doUpdate("CALL updateRecept(" + recept.getReceptId() + ", '" + recept.getReceptNavn() + "');") == 0) {
            String errMsg = "No rows updated in \"Recept\".";
            log.warn(errMsg);
            throw new DALException(errMsg);
        }
    }

    @Override
    public void deleteRecept(int receptID) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (MySQLConnector.doUpdate("CALL deleteRecept(" + receptID + ");") == 0) {
            String eerMsg = "No rows deleted in \"Recept\".";
            log.warn(eerMsg);
            throw new DALException(eerMsg);
        }
    }
}
