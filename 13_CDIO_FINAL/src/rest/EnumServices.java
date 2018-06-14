package rest;

import dto.Aktiv;
import dto.Roller;
import dto.Status;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("enum")
@Produces(MediaType.APPLICATION_JSON)

public class EnumServices {


    @GET
    @Path("status_produktbatch")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getStatusProduktBatch() {
        Status[] arr = Status.values();

        return Response.ok(arr, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("status_operatoer")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getStatusOperatoer() {
        Aktiv[] arr = Aktiv.values();

        return Response.ok(arr, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("roller")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getRoller() {
        Roller[] arr = Roller.values();

        return Response.ok(arr, MediaType.APPLICATION_JSON).build();
    }
}
