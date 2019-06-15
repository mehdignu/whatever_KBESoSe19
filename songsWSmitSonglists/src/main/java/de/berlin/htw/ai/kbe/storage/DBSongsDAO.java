package de.berlin.htw.ai.kbe.storage;

import de.berlin.htw.ai.kbe.entities.Song;
import de.berlin.htw.ai.kbe.interfaces.SongsDAO;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.*;
import java.util.List;

@Singleton
public class DBSongsDAO implements SongsDAO {

    //get the absolute path
    @Context
    private UriInfo uriInfo;
    private List<Song> songsList;
    private Song song;
    private Response response;

    private EntityManager em;

    private EntityManagerFactory emf;

    @Inject
    public DBSongsDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Song> getAllRecords() {
        System.out.println("getAllSong: Returning all songs!");
        em = emf.createEntityManager();
        em.getTransaction().begin();
        songsList = em.createQuery("SELECT song FROM Song song", Song.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return songsList;
    }

    @Override
    public Response getSingleRowRecord(Integer id) {
        song = new Song();
        em = emf.createEntityManager();
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

    @Override
    public Response createRecord(Song t) {
        song = new Song();
        try {
            System.out.println("Trying to post/insert new Song in DB");
            em = emf.createEntityManager();
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

    @Override
    public Response updateRecord(Integer id, Song t) {
        song = new Song();
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            song = em.find(Song.class, id);

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

    @Override
    public Response deleteRecord(Integer id) {
        song = new Song();
        em = emf.createEntityManager();
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
