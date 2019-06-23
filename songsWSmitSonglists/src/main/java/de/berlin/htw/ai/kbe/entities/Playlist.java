package de.berlin.htw.ai.kbe.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name = "songlistService")
public class Playlist {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @NotNull
    private Integer id;

    private String name;

    //bidirectional oneToMany user from songlist to userId in User
    //userId as Foriegnkey
    @ManyToOne
    @JoinColumn(name= "userId")
    private User owner;

    private String playlistName;

    private boolean isPrivate;

    //here unidirectional manyToMany of song to songlist
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "list_song",
            joinColumns = {@JoinColumn(name = "listId")}, inverseJoinColumns = {
            @JoinColumn(name = "songId")})
    @XmlElementWrapper(name = "songList")
    @XmlElement(name = "songList")
    @JsonProperty(value = "songList")
    private List<Song> songList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer playlistId) {
        this.id = playlistId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPublic(boolean aPublic) {
        isPrivate = aPublic;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songs) {
        this.songList = songs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
