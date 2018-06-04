package rest;

import java.util.List;

import javax.ws.rs.core.Response;

import dto.DTORecept;
import dto.DTOReceptKomp;
import exception.DALException;

public interface IReceptService {
	//Recept
	DTORecept getRecept(int receptId) throws DALException;
	List<DTORecept> getReceptList() throws DALException;
	Response createRecept(DTORecept recept) throws DALException;
	Response updateRecept(DTORecept recept) throws DALException;
	Response deleteRecept(int input_id) throws DALException;
	//Recept komponent
	DTOReceptKomp getReceptKomp(int receptId, int raavareId) throws DALException;
	List<DTOReceptKomp> getReceptKompList(int receptId) throws DALException;
	List<DTOReceptKomp> getReceptKompList() throws DALException;
	Response createReceptKomp(DTOReceptKomp receptkomponent) throws DALException;
	Response updateReceptKomp(DTOReceptKomp receptkomponent) throws DALException;
	Response deleteReceptKomp(int recept_id, int raavare_id) throws DALException;
}
