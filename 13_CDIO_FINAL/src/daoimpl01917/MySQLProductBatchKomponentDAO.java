package daoimpl01917;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector01917.Connector;
import daointerfaces01917.DALException;
import daointerfaces01917.ProduktBatchKompDAO;
import dto01917.ProduktBatchKompDTO;

public class MySQLProductBatchKomponentDAO implements ProduktBatchKompDAO {

	@Override
	public ProduktBatchKompDTO getProduktBatchKomp(int pbId, int rbId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM produktbatchkomp WHERE pb_id ="+pbId+" AND rb_id ="+rbId);
		try {
			if(!rs.first()) throw new DALException("Produktbatch komponentet med produkt batch id: "+pbId + " og raavare batch id: "+rbId+ " blev ikke fundet");
			return new ProduktBatchKompDTO(rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("opr_id"));
		}
		catch(SQLException e) {
			throw new DALException(e);
		}
	}

	@Override
	public List<ProduktBatchKompDTO> getProduktBatchKompList(int pbId) throws DALException {
		List<ProduktBatchKompDTO> list = new ArrayList<ProduktBatchKompDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM produktbatchkomp where pb_id="+pbId);
		try {
			while(rs.next()) {
				list.add(new ProduktBatchKompDTO(rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("opr_id")));
				
			}
		}
		catch(SQLException e) {
			throw new DALException(e);
		}
		return list;
	}

	@Override
	public List<ProduktBatchKompDTO> getProduktBatchKompList() throws DALException {
		List<ProduktBatchKompDTO> list = new ArrayList<ProduktBatchKompDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM produktbatchkomp");
		try {
			while(rs.next()) {
				list.add(new ProduktBatchKompDTO(rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("opr_id")));
				
			}
		}
		catch(SQLException e) {
			throw new DALException(e);
		}
		return list;
	}

	@Override
	public void createProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent) throws DALException {
		if(Connector.doUpdate("CALL createProduktBatchKomp("+produktbatchkomponent.getPbId()+","+produktbatchkomponent.getRbId()+","+produktbatchkomponent.getTara()+""
				+ ","+produktbatchkomponent.getNetto()+","+produktbatchkomponent.getOprId()+")")==0) {
			throw new DALException("Couldn't add tuple to \"Produkt batch komponent\".");
		}
	}

	@Override
	public void updateProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent) throws DALException {
		if(Connector.doUpdate("CALL updateProduktBatchKomp("+produktbatchkomponent.getPbId()+","+produktbatchkomponent.getRbId()+","
				+ ""+produktbatchkomponent.getTara()+","+produktbatchkomponent.getNetto()+","+produktbatchkomponent.getOprId()+")")==0) {
			throw new DALException("No rows updated in \"Produkt batch komponent\".");
		}
	}

	@Override
	public void deleteProduktBatchKomp(int productBatch_ID, int raavareBatch_ID) throws DALException {
		if(Connector.doUpdate("CALL deleteProduktBatchKomp("+productBatch_ID+","+raavareBatch_ID+")")==0) {
			throw new DALException("No rows updated in \"Produkt batch komponent\".");
		}
	}

}
