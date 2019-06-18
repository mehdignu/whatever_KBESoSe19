package de.berlin.htw.ai.kbe.resource;

import de.berlin.htw.ai.kbe.interfaces.UsersDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/auth")
public class UserWebService {

    private UsersDAO usersDAO;

    @Inject
    public UserWebService(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@QueryParam("userId") String userID, @QueryParam("secret") String password) {
        return usersDAO.authenticateUser(userID, password);
    }
}
