package de.htw.ai.kbe.resource;

import de.htw.ai.kbe.storage.InMemorySongCollection;
import de.htw.ai.kbe.storage.Song;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;

/**
 * Root resource (exposed at "songs" path)
 */
@Path("songs")
public class SongsResource {

    private InMemorySongCollection songsList = InMemorySongCollection.getInstance();

    /**
     * Bsp:      GET http://localhost:8080/songsWS/rest/songs
     *
     * @return json
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Collection<Song> getAllSongs() {
        System.out.println("getAllSong: Returning all songs!");
        return songsList.getAllSongs();
    }

    //

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
    public Response getSong(@PathParam("id") Integer id) {
        Song song = songsList.getSong(id);
        if (song != null) {
            System.out.println("getSong: Returning song for id " + id);
            return Response.status(Response.Status.OK).entity(song).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("ID not found").build();
        }
    }

    //get the absolute path
    @Context
    UriInfo uriInfo;

    /**
     * POST http://localhost:8080/songsWS/rest/songs with song in payload
     *
     * @param song Content-Type:application/xml (mit einer XML-Payload) || (mit einer JSON-Payload)
     * @return xml - trägt den Song in der DB ein. Falls erfolgreich, dann Status-Code 201 und liefert die URL für den neuen Song
     * im “Location”-Header an den Client zurück
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.TEXT_PLAIN)
    public Response createSong(Song song) {
        Integer newId = songsList.addSong(song);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(Integer.toString(newId));
        return Response.created(uriBuilder.build()).build();
    }

    /**
     *
     * @param id eingegeben in Postman/Kommandopfad
     * @param song Content-Type: application/xml (mit einer XML-Payload) || (mit einer JSON-Payload)
     * @return - erneuert den Eintrag für songId  x in der DB und schickt “on Success” den HTTP-Statuscode 204, Body ist leer.
     *
     */
    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateSong(@PathParam("id") Integer id, Song song ) {

        //noch nicht implemented
        return null;
    }

    /**
     *
     * @param id eingegeben in Postman/Kommandopfad
     * @return löscht den Eintrag für Id x in der DB und schickt on Success einen HTTP-Statuscode 204, Body ist leer.
     *          falls Id nicht gefunden return mit HTTP-Statuscode 404
     *
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteSong(@PathParam("id") Integer id) {
        Song song = songsList.getSong(id);
        if (song != null) {
            System.out.println("deleteSong: deleting song for id " + id);
            songsList.deleteSong(id);
            return Response.status(Response.Status.NO_CONTENT).entity("").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("ID not found").build();
        }
    }


}
