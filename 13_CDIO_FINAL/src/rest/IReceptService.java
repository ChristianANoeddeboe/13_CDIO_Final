package rest;

import java.util.List;

import javax.ws.rs.core.Response;

import dto.DTORecept;
import dto.DTOReceptKomp;
import exception.DALException;

public interface IReceptService {
	//Recept
	Response getRecept(int receptId) throws DALException;
	Response getReceptList() throws DALException;
	Response createRecept(DTORecept recept) throws DALException;
	Response updateRecept(DTORecept recept) throws DALException;
	Response deleteRecept(int input_id) throws DALException;
	//Recept komponent
	Response getReceptKomp(int receptId, int raavareId) throws DALException;
	Response getReceptKompList(int receptId) throws DALException;
	Response getReceptKompList() throws DALException;
	Response createReceptKomp(DTOReceptKomp receptkomponent) throws DALException;
	Response updateReceptKomp(DTOReceptKomp receptkomponent) throws DALException;
	Response deleteReceptKomp(int recept_id, int raavare_id) throws DALException;
}
