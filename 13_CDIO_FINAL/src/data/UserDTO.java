//package data;
//
//import java.util.ArrayList;
//
//public class UserDTO {
//	private int userId;
//	private String userName;
//	private String ini;
//	private String cpr;
//	private String password;
//	//private ArrayList<String> roles = new ArrayList<String>();
//	private String roles;
//	public UserDTO() {
//		
//	};
//	
//	public UserDTO(String userName, String cpr, String password, String roles) {
//		this.userName = userName;
//		this.cpr = cpr;
//		this.password = password;
//	};
//	
//	public UserDTO(int userId, String userName, String ini, String cpr, String password, String roles) {
//		this.userId = userId;
//		this.userName = userName;
//		this.ini = ini;
//		this.cpr = cpr;
//		this.password = password;
//		this.roles = roles;
////		for(String str : roles) {
////			this.roles.add(str);
////		}
//	}
//	
//	public void setUserId(int userId) {
//		this.userId = userId;
//	}
//	
//	public int getUserId() {
//		return userId;
//	}
//	
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
//	
//	public String getUserName() {
//		return userName;
//	}
//	
//	public void setIni(String ini) {
//		this.ini = ini;
//	}
//	
//	public String getIni() {
//		return ini;
//	}
//	
//	public void setCpr(String cpr) {
//		this.cpr = cpr;
//	}
//	
//	public String getCpr() {
//		return cpr;
//	}
//	
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	
//	public String getPassword() {
//		return password;
//	}
//	public String getRoles() {
//		return roles;
//	}
//	public void setRoles(String roles) {
//		this.roles = roles;
//	}
//	
//	@Override
//	public String toString() {
//		// TODO Auto-generated method stub
//		return "{\"userId\":"+"\""+this.getUserId()+"\",\"userName\": \"" + this.getUserName() + "\", \"ini\": \""+this.getIni() + "\", \"cpr\": \""+this.getCpr()+ "\", \"password\": \"" + this.getPassword() + "\", \"roles\":\""+this.getRoles()+"\"}";
//	}
//	
////	public void setRoles(ArrayList<String> roles) {
////		this.roles = roles;
////	}
////	
////	public ArrayList<String> getRoles() {
////		return roles;
////	}
//}