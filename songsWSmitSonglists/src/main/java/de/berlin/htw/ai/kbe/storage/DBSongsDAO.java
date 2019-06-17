package de.berlin.htw.ai.kbe.storage;

import de.berlin.htw.ai.kbe.entities.Song;
import de.berlin.htw.ai.kbe.interfaces.SongsDAO;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
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

    @PersistenceContext
    protected EntityManager em;


    private static EntityManagerFactory emf;

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Inject
    public DBSongsDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Song> getAllRecords() {
        System.out.println("getAllSong: Returning all songs!");
        em = getEntityManager();
        em.getTransaction().begin();
        try {
            songsList = em.createQuery("SELECT song FROM Song song").getResultList();
            em.getTransaction().commit();
            return songsList;
        } finally {
            em.close();
        }
    }

    @Override
    public Response getSingleRowRecord(Integer id) {
        song = new Song();
        em = getEntityManager();
        em.getTransaction().begin();
        try {
            song = em.find(Song.class, id);
            if (song != null) {
                System.out.println("getSong: Returning song for id " + id);
                response = Response.status(Response.Status.OK).entity(song).type(MediaType.APPLICATION_XML).build();
            } else {
                response = Response.status(Response.Status.NOT_FOUND).entity("ID not found").build();
            }
        } finally {
            em.getTransaction().commit();
            em.close();
            return response;
        }
    }

    @Override
    public Response createRecord(Song t) {
        response = null;
        song = new Song();
        System.out.println("Trying to post/insert new Song in DB");
        em = getEntityManager();
        em.getTransaction().begin();
        try {
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
            return response;
        }

    }

    @Override
    public Response updateRecord(Integer id, Song t) {
        song = new Song();
        em = getEntityManager();
        em.getTransaction().begin();

        try {
            song = em.find(Song.class, id);
            if (song != null) {
                if (id == t.getSongId()) {
                    System.out.println("updateSong: updating song information for id " + id);
                    setRecordDetails(t);
                    em.merge(song);
                    em.getTransaction().commit();
                    response = Response.status(Response.Status.NO_CONTENT).entity("").build();
                } else {
                    System.out.println("A song has been found, but the given Id in payload does not match or Id is missing");
                    response = Response.status(Response.Status.BAD_REQUEST).entity("").build();
                }
            } else {
                System.out.println("No song could be found with the given Id");
                response = Response.status(Response.Status.BAD_REQUEST).entity("").build();
            }

        } finally {
            em.close();
            return response;
        }
    }

    @Override
    public Response deleteRecord(Integer id) {
        response = Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("").build();
        //return new GenericExceptionMapper().toResponse(new NotAllowedException(""));
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
