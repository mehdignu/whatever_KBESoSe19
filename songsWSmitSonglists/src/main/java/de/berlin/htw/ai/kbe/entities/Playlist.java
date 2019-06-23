package de.berlin.htw.ai.kbe.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "songlists")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Playlist {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @NotNull
    private Integer id;

    private String name;

    //bidirectional oneToMany user from songlist to userId in User
    //userId as Foriegnkey
    @ManyToOne
    private User owner;

    @Column(name = "private", nullable = false)
    private boolean isPrivate = false;

    //here unidirectional manyToMany of song to songlist
    @ManyToMany
    @JoinTable(name = "list_song", joinColumns = { @JoinColumn(name = "list_id") }, inverseJoinColumns = {
            @JoinColumn(name = "song_id") })
    @XmlElementWrapper(name = "songs")
    @XmlElement(name = "song")
    @JsonProperty(value = "songs")
    private List<Song> songs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
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
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", isPrivate=" + isPrivate +
                ", songs=" + songs +
                '}';
    }
}
