package rest;

import dto.Aktiv;
import dto.Roller;
import dto.Status;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("other")
@Produces(MediaType.APPLICATION_JSON)

public class OtherServices {


    @GET
    @Path("status_produktbatch")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getStatusProduktBatch() {
        Object[] arr = Status.values();

        return Response.ok(arr, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("status_operatoer")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getStatusOperatoer() {
        Object[] arr = Aktiv.values();

        return Response.ok(arr, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("roller")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getRoller() {
        Object[] arr = Roller.values();

        return Response.ok(arr, MediaType.APPLICATION_JSON).build();
    }
}
