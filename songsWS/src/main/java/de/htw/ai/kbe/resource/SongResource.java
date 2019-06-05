package de.htw.ai.kbe.resource;

import de.htw.ai.kbe.entities.Song;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Root resource (exposed at "songs" path)
 */
@Path("/songs")
public class SongResource extends ManageResource<Song> {

    private EntityManager em;
    private List<Song> songsList;
    private Song song;
    private Response response;

    //get the absolute path
    @Context
    UriInfo uriInfo;

    /**
     * @return
     * @throws NamingException
     */
    @Override
    protected List<Song> getListOfRecords() {
        System.out.println("getAllSong: Returning all songs!");
        em = getEntityManager();
        em.getTransaction().begin();
        songsList = em.createQuery("SELECT e FROM Song e").getResultList();
        em.getTransaction().commit();
        em.close();
        return songsList;
    }

    /**
     * @param id
     * @return
     * @throws NamingException
     */
    @Override
    protected Response getSingleRecord(Integer id) {
        song = new Song();
        em = getEntityManager();
        em.getTransaction().begin();
        song = em.find(Song.class, id);
        if (song != null) {    //wie ist die richtige Bedingung?
            System.out.println("getSong: Returning song for id " + id);
            response = Response.status(Response.Status.OK).entity(song).type(MediaType.APPLICATION_XML).build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND).entity("ID not found").build();
        }
        em.getTransaction().commit();
        em.close();
        return response;
    }

    /**
     * @param t
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    @Override
    protected Response createSingleRecord(Song t) {
        song = new Song();
        try {
            System.out.println("Trying to post/insert new Song in DB");
            em = getEntityManager();
            em.getTransaction().begin();
            setRecordDetails(t);
            em.persist(song);
            em.getTransaction().commit();
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            uriBuilder.path(Integer.toString(song.getSongId()));
            response = Response.created(uriBuilder.build()).build();
        } catch (Exception e) {
            System.out.println(e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

        return response;
    }

    /**
     * @param id
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    @Override
    protected Response deleteSingleRecord(Integer id) {
        song = new Song();
        em = getEntityManager();
        em.getTransaction().begin();
        song = em.find(Song.class, id);
        if (song != null) {
            System.out.println("deleteSong: deleting song for id " + id);
            em.remove(song);
            response = Response.status(Response.Status.NO_CONTENT).entity("").build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND).entity("ID not found").build();
        }
        em.getTransaction().commit();
        em.close();
        return response;
    }

    /**
     * @param id
     * @param t
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    @Override
    protected Response updateSingleRecord(Integer id, Song t) {
        song = new Song();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            song = em.find(Song.class, id);
            System.out.println("This is the payload songId" + t.getSongId());
            System.out.println("This is the payload title" + t.getTitle());
            System.out.println("This is the payload artist" + t.getArtist());
            System.out.println("This is the payload album " + t.getAlbum());
            System.out.println("This is the payload released date " + t.getReleased());
            System.out.println("========================================================");
            System.out.println("This is the DB songId" + song.getSongId());
            System.out.println("This is the DB title" + song.getTitle());
            System.out.println("This is the DB artist" + song.getArtist());
            System.out.println("This is the DB album " + song.getAlbum());
            System.out.println("This is the DB released date " + song.getReleased());

            if (song != null) {
                if (id == t.getSongId()) {
                    System.out.println("updateSong: updating song information for id " + id);
                    setRecordDetails(t);
                    em.merge(song);
                    em.getTransaction().commit();
                    response = Response.status(Response.Status.NO_CONTENT).entity("").build();
                } else {
                    System.out.println("A song has been found, but the given Id in payload does not match");
                    response = Response.status(Response.Status.BAD_REQUEST).entity("").build();
                }
            } else {
                System.out.println("No song could be found with the given Id");
                response = Response.status(Response.Status.BAD_REQUEST).entity("").build();
            }
        } catch (Exception e) {
            System.out.println("There is a problem as follow in Method Update: \n" + e);
            em.getTransaction().rollback();

        }
        em.close();
        return response;
    }

    /**
     * template Method -- um doppelte Code zu vermeiden
     *
     * @param t
     */
    private void setRecordDetails(Song t) {
        song.setTitle(t.getTitle());
        song.setArtist(t.getArtist());
        song.setAlbum(t.getAlbum());
        song.setReleased(t.getReleased());
    }
}
