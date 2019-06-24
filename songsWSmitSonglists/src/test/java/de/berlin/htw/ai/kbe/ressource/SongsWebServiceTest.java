package de.berlin.htw.ai.kbe.ressource;

import de.berlin.htw.ai.kbe.DBSongsDAO;
import de.berlin.htw.ai.kbe.entities.Song;
import de.berlin.htw.ai.kbe.interfaces.SongsDAO;
import de.berlin.htw.ai.kbe.resource.SongsWebService;
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
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class SongsWebServiceTest extends JerseyTest {




    @Mock
    private SongsDAO DBSongsDAO;

    @Rule   
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Override
    protected Application configure() {
        return new ResourceConfig(SongsWebService.class).register(
                new AbstractBinder() {
                    @Override
                    protected void configure() {

                        bind(Persistence.createEntityManagerFactory("Song-TEST-PU")).to(EntityManagerFactory.class);
                        bind(de.berlin.htw.ai.kbe.DBSongsDAO.class).to(SongsDAO.class).in(Singleton.class);
                    }
                });
    }



    /*

    GET Methoden Testen

     */

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

    /*

    PUT Methoden Testen

     */



    @Test
    public void updateSongJSONWithWrongIdShouldReturnBadRequest() {
        String newTitle = "new Title";
        Response response;
        Song song = new Song();
        song.setTitle(newTitle);
        Entity<Song> ent = Entity.entity(song, MediaType.APPLICATION_JSON);
        response = target("/songs/50000" ).request().put(ent);
        Assert.assertEquals(400, response.getStatus());
    }


    @Test
    public void updateSongXMLWithWrongIdShouldReturnBadRequest() {
        String newTitle = "new Title";
        Response response;
        Song song = new Song();
        song.setTitle(newTitle);
        Entity<Song> ent = Entity.entity(song, MediaType.APPLICATION_XML);
        response = target("/songs/50000" ).request().put(ent);
        Assert.assertEquals(400, response.getStatus());
    }


    @Test
    public void updateSongJSONWithValidIdShouldReturn204AndUpdatedSong() {

        Song song = new Song.Builder("16 lines").id(1).artist("BooHoo").album("boo ?").released(2016).build();
        Response response;
        response = target("/songs/1").request().put(Entity.json(song));
        Assert.assertEquals(204, response.getStatus());
    }



    @Test
    public void updateSongXMLWithValidIdShouldReturn204AndUpdatedSong() {

        Song song = new Song.Builder("Beamerboy").id(1).artist("BooHoo").album("boo ?").released(2016).build();
        Response response;
        response = target("/songs/1").request().put(Entity.xml(song));
        Assert.assertEquals(204, response.getStatus());
    }




}



