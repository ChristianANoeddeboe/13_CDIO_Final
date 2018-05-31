package interfaces;

import java.util.ArrayList;
import data.UserDTO;
import exception.DALException;

public interface IBrugerDAO {
    BrugerDTO getBruger(int oprId) throws DALException;
    List<BrugerDTO> getBrugerList() throws DALException;
    void createBruger(BrugerDTO opr) throws DALException;
    void updateBruger(BrugerDTO opr) throws DALException;
}

