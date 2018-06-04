package rest;

import java.util.List;

import dto.DTORecept;
import dto.DTOReceptKomp;
import exception.DALException;

public interface IReceptService {
	//Recept
	DTORecept getRecept(int receptId) throws DALException;
	List<DTORecept> getReceptList() throws DALException;
	void createRecept(DTORecept recept) throws DALException;
	void updateRecept(DTORecept recept) throws DALException;
	void deleteRecept(int input_id) throws DALException;
	//Recept komponent
	DTOReceptKomp getReceptKomp(int receptId, int raavareId) throws DALException;
	List<DTOReceptKomp> getReceptKompList(int receptId) throws DALException;
	List<DTOReceptKomp> getReceptKompList() throws DALException;
	void createReceptKomp(DTOReceptKomp receptkomponent) throws DALException;
	void updateReceptKomp(DTOReceptKomp receptkomponent) throws DALException;
	void deleteReceptKomp(int recept_id, int raavare_id) throws DALException;
}
