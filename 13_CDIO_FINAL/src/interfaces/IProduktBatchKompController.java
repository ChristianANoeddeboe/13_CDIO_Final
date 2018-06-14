package interfaces;

import dto.DTOProduktBatchKomp;
import exception.DALException;

import java.util.List;

public interface IProduktBatchKompController {

	DTOProduktBatchKomp getProduktBatchKomp(int pbId, int rbId)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	List<DTOProduktBatchKomp> getProduktBatchKomponentList(int pbId)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	List<DTOProduktBatchKomp> getProduktBatchKomponentList()
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	void createProdBatchKomp(DTOProduktBatchKomp prodBatchKomp)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	void updateProdBatchKomp(DTOProduktBatchKomp prodBatchKomp)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	void deleteProdBatchKomp(int pbId, int rbId)
			throws DALException, InstantiationException, IllegalAccessException, ClassNotFoundException;

}