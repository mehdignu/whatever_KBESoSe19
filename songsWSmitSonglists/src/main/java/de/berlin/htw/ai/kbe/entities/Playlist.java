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

    //bidirectional oneToMany user from songlist to userId in User
    //userId as Foriegnkey
    @ManyToOne
    @JoinColumn(name= "userId")
    private User owner;

    private String playlistName;

    private boolean isPublic;

    //here unidirectional manyToMany of song to songlist
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "list_song",
            joinColumns = {@JoinColumn(name = "listId")}, inverseJoinColumns = {
            @JoinColumn(name = "songId")})
    @XmlElementWrapper(name = "songs")
    @XmlElement(name = "song")
    @JsonProperty(value = "songs")
    private List<Song> songs;

    public Integer id() {
        return id;
    }

    public void id(Integer playlistId) {
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

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "id=" + id +
                ", owner=" + owner +
                ", playlistName='" + playlistName + '\'' +
                ", isPublic=" + isPublic +
                ", songs=" + songs +
                '}';
    }
}
