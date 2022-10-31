package ru.ngs.summerjob.city.web;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import ru.ngs.summerjob.city.domain.PersonResponse;

@Path("/check")
public class CheckPersonService {
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PersonResponse checkPerson(@PathParam("id") int simpleId,
                                      @QueryParam("name") String simpleName) {
        return new PersonResponse();
    }
}
