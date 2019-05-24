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

    //Bsp:      GET http://localhost:8080/songsWS/rest/songs
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Collection<Song> getAllSongs() {
        System.out.println("getAllSong: Returning all songs!");
        return songsList.getAllSongs();
    }

    //Bsp:      GET http://localhost:8080/songsWS/rest/songs/1
    //Returns:  200 & contact with id 1 or 404 when id not found,
    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getSong(@PathParam("id") Integer id) {
        Song song = songsList.getSong(id);
        if (song != null) {
            System.out.println("getSong: Returning song for id " + id);
            return Response.status(Response.Status.OK).entity(song).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("ID not found").build();
        }
    }

    //Returns: 200 and 204 on provided id not found
//	@GET
//  @Path("/{id}")
//  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
//	public Contact getContact(@PathParam("id") Integer id) {
//        return addressBook.getContact(id);
//    }


    //  POST http://localhost:8080/contactsJAXRS/rest/contacts with contact in payload
//  Status Code 201 und URI fuer den neuen Eintrag im http-header 'Location' zurueckschicken, also:
//  Location: /contactsJAXRS/rest/contacts/neueID

    @Context
    UriInfo uriInfo;

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces(MediaType.TEXT_PLAIN)
    public Response createSong(Song song) {
        Integer newId = songsList.addSong(song);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(Integer.toString(newId));
        return Response.created(uriBuilder.build()).build();
    }

}
