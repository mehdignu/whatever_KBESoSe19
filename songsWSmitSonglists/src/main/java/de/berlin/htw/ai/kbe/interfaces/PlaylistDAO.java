package de.berlin.htw.ai.kbe.interfaces;

import de.berlin.htw.ai.kbe.entities.Playlist;

import javax.ws.rs.core.Response;
import java.util.List;

public interface PlaylistDAO {

    Response getAllPlaylists(String userID);

    Response getSinglePlaylist(Integer playlistId);

    Response createPLaylist(Playlist playlist, String userID);

    Response deletePlaylist(Integer songlistId);

}
