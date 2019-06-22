package de.berlin.htw.ai.kbe.storage;

import de.berlin.htw.ai.kbe.entities.Playlist;
import de.berlin.htw.ai.kbe.entities.Song;
import de.berlin.htw.ai.kbe.interfaces.PlaylistDAO;
import de.berlin.htw.ai.kbe.interfaces.Secured;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

//@Secured
public class DBPlaylistDAO implements PlaylistDAO {

    //get the absolute path
    @Context
    private UriInfo uriInfo;

    private DBUserDAO dbUserDAO;

    private List<Playlist> playlists;

    private Song song;

    private Response response;

    protected EntityManager em;

    private static EntityManagerFactory emf;

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Inject
    public DBPlaylistDAO(EntityManagerFactory emf) {
        this.emf = emf;
        this.dbUserDAO = new DBUserDAO();
    }

    @Override
    public Response getAllPlaylists(String userId) {



        return Response.status(Response.Status.NOT_FOUND).entity("ID not found").build();
    }

    @Override
    public Response getSinglePlaylist(Integer playlistId) {

        // to implement

        return null;
    }

    @Override
    public Response createPLaylist(Playlist playlist) {

        System.out.println(playlist);

        return Response.status(Response.Status.NOT_FOUND).entity("ID not found").build();
    }

    @Override
    public Response deletePlaylist(Integer songlistId) {

        //to implement

        return null;
    }

}
