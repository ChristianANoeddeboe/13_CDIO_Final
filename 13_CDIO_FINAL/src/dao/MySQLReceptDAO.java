package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector.Connector;
import exception.DALException;
import interfaces.ReceptDAO;
import dto.ReceptDTO;

public class MySQLReceptDAO implements ReceptDAO {
	
	/*Return a single recept based on the recept ID.*/
	@Override
	public ReceptDTO getRecept(int receptId) throws DALException {
		//Select the entry from receptView that matches the ID.
		ResultSet rs = Connector.doQuery("SELECT * FROM receptView WHERE recept_id="+receptId);
		
		try {
			if(!rs.first()) throw new DALException("Recept "+receptId+" findes ikke.");
			return new ReceptDTO(rs.getInt("recept_id"), rs.getString("recept_navn"));
		}catch(SQLException e) {
			throw new DALException(e);
		}
	}

	/*Return the entire lists of recepts.*/
	@Override
	public List<ReceptDTO> getReceptList() throws DALException {
		List<ReceptDTO> list = new ArrayList<ReceptDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM receptView");
		
		try {
			while(rs.next()) {
				list.add(new ReceptDTO(rs.getInt("recept_id"), rs.getString("recept_navn")));
			}
			return list;
		} catch(SQLException e) {
			throw new DALException(e);
		}
	}
	
	@Override
	public void createRecept(ReceptDTO recept) throws DALException {
		if(!recept.isValid()) {
			throw new DALException("2 Invalid data.");
		}
		if(Connector.doUpdate("CALL createRecept('"+recept.getReceptNavn()+"');") == 0) {
			throw new DALException("Couldn't add tuple to \"Recept\".");
		}
	}
	
	@Override
	public void updateRecept(ReceptDTO recept) throws DALException {
		if(Connector.doUpdate("CALL updateRecept("+recept.getReceptId()+", '"+recept.getReceptNavn()+"');")==0) {
			throw new DALException("No rows updated in \"Recept\".");
		}
	}

	@Override
	public void deleteRecept(int receptID) throws DALException {
		if(Connector.doUpdate("CALL deleteRecept("+receptID+"');")==0) {
			throw new DALException("No rows deleted in \"Recept\".");
		}
		
	}
}
