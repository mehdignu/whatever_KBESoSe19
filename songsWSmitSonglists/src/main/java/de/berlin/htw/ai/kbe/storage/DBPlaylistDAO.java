package de.berlin.htw.ai.kbe.storage;

import de.berlin.htw.ai.kbe.entities.Playlist;
import de.berlin.htw.ai.kbe.entities.Song;
import de.berlin.htw.ai.kbe.entities.User;
import de.berlin.htw.ai.kbe.interfaces.PlaylistDAO;
import de.berlin.htw.ai.kbe.interfaces.Secured;

import javax.inject.Inject;
import javax.persistence.*;
import javax.transaction.Transactional;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Secured
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
    public Response getAllPlaylists(String userId, String userReq) {

        //get the owner of the lists
        User user;
        em = getEntityManager();
        em.getTransaction().begin();
        try {

            user = em.find(User.class, userId);

        } finally {
            em.getTransaction().commit();
            em.close();
        }

        List<Playlist> p;



        if (userId.equals(userReq)) {

            em = getEntityManager();
            em.getTransaction().begin();
            try {

                //give the user all his playlist lists
                Query q = em.createQuery("SELECT l FROM Playlist l WHERE l.owner = :user");
                q.setParameter("user", user);
                p = q.getResultList();

                System.out.println(q.getResultList());

            } finally {
                em.getTransaction().commit();
                em.close();
            }


        } else {

            //give the user only the public playlist lists
            em = getEntityManager();
            em.getTransaction().begin();
            try {

                //give the user all his playlist lists
                Query q = em.createQuery("SELECT l FROM Playlist l WHERE l.owner = :user AND l.isPrivate = :private ");
                q.setParameter("user", user);
                q.setParameter("private", false);
                p = q.getResultList();


            } finally {
                em.getTransaction().commit();
                em.close();
            }


        }


        return Response.status(Response.Status.OK).entity(p).build();
    }

    @Override
    public Response getSinglePlaylist(Integer playlistId, String user) {

        Playlist p;

        Query q = em.createQuery("SELECT l FROM Playlist l WHERE l.id = :id");
        q.setParameter("id", playlistId);
        try {
            p = (Playlist) q.getSingleResult();


            if (p.getOwner().getUserId().equals(user)) {
                return Response.status(Response.Status.OK).entity(p).build();
            } else {
                if (!p.isPrivate()) {
                    return Response.status(Response.Status.OK).entity(p).build();
                } else {
                    return Response.status(Response.Status.BAD_REQUEST).entity("cannot access private list").build();
                }
            }

        } catch (NoResultException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("no list have been found").build();
        }

    }

    @Override
    public Response createPLaylist(Playlist playlist, String userID) {

        //check if the playlist is fine
        if (checkPlaylist(playlist)) {


            try {


                User owner = dbUserDAO.getUserById(userID);

                playlist.setOwner(owner);


                if (playlist.getSongs() == null || playlist.getSongs().isEmpty()) {
                    throw new IllegalArgumentException("No songs given");
                }


                try {
                    System.out.println("Trying to post/insert new playlist in DB");
                    em = getEntityManager();
                    em.getTransaction().begin();
                    em.persist(playlist);
                    em.getTransaction().commit();
                } catch (Exception e) {
                    System.out.println(e);
                    em.getTransaction().rollback();
                } finally {
                    em.close();
                }

                UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
                uriBuilder.path(Integer.toString(playlist.getId()));

                URI uri = uriBuilder.build();

                return Response.status(Response.Status.OK).entity("all right").build();


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

        for (Song s : playlist.getSongs()) {

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


        em = getEntityManager();
        em.getTransaction().begin();
        boolean isThere = false;

        try {

            Query q = em.createQuery("SELECT l FROM Song l WHERE l.id = :id AND l.album = :album AND l.artist = :artist AND l.released = :released AND l.title = :title");
            q.setParameter("id", s.getId());
            q.setParameter("album", s.getAlbum());
            q.setParameter("artist", s.getArtist());
            q.setParameter("released", s.getReleased());
            q.setParameter("title", s.getTitle());


            Song res;
            try {
                res = (Song) q.getSingleResult();
                if (res != null) {
                    isThere = true;
                }


            } catch (NoResultException e) {
                return false;
            }

        } finally {
            em.getTransaction().commit();
            em.close();

        }

        return isThere;


    }

    @Override
    public Response deletePlaylist(Integer playlistId, String userID) {
        Playlist p;

        Query q = em.createQuery("SELECT l FROM Playlist l WHERE l.id = :id");
        q.setParameter("id", playlistId);
        try {
            p = (Playlist) q.getSingleResult();


            if (p.getOwner().getUserId().equals(userID)) {
                //delete list

                em = getEntityManager();
                em.getTransaction().begin();
                try {

                    em.remove(p);

                } finally {
                    em.getTransaction().commit();
                    em.close();

                }

            }

        } catch (NoResultException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("no list have been found").build();
        }

        return Response.status(Response.Status.OK).entity("list have been deleted").build();

    }

}
