package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector.Connector;
import exception.DALException;
import interfaces.ReceptKompDAO;
import dto.ReceptKompDTO;

public class MySQLReceptKompDAO implements ReceptKompDAO {

	@Override
	public ReceptKompDTO getReceptKomp(int receptId, int raavareId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM receptKompView WHERE recept_id="+receptId+
				" AND raavare_id="+raavareId);
		try {
			if(!rs.first()) throw new DALException("Recept komponenet med recept ID "+receptId
					+" og raavare ID "+raavareId+" findes ikke.");
			return new ReceptKompDTO(rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance"));
		} catch(SQLException e) {
			throw new DALException(e);
		}
	}

	@Override
	public List<ReceptKompDTO> getReceptKompList(int receptId) throws DALException {
		List<ReceptKompDTO> list = new ArrayList<ReceptKompDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM receptKompView WHERE recept_id="+receptId);
		
		try {
			while(rs.next()) {
				list.add(new ReceptKompDTO(rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance")));
			}
			return list;
		} catch(SQLException e){
			throw new DALException(e);
		}

	}

	@Override
	public List<ReceptKompDTO> getReceptKompList() throws DALException {
		List<ReceptKompDTO> list = new ArrayList<ReceptKompDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM receptKompView WHERE recept_id");
		
		try {
			while(rs.next()) {
				list.add(new ReceptKompDTO(rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance")));
			}
			return list;
		} catch(SQLException e){
			throw new DALException(e);
		}
	}

	@Override
	public void createReceptKomp(ReceptKompDTO receptkomponent) throws DALException {
		if(receptkomponent.getNomNetto() <= 0 || receptkomponent.getTolerance() <= 0) {
			throw new DALException("Netto or tolerance was less than or equal to 0");
		}
		if(Connector.doUpdate("CALL createReceptkomponent("+receptkomponent.getReceptId()+", "+receptkomponent.getRaavareId()+", "+receptkomponent.getNomNetto()+", "+receptkomponent.getTolerance()+");")==0){
			throw new DALException("Couldn't add tuple to \"Recept komponent\".");
		}
	}

	@Override
	public void updateReceptKomp(ReceptKompDTO receptkomponent) throws DALException {
		if(Connector.doUpdate("CALL updateReceptkomponent("+receptkomponent.getReceptId()+", "+receptkomponent.getRaavareId()+", "+receptkomponent.getNomNetto()+", "+receptkomponent.getTolerance()+");")==0) {
			throw new DALException("No rows updated in \"Recept komponent\".");
		}
	}

	@Override
	public void deleteReceptKomp(int recept_id, int raavare_id) throws DALException{
		if(Connector.doUpdate("CALL deleteReceptKomp("+recept_id+", "+raavare_id+");")==0) {
			throw new DALException("No rows updated in \"Recept komponent\".");
		}
	}
	
}
