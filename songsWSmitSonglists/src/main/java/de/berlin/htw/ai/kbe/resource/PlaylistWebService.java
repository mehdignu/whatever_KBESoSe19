package de.berlin.htw.ai.kbe.resource;

import de.berlin.htw.ai.kbe.entities.Playlist;
import de.berlin.htw.ai.kbe.interfaces.PlaylistDAO;
import de.berlin.htw.ai.kbe.interfaces.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/songLists")
@Secured
public class PlaylistWebService {

    private PlaylistDAO playlistDAO;

    /**
     * Beispiel: ‘GET /songsWS/rest/songLists?userId=mmuster’ soll alle Songlisten von ‘mmuster’ an User ‘mmuster’
     * zurückschicken.
     * Beispiel: ‘GET /songsWS/rest/songLists?userId=eschuler’ soll nur die ‘public’ Songlisten von ‘eschuler’ an User
     * ‘mmuster’ zurückschicken.
     *
     * @param userID
     * @return
     *
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Playlist> getAllPlaylists(@QueryParam("userId") String userID) {
        return playlistDAO.getAllPlaylists(userID);
    }

    /**
     * Beispiel: ‘GET /songsWS/rest/songLists/22’ soll die Songliste 22 an User ‘mmuster’ zurückschicken. Eine Songliste
     * 22 muss existieren. Wenn die Liste mmuster gehört, dann kann sie an mmuster zurückgeschickt werden. Wenn die
     * Liste einem anderen User gehört, dann kann die Liste nur zurückgeschickt werden, wenn sie “public” ist, ansonsten
     * einen HTTP-Status 403 (FORBIDDEN) schicken.
     *
     * @param playlistId
     * @return
     *
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Response getSinglePlaylist(@PathParam("id") Integer playlistId) {
        return playlistDAO.getSinglePlaylist(playlistId);
    }

    /**
     * POST /songsWS/rest/songLists mit XML oder JSON-Payload legt eine neue Songlist für den User ‘mmuster’ an und
     * schickt die URL mit neuer Id für die neue Songliste im Location-Header zurück. Alle Songs in der Payload der
     * Anfrage müssen in der DB existieren. Falls einer der Songs nicht existiert, können Sie der einfachheithalber eine 400
     * (BAD_REQUEST) zurückschicken.
     *
     * @param playlist
     * @return
     *
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.TEXT_PLAIN)
    public Response createPlaylist(Playlist playlist) {
        return playlistDAO.createPLaylist(playlist);
    }

    /**
     * User können nur eigene Songlisten löschen, nicht die der anderen User, auch keine fremden, public Songlisten.
     * Beispiel: ‘DELETE /songsWS/rest/songLists/22’. Löscht die Songliste 22, die dem User ‘mmuster’ gehört.
     * Beispiel: Ein ‘DELETE /songsWS/rest/songLists/33’ vom User ‘mmuster’ requested, aber 33 gehört eschuler, soll
     * von Ihrem Service mit HTTP-StatusCode 403 (FORBIDDEN) abgewiesen werden.
     *
     * @param songlistId
     * @return
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deletePlaylist(Integer songlistId) {
        return playlistDAO.deletePlaylist(songlistId);
    }

}
