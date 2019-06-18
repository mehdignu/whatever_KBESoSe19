package de.berlin.htw.ai.kbe.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Entity
@XmlRootElement
@Table(name = "usersService")
public class User {

    @Id
    private String userId;
    private String password;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy="playlist",
        cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Playlist> playlists;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
