package de.berlin.htw.ai.kbe.storage;

import de.berlin.htw.ai.kbe.entities.Song;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertNotNull;


public class SongResourceTest extends JerseyTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static Song songTest;

    private static SongResource songResource;

    @Override
    protected Application configure() {
        return new ResourceConfig(SongResource.class);
    }


    @BeforeClass
    public static void init() {
        songResource = new SongResource();
        songResource.em = em;
        emf = Persistence.createEntityManagerFactory("song-test");
        em = emf.createEntityManager();
        //songTestObjekt mit Werte init.
        songTest = new Song();

        songTest.setAlbum("Bloom");
        songTest.setArtist("Camila Cabello, Machine Gun Kelly");
        songTest.setReleased(2017);
        songTest.setTitle("Bad Things");


    }

    @Test
    public void testFindSongWhenValidIdShouldReturnAccepted() {
        Response song5Response = songResource.getSingleRowRecord(5);
        System.out.println(song5Response);
        Assert.assertEquals(song5Response.getStatus(), 200);
    }

    @Test
    public void testFindSongWhenUnvalidIdShouldReturnNotFound() {
        Response song5Response = songResource.getSingleRowRecord(50);
        System.out.println(song5Response);
        Assert.assertEquals(song5Response.getStatus(), 404);
    }

    @Test
    public void testFindAllSongShouldReturnNotNull() {
        List<Song> songList = songResource.getAllRecords();
        for(Song song:songList) {
            System.out.println(song);
        }
        assertNotNull(songList);
    }

    @Test
    public void testPutSongWhenSameIdShouldReturnNoContent() {
        songTest.setSongId(5);
        Response response = songResource.updateRecord(5, songTest);
        Assert.assertEquals(Response.Status.NO_CONTENT, response.getStatusInfo());
    }

    @Test
    public void testPutSongWhenNotSameIdShouldReturnBadRequest() {
        songTest.setSongId(10);
        Response response = songResource.updateRecord(5, songTest);
        Assert.assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test
    public void testPutSongWhenNonExistanceIdShouldReturnBadRequest() {
        songTest.setSongId(5);
        Response response = songResource.updateRecord(200, songTest);
        Assert.assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }


}



