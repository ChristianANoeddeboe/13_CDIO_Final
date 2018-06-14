package interfaces;

import java.util.List;

import dto.DTOBruger;
import exception.DALException;

public interface IBrugerController {

	DTOBruger getBruger(int brugerId)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	List<DTOBruger> getBrugerList()
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	void createBruger(DTOBruger brugerDTO)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	void updateBruger(DTOBruger brugerDTO)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	void deleteBruger(int brugerId)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

}