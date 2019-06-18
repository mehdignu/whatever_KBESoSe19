package de.berlin.htw.ai.kbe.interfaces;

import de.berlin.htw.ai.kbe.entities.Playlist;

import javax.ws.rs.core.Response;
import java.util.List;

public interface PlaylistDAO {

    List<Playlist> getAllPlaylists(String userID);

    Response getSinglePlaylist(Integer playlistId);

    Response createPLaylist(Playlist playlist);

    Response deletePlaylist(Integer songlistId);

}
