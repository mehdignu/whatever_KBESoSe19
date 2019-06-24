package de.berlin.htw.ai.kbe.ressource;

import de.berlin.htw.ai.kbe.DBUserDAO;
import de.berlin.htw.ai.kbe.interfaces.SongsDAO;
import de.berlin.htw.ai.kbe.interfaces.UsersDAO;
import de.berlin.htw.ai.kbe.resource.SongsWebService;
import de.berlin.htw.ai.kbe.resource.UserWebService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertNotNull;

public class UserWebserviceTest extends JerseyTest {


    @Mock
    private UsersDAO DBUserDAO;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Override
    protected Application configure() {
        return new ResourceConfig(UserWebService.class).register(
                new AbstractBinder() {
                    @Override
                    protected void configure() {

                        bind(Persistence.createEntityManagerFactory("Song-TEST-PU")).to(EntityManagerFactory.class);
                        bind(de.berlin.htw.ai.kbe.DBUserDAO.class).to(UsersDAO.class).in(Singleton.class);
                    }
                });
    }


    @Test
    public void testAuthenticateUserWhenParametersAreEmptyShouldReturnForbidden() {
        Response res = target("/auth").queryParam("userId", "")
                .queryParam("secret", "").request().get();
        Assert.assertEquals(403, res.getStatus());
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
                .queryParam("secret", "321dwssap")
                .request()
                .get();

        String token = response.readEntity(String.class);
        assertNotNull(token);
        System.out.println(token);

        Assert.assertEquals(200, response.getStatus());
    }

}
