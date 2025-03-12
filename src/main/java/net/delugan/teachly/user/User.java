package net.delugan.teachly.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import net.delugan.teachly.utils.DateTracked;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Entity class representing a user in the system.
 * Stores user profile information and authentication details.
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "users")
public class User extends DateTracked implements Serializable {
    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The username of the user.
     */
    @Column(nullable = false)
    @Schema(description = "The username of the User", example = "teachly")
    private String username;

    /**
     * The email address of the user.
     * This field is not exposed in JSON responses.
     */
    @Column(nullable = false, unique = true)
    @JsonIgnore
    @Schema(description = "The email of the User", example = "teachly@delugan.net")
    private String email;

    /**
     * URL to the user's profile picture.
     */
    @Schema(description = "The picture URL of the User", example = "https://picsum.photos/64")
    private String picture;

    /**
     * Timestamp of the user's last login.
     */
    @Column(name = "last_login")
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "The date of the last login of the User", example = "2024-12-26T23:35:38Z")
    private Date lastLogin;

    /**
     * The Google ID associated with this user.
     * This field is not exposed in JSON responses.
     */
    @Column(name = "google_id", unique = true, nullable = false)
    @JsonIgnore
    @Schema(description = "The Google ID of the User", example = "1234567890")
    private String googleId;

    /**
     * Constructs a new User with the specified details.
     *
     * @param username The username
     * @param email The email address
     * @param picture URL to the profile picture
     * @param googleId The Google ID
     */
    public User(String username, String email, String picture, String googleId) {
        super();
        this.username = username;
        this.email = email;
        this.picture = picture;
        this.googleId = googleId;
    }

    /**
     * Protected constructor for JPA.
     */
    protected User() {
        super();
    }

    /**
     * Gets the user's ID.
     *
     * @return The user's ID
     */
    public UUID getId() {
        return id;
    }

    /**
     * Gets the URL to the user's profile picture.
     *
     * @return The profile picture URL
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Sets the URL to the user's profile picture.
     *
     * @param password The profile picture URL
     */
    public void setPicture(String password) {
        this.picture = password;
    }

    /**
     * Gets the user's username.
     *
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username.
     *
     * @param username The new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the user's email address.
     *
     * @return The email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email The new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the timestamp of the user's last login.
     *
     * @return The last login timestamp
     */
    public Date getLastLogin() {
        return lastLogin;
    }

    /**
     * Sets the timestamp of the user's last login.
     *
     * @param lastLogin The new last login timestamp
     */
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * Gets the user's Google ID.
     *
     * @return The Google ID
     */
    public String getGoogleId() {
        return googleId;
    }
}
