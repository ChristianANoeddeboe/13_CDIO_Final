//package rest;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response.Status;
//
//import data.UserDAO;
//import data.UserDTO;
//@Path("operator")
//
//
//public class OperatorService {
//	static UserDAO dao = new UserDAO();	
//	@POST
//	@Path("add")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response addOperatoer(UserDTO input) {
//		if(input.getUserName().length() < 1|| input.getCpr().length() < 1 ||input.getPassword().length() < 1 || input.getPassword().length() < 1 || input.getRoles().length() < 1) {
//			return Response.status(Status.NOT_ACCEPTABLE).entity("No empty fields are allowed").build();
//		}else {
//			input.setUserName(trimmer(input.getUserName()));
//			input.setIni(initials(input.getUserName()));
//			UserDAO.setCounter(UserDAO.getCounter()+1);
//			input.setUserId(UserDAO.getCounter());
//			dao.createUser(input);
//			return Response.status(Status.OK).entity("User created successfully").build();
//		}
//	}
//
//	@GET
//	@Path("display")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String displayOperator() {
//		String temp2 = "[";
//		int counter = 0;
//		for (UserDTO userDTO : dao.getUsers()) {
//			temp2 += userDTO.toString();
//			counter++;
//			if(dao.getUsers().size() > 1 && counter < dao.getUsers().size()) {
//				temp2 += ",";
//			}
//		}
//		temp2 += "]";
//		return temp2;
//	}
//
//	@PUT
//	@Path("update")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response updateIngredient(UserDTO input) {
//		for (UserDTO userDTO : dao.getUsers()) {
//			if(input.getUserId() == userDTO.getUserId()) {
//				userDTO.setUserName(trimmer(input.getUserName()));
//				userDTO.setCpr(input.getCpr());
//				userDTO.setIni(initials(input.getUserName()));
//				userDTO.setPassword(input.getPassword());
//				userDTO.setRoles(input.getRoles());
//				return Response.status(Status.OK).entity("Updated user").build();
//			}
//		}
//		return Response.status(Status.NOT_FOUND).entity("ID not found").build();
//	}
//
//	@DELETE
//	@Path("delete")
//	public Response deleteOperator(String str) {
//		//Return response instead
//		for (UserDTO userDTO : dao.getUsers()) {
//			if(userDTO.getUserId() == Integer.parseInt(str)) {
//				dao.deleteUser(userDTO);
//				return Response.status(Status.OK).entity("Deleted user").build(); //We found a matching id
//			}
//		}
//		//We did not find a matching id
//		return Response.status(Status.NOT_FOUND).entity("ID not found").build();
//	}
//
//
//	
//
//}
