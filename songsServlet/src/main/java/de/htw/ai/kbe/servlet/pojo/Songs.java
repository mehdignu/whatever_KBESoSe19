package de.htw.ai.kbe.servlet.pojo;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Songs {

    @XmlElement(name = "song")
    private List<Song> songList;

    public List<Song> getSongList() {
        return this.songList;
    }

    public void setSongList(List<Song> song) {
        this.songList = song;
    }


}
