package de.berlin.htw.ai.kbe.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@XmlRootElement
@Table(name = "songsService")
public class Song {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @NotNull
    private Integer songId;

    private String title;
    private String artist;
    private String album;
    private Integer released;

    @JsonIgnore
    @XmlTransient
    @ManyToMany(mappedBy="songsService")
    private Set<Playlist> lists;

    public Song() {
    }

    @NotNull
    public Integer getSongId() {
        return songId;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public Integer getReleased() {
        return released;
    }


    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setReleased(Integer released) {
        this.released = released;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songId= " + songId +
                ", title=' " + title + '\'' +
                ", artist=' " + artist + '\'' +
                ", album=' " + album + '\'' +
                ", released= " + released +
                '}';
    }

}
