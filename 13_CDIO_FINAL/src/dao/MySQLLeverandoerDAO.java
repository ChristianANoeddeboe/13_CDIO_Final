package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector.MySQLConnector;
import controller.ErrorChecking;
import dto.LeverandoerDTO;
import exception.DALException;
import interfaces.LeverandoerDAO;
import lombok.extern.java.Log;

@Log
public class MySQLLeverandoerDAO implements LeverandoerDAO{

	@Override
	public LeverandoerDTO getRaavare(int raavareId) throws DALException {
		ResultSet rs = MySQLConnector.doQuery("SELECT * FROM raavareview where raavare_id = " + raavareId);
		String out = "";
		try
		{
			while (rs.next())
			{
				out = out.concat(rs.getString("leverandoer") + " ");
			}
			return new LeverandoerDTO(raavareId, out);
		}
		catch (SQLException e) { throw new DALException(e); }
	}

	@Override
	public List<LeverandoerDTO> getRaavareList() throws DALException {
		List<LeverandoerDTO> list = new ArrayList<>();
		ResultSet rs = MySQLConnector.doQuery("SELECT * FROM raavareview");
		try
		{
			while (rs.next())
			{
				list.add(new LeverandoerDTO(rs.getInt("raavare_id"), rs.getString("leverandoer")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createRaavare(LeverandoerDTO raavare) throws DALException {
		validateData(raavare);
		if(MySQLConnector.doUpdate("call createRaavare('"+raavare.getLeverandoer()+"','"+raavare.getRaavareId()+"')")==0) {
			throw new DALException("Couldn't add tuple to \"Raavare\".");
		}

	}

	@Override
	public void updateRaavare(LeverandoerDTO raavare) throws DALException {
		validateData(raavare);
		if(MySQLConnector.doUpdate("call updateLeverandoer('"+raavare.getRaavareId()+"','"+raavare.getLeverandoer()+"')")==0) {
			throw new DALException("No rows updated in \"Raavare\".");
		}

	}

	@Override
	public void deleteRaavare(int recept_id) throws DALException {
		if(MySQLConnector.doUpdate("call deleteLeverandoer('"+recept_id+"')")==0) {
			throw new DALException("No rows updated in \"Raavare\".");
		}

	}
	
	private void validateData(LeverandoerDTO leverandoer) throws DALException {
        String errMsg;
        errMsg = ErrorChecking.checkIntSize(leverandoer.getRaavareId());
        throwException(errMsg);
        errMsg = ErrorChecking.checkStrSize(leverandoer.getLeverandoer());
        throwException(errMsg);
    }

    private void throwException(String errMsg) throws DALException {
        if(errMsg != null){
            log.severe(errMsg);
            throw new DALException(errMsg);
        }
    }

}