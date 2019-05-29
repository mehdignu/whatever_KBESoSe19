package de.htw.ai.kbe.dataBase;

import javax.persistence.*;

@Entity
@Table(name="song")
public class Song {

    private int id;
    private Integer songId;
    private String title;
    private String  artist;
    private String album;
    private Integer released;

    public Song () {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

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

    public void setId(int id) {
        this.id = id;
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
}
