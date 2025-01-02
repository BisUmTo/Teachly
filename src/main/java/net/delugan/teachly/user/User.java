package net.delugan.teachly.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import net.delugan.teachly.utils.DateTracked;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "users")
public class User extends DateTracked implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @Schema(description = "The username of the User", example = "teachly")
    private String username;

    @Column(nullable = false, unique = true)
    @Schema(description = "The email of the User", example = "teachly@delugan.net")
    private String email;

    @Schema(description = "The picture URL of the User", example = "https://picsum.photos/64")
    private String picture;

    @Column(name = "last_login")
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "The date of the last login of the User", example = "2024-12-26T23:35:38Z")
    private Date lastLogin;

    @Column(name = "google_id", unique = true, nullable = false)
    @Schema(description = "The Google ID of the User", example = "1234567890")
    private String googleId;

    public User(String username, String email, String picture, String googleId) {
        super();
        this.username = username;
        this.email = email;
        this.picture = picture;
        this.googleId = googleId;
    }

    protected User() {
        super();
    }

    public UUID getId() {
        return id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String password) {
        this.picture = password;
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

    public void setEmail(String email) {
        this.email = email;
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
}
