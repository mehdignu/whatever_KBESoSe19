package de.htw.ai.kbe.resource;

import de.htw.ai.kbe.entities.User;
import de.htw.ai.kbe.resource.AuthenticationEndpoint;
import org.junit.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import static org.junit.Assert.assertNotNull;


public class AuthenticationEndpointTest extends JerseyTest {


    private static User userTest;


    @Override
    protected Application configure() {
        return new ResourceConfig(AuthenticationEndpoint.class);
    }


    @BeforeClass
    public static void init() {


        userTest = new User();

        userTest.setUserId("mmuster");
        userTest.setPassword("password");
        userTest.setFirstName("Maxime");
        userTest.setLastName("Muster");


    }



    @Test
    public void testAuthenticateUserWhenParametersAreEmptyShouldReturnForbidden() {
        Response response = target("/auth")
                .queryParam("userId", "")
                .queryParam("secret", "")
                .request()
                .get();
        Assert.assertEquals(403, response.getStatus());
    }


    @Test
    public void testAuthenticateUserWhenUserDoesntExistInTheDatabaseShouldReturnForbidden() {
        Response response = target("/auth")
                .queryParam("userId", "dede")
                .queryParam("secret", "dede")
                .request()
                .get();
        Assert.assertEquals(403, response.getStatus());
    }


    @Test
    public void testAuthenticateUserWhenIdIsWrongShouldReturnForbidden() {
        Response response = target("/auth")
                .queryParam("userId", "mmusterdede")
                .queryParam("secret", "passwd123")
                .request()
                .get();
        Assert.assertEquals(403, response.getStatus());

    }

    @Test
    public void testAuthenticateUserWhenPasswordIsWrongShouldReturnForbidden() {
        Response response = target("/auth")
                .queryParam("userId", "mmuster")
                .queryParam("secret", "passwd123123")
                .request()
                .get();
        Assert.assertEquals(403, response.getStatus());
    }

    @Test
    public void testAuthenticateUserWhenUserExistShouldReturnAccepted() {
        Response response = target("/auth")
                .queryParam("userId", "mmuster")
                .queryParam("secret", "passwd123")
                .request()
                .get();

        String token = response.readEntity(String.class);
        assertNotNull(token);
        System.out.println(token);

        Assert.assertEquals(200, response.getStatus());
    }

}

