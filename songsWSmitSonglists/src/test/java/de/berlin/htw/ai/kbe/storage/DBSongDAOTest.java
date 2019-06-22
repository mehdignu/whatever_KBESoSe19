package de.berlin.htw.ai.kbe.storage;

import de.berlin.htw.ai.kbe.entities.Song;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


import org.h2.tools.RunScript;

import static org.junit.Assert.assertNotNull;

/**
 *  test is SUCCESSFULL
 */

public class DBSongDAOTest extends JerseyTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    private static Song songTest;

    private static DBSongsDAO dbSongsDAO;

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(DBSongsDAO.class);
    }


    @BeforeClass
    public static void init() {

        emf = Persistence.createEntityManagerFactory("song-test");
        em = emf.createEntityManager();

        dbSongsDAO = new DBSongsDAO(emf);
        dbSongsDAO.em = em;

        // new dataset for every test case
        Session session = em.unwrap(Session.class);
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                try {
                    File script = new File(getClass().getResource("/sql/data.sql").getFile());
                    RunScript.execute(connection, new FileReader(script));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("could not initialize with script");
                }
            }
        });

        //songTestObjekt mit Werte init.
        songTest = new Song();

        songTest.setAlbum("Bloom");
        songTest.setArtist("Camila Cabello, Machine Gun Kelly");
        songTest.setReleased(2017);
        songTest.setTitle("Bad Things");

    }

    @After
    public void clearAfter() {
        if (em.isOpen()) {
            em.clear();
            em.close();
        }
    }


    @Test
    public void testFindSongWhenValidIdShouldReturnAccepted() {
        Response song5Response = dbSongsDAO.getSingleRowRecord(5);
        System.out.println(song5Response);
        Assert.assertEquals(song5Response.getStatus(), 200);
    }

    @Test
    public void testFindSongWhenUnvalidIdShouldReturnNotFound() {
        Response song5Response = dbSongsDAO.getSingleRowRecord(50);
        System.out.println(song5Response);
        Assert.assertEquals(song5Response.getStatus(), 404);
    }

    @Test
    public void testFindAllSongShouldReturnNotNull() {
        List<Song> songList = dbSongsDAO.getAllRecords();
        for(Song song:songList) {
            System.out.println(song);
        }
        assertNotNull(songList);
    }

    @Test
    public void testPutSongWhenSameIdShouldReturnNoContent() {
        songTest.setId(5);
        Response response = dbSongsDAO.updateRecord(5, songTest);
        Assert.assertEquals(Response.Status.NO_CONTENT, response.getStatusInfo());
    }

    @Test
    public void testPutSongWhenNotSameIdShouldReturnBadRequest() {
        songTest.setId(10);
        Response response = dbSongsDAO.updateRecord(5, songTest);
        Assert.assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test
    public void testPutSongWhenNonExistanceIdShouldReturnBadRequest() {
        songTest.setId(5);
        Response response = dbSongsDAO.updateRecord(200, songTest);
        Assert.assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }
}



