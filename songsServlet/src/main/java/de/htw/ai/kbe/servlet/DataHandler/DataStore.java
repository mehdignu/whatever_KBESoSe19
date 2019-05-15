package de.htw.ai.kbe.servlet.DataHandler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.ai.kbe.servlet.pojo.Song;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class DataStore {


    private Map<Integer, Song> songs;
    private static int latestID;
    private static String filePath;

    public DataStore() {
        this.songs = new HashMap<>();
    }

    /**
     * loads all songs from the data path and save them
     *
     * @param dataPath
     * @throws IOException
     */
    public void loadSongs(String dataPath) throws IOException {

        try {

            filePath = dataPath;
            List<Song> readSongs = readXMLToSongs(dataPath);

            //save all the songs along with their id's
            this.songs = readSongs.stream().collect(Collectors.toMap(Song::getId, Function.identity()));

            //gte the latest id
            latestID = songs.keySet().stream().max(Integer::compareTo).orElse(0) + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @SuppressWarnings("unchecked")
    public static Song readJSONToSongs(InputStream is) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return (Song) objectMapper.readValue(is, new TypeReference<Song>() {
            });
        } catch (Exception e) {
            throw new IOException("could not read song from stream");
        }
    }

 public synchronized void saveSongs() throws IOException {

        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(filePath))) {
            Songs songsWrapper = new Songs();
            songsWrapper.setSongList((List<Song>) songs);

            try {
                JAXBContext context = JAXBContext.newInstance(Songs.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(songsWrapper, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                throw new IOException("failed to save songs");
            }

        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }

    }

    /**
     * adds the song to the store
     * @param song
     * @return
     */
    public synchronized Song addSong(Song song) {
        song.setId(latestID++);
        this.songs.put(song.getId(), song);
        return song;
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
