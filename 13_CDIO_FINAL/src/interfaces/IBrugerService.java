package interfaces;

import dto.DTOBruger;
import exception.DALException;

import javax.ws.rs.core.Response;
import java.sql.SQLException;

public interface IBrugerService {
	Response getBrugerList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	Response createBruger(DTOBruger opr) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	Response updateBruger(DTOBruger opr) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	Response deleteBruger(int opr_id) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
}
