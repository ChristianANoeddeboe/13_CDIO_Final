package interfaces;

import java.util.ArrayList;
import data.UserDTO;

public interface IUserDAO {
	public ArrayList<UserDTO> getUsers();
	public UserDTO getUser (int id);
	public UserDTO createUser(UserDTO user);
	public boolean deleteUser(UserDTO user);
	public UserDTO updateUser(UserDTO user);
}
