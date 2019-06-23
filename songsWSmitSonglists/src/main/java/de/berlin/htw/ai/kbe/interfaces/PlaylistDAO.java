package de.berlin.htw.ai.kbe.interfaces;

import de.berlin.htw.ai.kbe.entities.Playlist;

import javax.ws.rs.core.Response;
import java.util.List;

public interface PlaylistDAO {

    Response getAllPlaylists(String userID, String userReq);

    Response getSinglePlaylist(Integer playlistId, String user);

    Response createPLaylist(Playlist playlist, String userID);

    Response deletePlaylist(Integer songlistId, String userID);



}
