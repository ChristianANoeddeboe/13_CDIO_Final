package controller;

import dao.DAOReceptKomp;
import dto.DTOReceptKomp;
import exception.DALException;
import interfaces.IDAOReceptKomp;
import interfaces.IReceptKompController;

import java.util.List;
import java.util.logging.Logger;

public class ReceptKompController implements IReceptKompController {
    private static final Logger log = Logger.getLogger(ReceptKompController.class.getName());
    private static IDAOReceptKomp dao;
	private static IReceptKompController instance;
	private ReceptKompController() {
	}
	public static IReceptKompController getInstance() {
		if(instance == null) {
			dao = new DAOReceptKomp();
			instance =  new ReceptKompController();
			return instance;
		}
		return instance;
	}
	public DTOReceptKomp getReceptKomp(int receptID, int raavarID) throws  DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		return  dao.getReceptKomp(receptID, raavarID);
	}

	public List<DTOReceptKomp> getReceptKompList(int receptID) throws  DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		return  dao.getReceptKompList(receptID);
	}

	public  List<DTOReceptKomp> getReceptKompList() throws  DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		return  dao.getReceptKompList();
	}

	public void createReceptKomp(DTOReceptKomp receptKompDTO) throws  DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		validateData(receptKompDTO);
		dao.createReceptKomp(receptKompDTO);
	}

	public  void updateReceptKomp(DTOReceptKomp receptKompDTO) throws  DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		validateData(receptKompDTO);
		dao.updateReceptKomp(receptKompDTO);
	}

	public void deleteReceptKomp(int receptKompID, int raavareID) throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		dao.deleteReceptKomp(receptKompID, raavareID);
	}

	private void validateData(DTOReceptKomp receptkomponent) throws DALException {
		String errMsg;
		errMsg = ErrorChecking.checkIntSize(receptkomponent.getRaavareId());
		throwException(errMsg);
		errMsg = ErrorChecking.checkIntSize(receptkomponent.getReceptId());
		throwException(errMsg);
	}

	private void throwException(String errMsg) throws DALException {
		if(errMsg != null){
			log.severe(errMsg);
			throw new DALException(errMsg);
		}
	}
}
