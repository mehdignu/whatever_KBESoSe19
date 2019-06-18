package de.berlin.htw.ai.kbe.storage;

import de.berlin.htw.ai.kbe.entities.Playlist;
import de.berlin.htw.ai.kbe.entities.Song;
import de.berlin.htw.ai.kbe.interfaces.PlaylistDAO;
import de.berlin.htw.ai.kbe.interfaces.Secured;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Secured
public class DBPlaylistDAO implements PlaylistDAO {

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
    public DBPlaylistDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Playlist> getAllPlaylists(String userId) {
        return null;
    }

    @Override
    public Response getSinglePlaylist(Integer playlistId) {
        return null;
    }

    @Override
    public Response createPLaylist(Playlist playlist) {
        return null;
    }

    @Override
    public Response deletePlaylist(Integer songlistId) {
        return null;
    }

}
