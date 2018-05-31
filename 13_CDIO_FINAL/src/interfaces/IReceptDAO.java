package interfaces;

import exception.DALException;

public interface IReceptDAO {
    ReceptDTO getRecept(int receptId) throws DALException;
    List<ReceptDTO> getReceptList() throws DALException;
    void createRecept(ReceptDTO recept) throws DALException;
    void updateRecept(ReceptDTO recept) throws DALException;
}

