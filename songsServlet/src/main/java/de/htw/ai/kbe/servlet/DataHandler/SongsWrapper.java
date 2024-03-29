package de.htw.ai.kbe.servlet.DataHandler;


import de.htw.ai.kbe.servlet.pojo.Song;

import java.util.List;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAnyElement;

public class SongsWrapper {

    private List<Song> songs;

    public SongsWrapper() {
        songs = new ArrayList<Song>();
    }

    public SongsWrapper(List<Song> songs) {
        this.songs = songs;
    }

    @XmlAnyElement(lax=true)
    public List<Song> getSongs() {
        return songs;
    }

}
