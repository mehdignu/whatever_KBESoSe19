package de.berlin.htw.ai.kbe.storage;

import de.berlin.htw.ai.kbe.entities.Playlist;
import de.berlin.htw.ai.kbe.entities.Song;
import de.berlin.htw.ai.kbe.entities.User;
import de.berlin.htw.ai.kbe.interfaces.PlaylistDAO;
import de.berlin.htw.ai.kbe.interfaces.Secured;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

//@Secured
public class DBPlaylistDAO implements PlaylistDAO {

    //get the absolute path
    @Context
    private UriInfo uriInfo;

    private DBUserDAO dbUserDAO;
    private DBSongsDAO dbSongsDAO;

    private List<Playlist> playlists;

    private Song song;

    private Response response;

    private EntityManager em;

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
    public Response createPLaylist(Playlist playlist, String userID) {

        //check if the playlist is fine
        if (checkPlaylist(playlist)) {


            try {


                User owner = dbUserDAO.getUserById(userID);

                playlist.setOwner(owner);


                if (playlist.getSongList() == null || playlist.getSongList().isEmpty()) {
                    throw new IllegalArgumentException("No songs given");
                }


                try {
                    em = getEntityManager();
                    em.getTransaction().begin();
                    em.persist(playlist);
                    em.getTransaction().commit();
                } catch (Exception e) {
                    em.getTransaction().rollback();
                    throw new PersistenceException("Could not persist entity: " + e.toString());
                }


                UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
                uriBuilder.path(Integer.toString(playlist.getId()));

                URI uri = uriBuilder.build();


                return Response.status(Response.Status.BAD_REQUEST).entity("fuk you").build();


            } catch (NoSuchElementException | PersistenceException | IllegalArgumentException e) {
                return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
            }
        } else {
            //things not fine
            return Response.status(Response.Status.BAD_REQUEST).entity("playlist is not valid").build();
        }
    }


    /**
     * check if the songs of the playlist exist in the database
     *
     * @param playlist
     * @return
     */
    private boolean checkPlaylist(Playlist playlist) {


        boolean allGood = true;

        for (Song s : playlist.getSongList()) {

            if (!isSongThere(s)) {
                allGood = false;
                break;
            }

        }

        return allGood;

    }


    /**
     * chekc if song s exixsts in the database
     *
     * @param s
     * @return
     */
    private boolean isSongThere(Song s) {
        song = new Song();
        em = getEntityManager();
        em.getTransaction().begin();
        boolean res = false;
        try {

            //for now i only check the id, aber es kann genauer sein
            song = em.find(Song.class, s.getId());
            if (song != null) {
                res = true;
            } else {
                res = false;
            }
        } finally {
            em.getTransaction().commit();
            em.close();
            return res;
        }
    }

    @Override
    public Response deletePlaylist(Integer playlistId) {

        //to implement

        return null;
    }

}
