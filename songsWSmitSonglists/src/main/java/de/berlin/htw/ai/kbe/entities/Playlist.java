package de.berlin.htw.ai.kbe.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name = "songListsService")
public class Playlist {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @NotNull
    private Integer id;

    @ManyToOne
    //bidirectional oneToMany user from songlist to userId in User
    private User owner;

    private String playlistName;
    private boolean isPublic;

    //here unidirectional manyToMany of song to songlist
    private Set<Song> songs;

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

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
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
