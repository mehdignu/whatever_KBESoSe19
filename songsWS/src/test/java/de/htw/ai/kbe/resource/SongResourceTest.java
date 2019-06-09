package de.htw.ai.kbe.resource;

import de.htw.ai.kbe.entities.Song;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.testng.Assert.assertNotNull;


public class SongResourceTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static Song songTest;

    private SongResource songResource;

    @BeforeClass
    public static void init() {
        // using an H2 in-memory database to simulate a real database
        emf = Persistence.createEntityManagerFactory("song-test");
        em = emf.createEntityManager();


        //songTestObjekt mit Werte init.
        songTest = new Song();

        songTest.setAlbum("Bloom");
        songTest.setArtist("Camila Cabello, Machine Gun Kelly");
        songTest.setReleased(2017);
        songTest.setTitle("Bad Things");


    }

    @BeforeTest
    public void initializeDatabase() {
        songResource = new SongResource();
        songResource.em = em;
    }

    @AfterClass
    public void tearDown() {
        em.clear();
        em.close();
        emf.close();
    }


    @Test
    public void findSongByID() {
        Response song5Response = songResource.getSingleRowRecord(5);
        System.out.println(song5Response);
        Assert.assertEquals(song5Response.getStatus(), 200);
    }

    @Test
    public void findAllSong() {
        List<Song> songList = songResource.getAllRecords();
        for(Song song:songList) {
            System.out.println(song);
        }
        assertNotNull(songList);
    }

    @Test
    public void putSongWithSameId() {
        songTest.setSongId(5);
        Response response = songResource.updateRecord(5, songTest);
        Assert.assertEquals(Response.Status.NO_CONTENT, response.getStatusInfo());
    }

    @Test
    public void putSongWithNotSameId() {
        songTest.setSongId(10);
        Response response = songResource.updateRecord(5, songTest);
        Assert.assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test
    public void putSongWithNonExistanceId() {
        songTest.setSongId(5);
        Response response = songResource.updateRecord(200, songTest);
        Assert.assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

}