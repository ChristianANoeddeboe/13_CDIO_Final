//package data;
//
//import java.util.ArrayList;
//
//import interfaces.OperatoerDAO;
//
//public class UserDAO implements OperatoerDAO {
//	static ArrayList<UserDTO> users = new ArrayList<UserDTO>();
//	static int counter = 0;
//	static {
//		users.add(new UserDTO(1,"test","testini","CPR","temppassword","Administrator"));
//		users.add(new UserDTO(2,"test","testini2","CPR2","temppassword2","Administrator2"));
//		users.add(new UserDTO(3,"test2","testini3","CPR3","temppassword3","Administrator3"));
//		counter = 3;
//	}
//	
//	public UserDAO() {
//		// TODO Auto-generated constructor stub
//	}
//	@Override
//	public ArrayList<UserDTO> getUsers() {
//		return users;
//	}
//
//	@Override
//	public UserDTO getUser(int id) {
//		return users.get(id);
//	}
//
//	@Override
//	public UserDTO createUser (UserDTO user) {
//		users.add(user);
//		return user;
//	}
//
//	@Override
//	public boolean deleteUser(UserDTO user) {
//		users.remove(user);
//		return true;
//	}
//
//	@Override
//	public UserDTO updateUser(UserDTO user) {
//		UserDTO temp = users.get(user.getUserId());
//		temp.setUserId(user.getUserId());
//		temp.setUserName(user.getUserName());
//		temp.setIni(user.getIni());
//		temp.setCpr(user.getCpr());
//		temp.setPassword(user.getPassword());
//		temp.setRoles(user.getRoles());
//		return users.get(user.getUserId());
//	}
//	@Override
//	public String toString() {
//		// TODO Auto-generated method stub
//		return super.toString();
//	}
//	
//	public static int getCounter() {
//		return counter;
//	}
//	public static void setCounter(int counter) {
//		UserDAO.counter = counter;
//	}
//}
