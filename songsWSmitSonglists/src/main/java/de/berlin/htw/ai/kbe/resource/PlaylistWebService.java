package de.berlin.htw.ai.kbe.resource;

import de.berlin.htw.ai.kbe.entities.Playlist;
import de.berlin.htw.ai.kbe.interfaces.PlaylistDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class PlaylistWebService {

    private PlaylistDAO playlistDAO;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Playlist> getAllPlaylists() {
        return playlistDAO.getAllPlaylists();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Response getSinglePlaylist(@PathParam("id") Integer playlistId) {
        return playlistDAO.getSinglePlaylist(playlistId);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.TEXT_PLAIN)
    public Response createPlaylist() {
        return playlistDAO.createPLaylist();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deletePlaylist(Integer songListId) {
        return playlistDAO.deletePlaylist(songListId);
    }

}
