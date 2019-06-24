package de.berlin.htw.ai.kbe.storage;

import de.berlin.htw.ai.kbe.entities.Song;
import de.berlin.htw.ai.kbe.interfaces.SongsDAO;
import de.berlin.htw.ai.kbe.storage.DBSongsDAO;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Response;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class DBSongsDAOTest {


    protected static EntityManagerFactory emf;
    protected static EntityManager em;

    private SongsDAO songsDAO;

    @BeforeClass
    public static void init() {
        // using an H2 in-memory database to simulate a real database
        emf = Persistence.createEntityManagerFactory("Song-TEST-PU");
        em = emf.createEntityManager();
    }

    @Before
    public void initializeDatabase() {
        songsDAO = new DBSongsDAO(emf);


    }

    @After
    public void clearAfter() {
        em.clear();
    }

    @AfterClass
    public static void tearDown() {
        em.clear();
        em.close();
        emf.close();
    }


    @Test
    public void getAllRecordsShouldReturnAllSongs() {
        List<Song> songlist = songsDAO.getAllRecords();
        assertEquals(10, songlist.size());
    }

    @Test
    public void getSingleRecordWithFalseIdShouldReturnNotFound(){
        Response res = songsDAO.getSingleRowRecord(5511);
        assertEquals(404, res.getStatus());
    }

}
