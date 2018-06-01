package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector.MySQLConnector;
import exception.DALException;
import interfaces.ProduktBatchDAO;
import dto.ProduktBatchDTO;

public class MySQLProduktBatchDAO implements ProduktBatchDAO{

	@Override
	public ProduktBatchDTO getProduktBatch(int pbId) throws DALException {
		ResultSet rs = MySQLConnector.doQuery("SELECT * FROM produktbatchview WHERE pb_id = " + pbId); // View
		try {
			if (!rs.first()) throw new DALException("Produktbatchet " + pbId + " findes ikke");
			return new ProduktBatchDTO(rs.getInt("pb_id"), rs.getString("status"), rs.getInt("recept_id"));
		}
		catch (SQLException e) {throw new DALException(e);} 
	}
	
	@Override
	public List<ProduktBatchDTO> getProduktBatchList() throws DALException {
		List<ProduktBatchDTO> list = new ArrayList<ProduktBatchDTO>();
		ResultSet rs = MySQLConnector.doQuery("SELECT * FROM produktbatchview");
		try
		{
			while (rs.next()) 
			{
				list.add(new ProduktBatchDTO(rs.getInt("pb_id"), rs.getString("status"), rs.getInt("recept_id")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createProduktBatch(ProduktBatchDTO produktbatch) throws DALException {
		if(!produktbatch.isValid()) {
			throw new DALException("2 Invalid data.");
		}
		if(MySQLConnector.doUpdate("call createProductBatch("+produktbatch.getStatus()+","+produktbatch.getReceptId()+")")==0) {
			throw new DALException("Couldn't add tuple to \"Produkt batch\".");
		}
	}

	@Override
	public void updateProduktBatch(ProduktBatchDTO produktbatch) throws DALException {
		if(!produktbatch.isValid()) {
			throw new DALException("2 Invalid data.");
		}
		if(MySQLConnector.doUpdate("call updateProductBatch("+produktbatch.getPbId()+","+produktbatch.getStatus()+","+produktbatch.getReceptId()+")")==0) {
			throw new DALException("No rows updated in \"Produkt batch\".");
		}
	}

	@Override
	public void deleteProduktBatch(int pbID) throws DALException {
		if(MySQLConnector.doUpdate("call deleteProductBatch("+pbID+")")==0) {
			throw new DALException("No rows updated in \"Produkt batch\".");
		}
		
	}
}
