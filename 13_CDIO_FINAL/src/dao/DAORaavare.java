package dao;

import connector.MySQLConnector;
import exception.DALException;
import interfaces.IDAORaavare;
import dto.DTORaavare;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor
public class DAORaavare implements IDAORaavare {

    @Override
    public DTORaavare getRaavare(int raavareId) throws DALException {
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM raavareview WHERE raavare_id = " + raavareId);
        try {
            if (!rs.first()) throw new DALException("Raavaren " + raavareId + " findes ikke.");
            return new DTORaavare(rs.getInt("raavare_id"), rs.getString("raavare_navn"), rs.getString("leverandoer"));
        } catch (SQLException e) {
            log.warn(e.toString());
            throw new DALException(e);
        }
    }

    @Override
    public List<DTORaavare> getRaavareList() throws DALException {
        List<DTORaavare> list = new ArrayList<>();
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM raavareview");
        try
        {
            while (rs.next())
            {
                list.add(new DTORaavare(rs.getInt("raavare_id"), rs.getString("raavare_navn"), rs.getString("leverandoer")));
            }
        }
        catch (SQLException e) {
            log.warn(e.toString());
            throw new DALException(e);
        }
        return list;
    }

    @Override
    public void createRaavare(DTORaavare raavare) throws DALException {
        if(MySQLConnector.doUpdate("call createRaavare('"+raavare.getRaavareNavn()+"','"+raavare.getLeverandoer()+"','"+raavare.getRaavareId()+"')")==0) {
        	String errMsg = "Couldn't add tuple to \"Raavare\".";
        	log.warn(errMsg);
            throw new DALException(errMsg);
        }
    }

    @Override
    public void updateRaavare(DTORaavare raavare) throws DALException {
        if(MySQLConnector.doUpdate("call updateRaavare('"+raavare.getRaavareId()+"','"+raavare.getRaavareNavn()+"','"+raavare.getLeverandoer()+"')")==0) {
        	String errMsg = "No rows updated in \"Raavare\".";
        	log.warn(errMsg);
            throw new DALException(errMsg);
        }
    }

	@Override
	public void deleteRaavare(int raavare_id) throws DALException {
		if(MySQLConnector.doUpdate("call deleteRaavare('"+raavare_id+"')")==0) {
        	String errMsg = "No rows updated in \"Raavare\".";
        	log.warn(errMsg);
            throw new DALException(errMsg);
        }
	}
}
