package dao;

import connector.MySQLConnector;
import exception.DALException;
import interfaces.RaavareDAO;
import dto.RaavareDTO;
import lombok.extern.java.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log
public class MySQLRaavareDAO implements RaavareDAO {

    @Override
    public RaavareDTO getRaavare(int raavareId) throws DALException {
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM raavareview WHERE raavare_id = " + raavareId);
        try {
            if (!rs.first()) throw new DALException("Raavaren " + raavareId + " findes ikke.");
            return new RaavareDTO(rs.getInt("raavare_id"), rs.getString("raavare_navn"), rs.getString("leverandoer"));
        } catch (SQLException e) {throw new DALException(e);}
    }

    @Override
    public List<RaavareDTO> getRaavareList() throws DALException {
        List<RaavareDTO> list = new ArrayList<>();
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM raavareview");
        try
        {
            while (rs.next())
            {
                list.add(new RaavareDTO(rs.getInt("raavare_id"), rs.getString("raavare_navn"), rs.getString("leverandoer")));
            }
        }
        catch (SQLException e) { throw new DALException(e); }
        return list;
    }

    @Override
    public void createRaavare(RaavareDTO raavare) throws DALException {
    	validateData(raavare);
        if(MySQLConnector.doUpdate("call createRaavare('"+raavare.getRaavareNavn()+"','"+raavare.getLeverandoer()+"','"+raavare.getRaavareId()+"')")==0) {
        	throw new DALException("Couldn't add tuple to \"Raavare\".");
        }
    }

    @Override
    public void updateRaavare(RaavareDTO raavare) throws DALException {
    	validateData(raavare);
        if(MySQLConnector.doUpdate("call updateRaavare('"+raavare.getRaavareId()+"','"+raavare.getRaavareNavn()+"','"+raavare.getLeverandoer()+"')")==0) {
        	throw new DALException("No rows updated in \"Raavare\".");
        }
    }

	@Override
	public void deleteRaavare(int recept_id) throws DALException {
		if(MySQLConnector.doUpdate("call deleteRaavare('"+recept_id+"')")==0) {
        	throw new DALException("No rows updated in \"Raavare\".");
        }
	}

    private void validateData(RaavareDTO raavare) throws DALException {
        String errMsg;
        errMsg = ErrorChecking.checkIntSize(raavare.getRaavareId());
        throwException(errMsg);
        errMsg = ErrorChecking.checkStrSize(raavare.getRaavareNavn());
        throwException(errMsg);
        errMsg = ErrorChecking.checkStrSize(raavare.getLeverandoer());
        throwException(errMsg);
    }

    private void throwException(String errMsg) throws DALException {
        if(errMsg != null){
            log.severe(errMsg);
            throw new DALException(errMsg);
        }
    }
}
