package de.berlin.htw.ai.kbe.interfaces;

import de.berlin.htw.ai.kbe.entities.Song;

import javax.ws.rs.core.Response;
import java.util.List;

@Secured
public interface SongsDAO {

    List<Song> getAllRecords();

    Response getSingleRowRecord(Integer id);

    Response createRecord(Song t);

    Response updateRecord(Integer id, Song t);

    Response deleteRecord(Integer id);

}
