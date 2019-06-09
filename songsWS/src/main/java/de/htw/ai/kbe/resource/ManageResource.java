package de.htw.ai.kbe.resource;

import de.htw.ai.kbe.PermissionController.Secured;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Secured
public abstract class ManageResource<T> {

    protected abstract List getListOfRecords();

    protected abstract Response getSingleRecord(Integer id);

    protected abstract Response createSingleRecord(T t);

    protected abstract Response deleteSingleRecord(Integer id);

    protected abstract Response updateSingleRecord(Integer id, T t);

    // entspricht <persistence-unit name="songDB-PU" transaction-type="RESOURCE_LOCAL"> in persistence.xml
    private static final String PERSISTENCE_UNIT_NAME = Constants.PERS_UNIT_NAME;

    protected EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        return emf.createEntityManager();
    }


    /**
     * Bsp:      GET http://localhost:8080/songsWS/rest/songs
     *
     * @return json
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<T> getAllRecords() {
        List records = getListOfRecords();
        return records;
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
        return getSingleRecord(id);
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
    public Response createRecord(T t) {
        return createSingleRecord(t);
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
    public Response updateRecord(@PathParam("id") Integer id, T t) {
        return updateSingleRecord(id, t);
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
        return deleteSingleRecord(id);
    }


}
