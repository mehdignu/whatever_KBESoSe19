package de.berlin.htw.ai.kbe.resource;

import de.berlin.htw.ai.kbe.entities.Song;
import de.berlin.htw.ai.kbe.interfaces.SongsDAO;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

//import de.htw.ai.kbe.PermissionController.Secured;

/**
 * Root resource (exposed at "songs" path)
 */
//@Secured
@Path("/songs")
public class SongsWebService {


    //get the absolute path
    @Context
    private UriInfo uriInfo;
    private List<Song> songsList;
    private Song song;
    private Response response;

    private EntityManagerFactory emf;
    private EntityManager em;

    private SongsDAO sDAO;

    @Inject
    public SongsWebService(SongsDAO sDAO) {
        this.sDAO = sDAO;
    }


    /**
     * Bsp:      GET http://localhost:8080/songsWSmi/rest/songs
     *
     * @return json
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Song> getAllRecords() {
        System.out.println("getAllSong: Returning all songs!");
        em = emf.createEntityManager();
        em.getTransaction().begin();
        songsList = em.createQuery("SELECT e FROM Song e").getResultList();
        em.getTransaction().commit();
        em.close();
        return songsList;
    }

    /**
     * Bsp:      GET http://localhost:8080/songsWS/rest/songs/1
     * Returns:  200 & contact with id 1 or 404 when id not found
     *
     * @param id eingegeben in addressbar
     * @return xml
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Response getSingleRowRecord(@PathParam("id") Integer id) {
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

    /**
     * POST http://localhost:8080/songsWS/rest/songs with song in payload
     *
     * @param t Content-Type:application/xml (mit einer XML-Payload) || (mit einer JSON-Payload)
     * @return xml - trägt den Song in der DB ein. Falls erfolgreich, dann Status-Code 201 und liefert die URL für den neuen Song
     * im “Location”-Header an den Client zurück
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.TEXT_PLAIN)
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

    /**
     * @param id eingegeben in Postman/Kommandopfad
     * @return löscht den Eintrag für Id x in der DB und schickt on Success einen HTTP-Statuscode 204, Body ist leer.
     * falls Id nicht gefunden return mit HTTP-Statuscode 404
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteRecord(@PathParam("id") Integer id) {
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
     * @param id eingegeben in Postman/Kommandopfad
     * @param t  Content-Type: application/xml (mit einer XML-Payload) || (mit einer JSON-Payload)
     * @return - erneuert den Eintrag für songId  x in der DB und schickt “on Success” den HTTP-Statuscode 204, Body ist leer.
     */
    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateRecord(@PathParam("id") Integer id, Song t) {
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
