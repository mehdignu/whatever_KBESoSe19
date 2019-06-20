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

@Secured
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
    public List<Playlist> getAllPlaylists(String userId) {

        System.out.println("Check User: " + userId);
        em = getEntityManager();

        if(dbUserDAO.userTokenList.containsKey(userId)) {
            System.out.println("The user " + userId + " is logged in");
            try {
                em.getTransaction().begin();
                Query newQuery = em.createQuery("SELECT playlist FROM Playlist playlist WHERE playlist.owner = :userId ");
                newQuery.setParameter("userId", userId);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        } else {
            // throw an exception
        }
        return playlists;
    }

    @Override
    public Response getSinglePlaylist(Integer playlistId) {

        // to implement

        return null;
    }

    @Override
    public Response createPLaylist(Playlist playlist) {

        // to implement

        return null;
    }

    @Override
    public Response deletePlaylist(Integer songlistId) {

        //to implement

        return null;
    }

}
