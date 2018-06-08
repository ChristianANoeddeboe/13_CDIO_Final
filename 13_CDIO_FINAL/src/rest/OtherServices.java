package rest;

import connector.MySQLConnector;
import dto.DTOProduktBatch;
import dto.Status;
import exception.DALException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("other")
@Produces(MediaType.APPLICATION_JSON)

public class OtherServices {


    @GET
    @Path("statuss")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getOperatoer() throws DALException {
        Object[] arr = Status.values();
        String result = "";
        for(Object object : arr){
            result += object.toString() + " ";
        }
        String[] strArr = result.split(" ");

        String json = "[";
        for(int i = 0; i < strArr.length-1; i++){
            json += "{\"status" + i + "\":\"" + strArr[i] +"\"},";
        }
        json += "{\"status" + (strArr.length-1) + "\":\"" + strArr[strArr.length-1] +"\"}]";

//        return Response.ok(json).build();
        return Response.ok(arr, MediaType.APPLICATION_JSON).build();
    }
}
