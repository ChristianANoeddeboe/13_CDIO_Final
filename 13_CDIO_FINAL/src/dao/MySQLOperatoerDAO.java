package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import connector.Connector;
import exception.DALException;
import interfaces.OperatoerDAO;
import dto.OperatoerDTO;
import dao.ErrorChecking;
public class MySQLOperatoerDAO implements OperatoerDAO {
	//Get operator with specific ID
	public OperatoerDTO getOperatoer(int oprId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM operatoer WHERE opr_id = " + oprId); // View
		try {
			if (!rs.first()) throw new DALException("Operatoeren " + oprId + " findes ikke.");
			return new OperatoerDTO (rs.getInt("opr_id"), rs.getString("fornavn"),
					rs.getString("efternavn"), rs.getString("cpr"),
					rs.getString("password"), rs.getString("roller"),
					rs.getString("aktiv"));
		}
		catch (SQLException e) {
			throw new DALException(e); 
		}

	}

	public void createOperatoer(OperatoerDTO opr) throws DALException {
		if(!opr.isValid()) {throw new DALException("Couldn't add tuple to \"Operatoer\".");}
		opr.formatCPR();
		if(Connector.doUpdate("CALL createOperator('"+opr.getCpr()+"','"+opr.getPassword()+"','"+opr.getRoles()+
				"','"+opr.getForNavn()+"','"+opr.getEfterNavn()+"','aktiv')")==0) {
			throw new DALException("Couldn't add tuple to \"Operatoer\".");
		}
	}	
	
	public void updateOperatoer(OperatoerDTO opr) throws DALException {
		if(!opr.isValid()) {throw new DALException("Error updating \"Operatoer\".");}
		opr.formatCPR();
		if(Connector.doUpdate("CALL updateOperator("+opr.getOprId()+",'"+opr.getCpr()+"','"+opr.getPassword()+
				"','"+opr.getRoles()+"','"+opr.getForNavn()+"','"+opr.getEfterNavn()+"','"+opr.getAktiv()+"')")==0) {
			throw new DALException("No rows updated in \"Operatoer\".");
		}
	}
	
	public List<OperatoerDTO> getOperatoerList() throws DALException {
		List<OperatoerDTO> list = new ArrayList<OperatoerDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM operatoer");
		try {
			while (rs.next()) {
				list.add(new OperatoerDTO(rs.getInt("opr_id"), rs.getString("fornavn"),
						rs.getString("efternavn"), rs.getString("cpr"),
						rs.getString("password"), rs.getString("roller"),
						rs.getString("aktiv")));
			}
		}
		catch (SQLException e) { 
			throw new DALException(e); 
		}
		return list;
	}

	@Override
	public void deleteOperatoer(int opr_id) throws DALException {
		if(Connector.doUpdate("CALL deleteOperator('"+opr_id+"','aktiv')")==0) {
			throw new DALException("Couldn't add tuple to \"Operatoer\".");
		}
	}
}

