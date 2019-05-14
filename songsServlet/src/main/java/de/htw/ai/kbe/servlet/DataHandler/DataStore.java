package de.htw.ai.kbe.servlet.DataHandler;

import de.htw.ai.kbe.servlet.pojo.Song;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;
import java.util.stream.Collectors;


public class DataStore {


    private Map<Integer, Song> songs;

    public DataStore() {
        this.songs = new HashMap<>();
    }

    /**
     * loads all songs from the data path and save them
     * @param dataPath
     * @throws IOException
     */
    public void loadSongs(String dataPath) throws IOException {

        try {

            List<Song> readSongs = readXMLToSongs(dataPath);

            //save all the songs along with their id's
            this.songs = readSongs.stream().collect(Collectors.toMap(Song::getId, Function.identity()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Reads a list of songs from an XML-file into a Songs object
    private static List<Song> readXMLToSongs(String filename)
            throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(SongsWrapper.class, Song.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
            return unmarshal(unmarshaller, Song.class, filename);
        }
    }

    private static List<Song> unmarshal(Unmarshaller unmarshaller, Class<Song> clazz, String xmlLocation)
            throws JAXBException {
        StreamSource xml = new StreamSource(xmlLocation);
        SongsWrapper wrapper = unmarshaller.unmarshal(xml, SongsWrapper.class).getValue();
        return wrapper.getSongs();
    }

    public synchronized List<Song> getAllSongs() {
        return new ArrayList<>(songs.values());
    }

    public synchronized Song getSong(int id) {
        return songs.get(id);
    }

}
