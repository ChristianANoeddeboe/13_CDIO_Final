package dao;

import connector.MySQLConnector;
import exception.DALException;
import interfaces.RaavareBatchDAO;
import dto.RaavareBatchDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLRaavareBatchDAO implements RaavareBatchDAO {

    @Override
    public RaavareBatchDTO getRaavareBatch(int rbId) throws DALException {
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM raavarebatchview WHERE rb_id = " + rbId);
        try {
            if (!rs.first()) throw new DALException("RaavareBatchen " + rbId + " findes ikke.");
            return new RaavareBatchDTO(rs.getInt("rb_id"), rs.getInt("raavare_id"), rs.getDouble("maengde"));
        } catch (SQLException e) {throw new DALException(e);}
    }
    

    @Override
    public List<RaavareBatchDTO> getRaavareBatchList() throws DALException {
        List<RaavareBatchDTO> list = new ArrayList<>();
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM raavarebatchview");
        try {
            while (rs.next()) {
                list.add(new RaavareBatchDTO(rs.getInt("rb_id"), rs.getInt("raavare_id"), rs.getDouble("maengde")));
            }
        } catch (SQLException e) {
            throw new DALException(e);
        }
        return list;
    }

    @Override
    public List<RaavareBatchDTO> getRaavareBatchList(int raavareId) throws DALException {
        List<RaavareBatchDTO> list = new ArrayList<>();
        ResultSet rs = MySQLConnector.doQuery("SELECT * FROM raavarebatchview WHERE raavare_id = " + raavareId);
        try {
            while (rs.next()) {
                list.add(new RaavareBatchDTO(rs.getInt("rb_id"), rs.getInt("raavare_id"), rs.getDouble("maengde")));
            }
        } catch (SQLException e) {
            throw new DALException(e);
        }
        return list;
    }

    @Override
    public void createRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException {
    	if(!raavarebatch.isValid()) {
			throw new DALException("2 Invalid data.");
		}
        if(MySQLConnector.doUpdate("call createRaavareBatch("+raavarebatch.getRaavareId()+","+raavarebatch.getMaengde()+")")==0){
        	throw new DALException("Couldn't add tuple to \"Raavare batch\".");
        }
    }

    @Override
    public void updateRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException {
    	if(!raavarebatch.isValid()) {
			throw new DALException("2 Invalid data.");
		}
        if(MySQLConnector.doUpdate("call updateRaavareBatch("+raavarebatch.getRbId()+","+raavarebatch.getRaavareId()+","+raavarebatch.getMaengde()+")")==0) {
        	throw new DALException("No rows updated in \"Raavare batch\".");
        }
    }

	@Override
	public void deleteRaavareBatch(int raavarebatch_ID) throws DALException {
		if(MySQLConnector.doUpdate("call updateRaavareBatch("+raavarebatch_ID+")")==0) {
        	throw new DALException("No rows updated in \"Raavare batch\".");
        }
		
	}


	@Override
	public RaavareBatchDTO getRaavareBatchRaavare(int raavareID) throws DALException {
		 ResultSet rs = MySQLConnector.doQuery("SELECT * FROM raavarebatchview WHERE raavare_id = " + raavareID);
	        try {
	            if (!rs.first()) throw new DALException("Raavaren " + raavareID + " findes ikke.");
	            return new RaavareBatchDTO(rs.getInt("rb_id"), rs.getInt("raavare_id"), rs.getDouble("maengde"));
	        } catch (SQLException e) {throw new DALException(e);}
	}
}
