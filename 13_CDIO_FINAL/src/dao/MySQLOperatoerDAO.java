package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import connector.MySQLConnector;
import exception.DALException;
import interfaces.OperatoerDAO;
import dto.OperatoerDTO;
import dto.OperatoerDTO.Aktiv;
public class MySQLOperatoerDAO implements OperatoerDAO {
	//Get operator with specific ID
	public OperatoerDTO getOperatoer(int oprId) throws DALException {
		ResultSet rs = MySQLConnector.doQuery("SELECT * FROM operatoer WHERE opr_id = " + oprId); // View
		try {
			if (!rs.first()) throw new DALException("Operatoeren " + oprId + " findes ikke.");
			return new OperatoerDTO (rs.getInt("opr_id"), rs.getString("fornavn"),
					rs.getString("efternavn"), rs.getString("cpr"),
					rs.getString("roller"),
					Aktiv.valueOf(rs.getString("aktiv")));
		}
		catch (SQLException e) {
			throw new DALException(e); 
		}

	}

	public void createOperatoer(OperatoerDTO opr) throws DALException {
		opr.formatCPR();
		if(MySQLConnector.doUpdate("CALL createOperator('"+opr.getCpr()+"','"+opr.getRoles()+
				"','"+opr.getFornavn()+"','"+opr.getEfternavn()+"','aktiv')")==0) {
			throw new DALException("Couldn't add tuple to \"Operatoer\".");
		}
	}	
	
	public void updateOperatoer(OperatoerDTO opr) throws DALException {
		opr.formatCPR();
		if(MySQLConnector.doUpdate("CALL updateOperator("+opr.getOprId()+",'"+opr.getCpr()+"','"+opr.getRoles()+"','"+opr.getFornavn()+"','"+opr.getEfternavn()+"','"+opr.getAktiv()+"')")==0) {
			throw new DALException("No rows updated in \"Operatoer\".");
		}
	}
	
	public List<OperatoerDTO> getOperatoerList() throws DALException {
		List<OperatoerDTO> list = new ArrayList<OperatoerDTO>();
		ResultSet rs = MySQLConnector.doQuery("SELECT * FROM operatoer");
		try {
			while (rs.next()) {
				list.add(new OperatoerDTO(rs.getInt("opr_id"), rs.getString("fornavn"),
						rs.getString("efternavn"), rs.getString("cpr"),
						 rs.getString("roller"),
						Aktiv.valueOf(rs.getString("aktiv"))));
			}
		}
		catch (SQLException e) { 
			throw new DALException(e); 
		}
		return list;
	}

	@Override
	public void deleteOperatoer(int opr_id) throws DALException {
		if(MySQLConnector.doUpdate("CALL deleteOperator('"+opr_id+"','aktiv')")==0) {
			throw new DALException("Couldn't add tuple to \"Operatoer\".");
		}
	}
}

