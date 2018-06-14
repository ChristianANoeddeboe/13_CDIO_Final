package interfaces;

import java.sql.SQLException;

import javax.ws.rs.core.Response;

import dto.DTOBruger;
import exception.DALException;

public interface IBrugerService {
	Response getBrugerList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	Response createBruger(DTOBruger opr) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	Response updateBruger(DTOBruger opr) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	Response deleteBruger(int opr_id) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
}
