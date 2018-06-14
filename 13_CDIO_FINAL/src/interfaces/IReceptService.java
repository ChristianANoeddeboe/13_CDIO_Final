package interfaces;

import javax.ws.rs.core.Response;

import dto.DTORecept;
import dto.DTOReceptKomp;
import exception.DALException;

public interface IReceptService {
	//Recept
	Response getReceptList() throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	Response createRecept(DTORecept recept) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	Response updateRecept(DTORecept recept) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	Response deleteRecept(int input_id) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	//Recept komponent
	Response getReceptKompList(int receptId) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	Response createReceptKomp(DTOReceptKomp receptkomponent) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	Response updateReceptKomp(DTOReceptKomp receptkomponent) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	Response deleteReceptKomp(int recept_id, int raavare_id) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;
}
