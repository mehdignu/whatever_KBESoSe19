package de.berlin.htw.ai.kbe.storage;

import de.berlin.htw.ai.kbe.entities.Playlist;
import de.berlin.htw.ai.kbe.interfaces.PlaylistDAO;

import javax.ws.rs.core.Response;
import java.util.List;

public class DBPlaylistDAO implements PlaylistDAO {

    @Override
    public List<Playlist> getAllPlaylists() {
        return null;
    }

    @Override
    public Response getSinglePlaylist(Integer playlistId) {
        return null;
    }

    @Override
    public Response createPLaylist() {
        return null;
    }

    @Override
    public Response deletePlaylist(Integer songListId) {
        return null;
    }

}
