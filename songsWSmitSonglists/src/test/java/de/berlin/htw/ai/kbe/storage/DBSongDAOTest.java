package de.berlin.htw.ai.kbe.storage;

import de.berlin.htw.ai.kbe.entities.Song;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 *  test is SUCCESSFULL
 */

public class DBSongDAOTest {

    private static EntityManagerFactory emf = Persistence
            .createEntityManagerFactory("song-test");

    private static Song songTest;


    @Test
    public void testFindSongWhenValidIdShouldReturnAccepted() {
        DBSongsDAO dbSongsDAO = new DBSongsDAO(emf);
        Response song5Response = dbSongsDAO.getSingleRowRecord(5);
        System.out.println(song5Response);
        Assert.assertEquals(song5Response.getStatus(), 200);
    }

    @Test
    public void testFindSongWhenUnvalidIdShouldReturnNotFound() {
        DBSongsDAO dbSongsDAO = new DBSongsDAO(emf);
        Response song5Response = dbSongsDAO.getSingleRowRecord(50);
        System.out.println(song5Response);
        Assert.assertEquals(song5Response.getStatus(), 404);
    }

    @Test
    public void testFindAllSongShouldReturnNotNull() {
        DBSongsDAO dbSongsDAO = new DBSongsDAO(emf);
        List<Song> songList = dbSongsDAO.getAllRecords();
        for(Song song:songList) {
            System.out.println(song);
        }
        assertNotNull(songList);
    }

    @Test
    public void testPutSongWhenSameIdShouldReturnNoContent() {
        DBSongsDAO dbSongsDAO = new DBSongsDAO(emf);
        songTest.setId(5);
        Response response = dbSongsDAO.updateRecord(5, songTest);
        Assert.assertEquals(Response.Status.NO_CONTENT, response.getStatusInfo());
    }

    @Test
    public void testPutSongWhenNotSameIdShouldReturnBadRequest() {
        DBSongsDAO dbSongsDAO = new DBSongsDAO(emf);
        songTest.setId(10);
        Response response = dbSongsDAO.updateRecord(5, songTest);
        Assert.assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test
    public void testPutSongWhenNonExistanceIdShouldReturnBadRequest() {
        DBSongsDAO dbSongsDAO = new DBSongsDAO(emf);
        songTest.setId(5);
        Response response = dbSongsDAO.updateRecord(200, songTest);
        Assert.assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }


}



