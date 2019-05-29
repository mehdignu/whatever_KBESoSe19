package de.htw.ai.kbe.dataBase;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    private String userId;
    private String password;
    private String firstName;
    private String lastName;

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 50)
    public String getUserId() {
        return userId;
    }

    @Column(length = 50)
    public String getPassword() {
        return password;
    }

    @Column(length = 50)
    public String getFirstName() {
        return firstName;
    }

    @Column(length = 50)
    public String getLastName() {
        return lastName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}