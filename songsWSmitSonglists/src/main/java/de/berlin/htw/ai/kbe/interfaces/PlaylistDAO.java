package de.berlin.htw.ai.kbe.interfaces;

import de.berlin.htw.ai.kbe.entities.Playlist;

import javax.ws.rs.core.Response;
import java.util.List;

public interface PlaylistDAO {

    List<Playlist> getAllPlaylists();

    Response getSinglePlaylist(Integer playlistId);

    Response createPLaylist();

    Response deletePlaylist(Integer songListId);

}
