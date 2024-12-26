package net.delugan.teachly.user;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;

    private String picture;
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;
    @Column(name = "last_login", nullable = false)
    private Date lastLogin;
    @Column(name = "google_id", nullable = false)
    private String googleId;

    public User(String username, String email, String picture, String googleId) {
        this.username = username;
        this.email = email;
        this.picture = picture;
        this.createdAt = new Date();
        this.lastLogin = createdAt;
        this.googleId = googleId;
    }


    public UUID getId() {
        return id;
    }

    public void setPicture(String password) {
        this.picture = password;
    }

    public String getPicture() {
        return picture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
