package de.htw.ai.kbe.xmlHandler;

import de.htw.ai.kbe.entities.Song;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.List;

public class XMLConverter {


    public XMLConverter() {

    }

    /**
    public void initToRead() {

        try {
            List<Song> readSongs = readXMLToSongs();
            readSongs.forEach(s -> {
                System.out.println(s.getTitle());
            });
        } catch (Exception e) { // Was stimmt hier nicht?
            e.printStackTrace();
        }

    }
    private static List<Song> unmarshal(Unmarshaller unmarshaller, Class<Song> clazz, String xmlLocation)
            throws JAXBException {
        StreamSource xml = new StreamSource(xmlLocation);
        SongsWrapper wrapper = (SongsWrapper) unmarshaller.unmarshal(xml, SongsWrapper.class).getValue();
        return wrapper.getSongs();
    }


    // Reads a list of songs from an XML-file into a Songs object
    private static List<Song> readXMLToSongs()
            throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(SongsWrapper.class, Song.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
            List<Song> songs = unmarshal(unmarshaller, Song.class, filename);
            return songs;
        }
    }
    */

}
