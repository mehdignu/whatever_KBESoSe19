package de.berlin.htw.ai.kbe.storage;

import de.berlin.htw.ai.kbe.authentication.AuthenticationFilter;
import de.berlin.htw.ai.kbe.entities.Song;
import de.berlin.htw.ai.kbe.interfaces.SongsDAO;
import de.berlin.htw.ai.kbe.interfaces.UsersDAO;
import de.berlin.htw.ai.kbe.resource.SongsWebService;
import de.berlin.htw.ai.kbe.resource.UserWebService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class SongsWebServiceTest extends JerseyTest {



//    @Override
//    protected Application configure() {
//
//
//
//        return new ResourceConfig(SongsWebService.class, AuthenticationFilter.class, UserWebService.class).register(new AbstractBinder()
//
//        {
//            @Override
//            protected void configure() {
//
//               DBSongsDAO.    getInstance().inject(Persistence.createEntityManagerFactory("Song-TEST-PU"));
//               DBUserDAO.getInstance().inject(Persistence.createEntityManagerFactory("Song-TEST-PU"));
//
//               bind(DBSongsDAO.class).to(SongsDAO.class).in(Singleton.class);
//               bind(DBUserDAO.class).to(UsersDAO.class).in(Singleton.class);
//            }
//        });
//
//    }


    @Mock
    private SongsDAO songsDAO;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Override
    protected Application configure() {
        return new ResourceConfig(SongsWebService.class).register(
                new AbstractBinder() {
                    @Override
                    protected void configure() {

                        bind(Persistence.createEntityManagerFactory("Song-TEST-PU")).to(EntityManagerFactory.class);
                        bind(DBSongsDAO.class).to(SongsDAO.class).in(Singleton.class);
                    }
                });
    }




    @Test
    public void getSongWithValidIdShouldReturnSong() {

        Song song = target("/songs/1").request(MediaType.APPLICATION_XML).get(Song.class);
        Assert.assertEquals(1, song.getId().intValue());
    }


    @Test
    public void getSongWithWrongIdShouldReturnNotFound() {

        Response res = target("/songs/1000").request().get();
        Assert.assertEquals(404, res.getStatus());
    }


    @Test
    public void getSongWithStupidIdShouldReturnNotFound() {

        Response res = target("/songs/1sd4").request().get();
        Assert.assertEquals(404, res.getStatus());
    }

//    @Test
//    public void putSongJsonMitRichtigemId() {
//
//        int id = 1;
//        String newTitle = "new Title";
//        Response response;
//        Song song = new Song();
//        song.setTitle(newTitle);
//        Entity<Song> ent = Entity.entity(song, MediaType.APPLICATION_JSON);
//        response = target("/songs/" + id).request().put(ent);
//        assertTrue(response.equals(DBSongsDAO.getInstance().getSingleRowRecord(id).getStatusInfo()));
//
//    }
//
//
//    @Ignore
//    public void testPutSongWhenNotSameIdShouldReturnBadRequest() {
//        int id = 10;
//        Response response;
//        Song songTest = new Song();
//        songTest.setId(5);
//        Entity<Song> ent = Entity.entity(songTest, MediaType.APPLICATION_JSON);
//        response = target("/songs/" + id).request().header("Authorization",TokenZumUnitTest.Token_Test).put(ent);
//        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
//    }
//
//    @Ignore
//    public void testPutSongWhenNonExistanceIdShouldReturnBadRequest() {
//        songTest.setSongId(5);
//        Response response = songResource.updateRecord(200, songTest);
//        Assert.assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
//    }


}



