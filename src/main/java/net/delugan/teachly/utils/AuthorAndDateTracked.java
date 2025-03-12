package net.delugan.teachly.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import net.delugan.teachly.user.User;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

/**
 * Abstract base class that extends DateTracked to add author tracking functionality.
 * Provides fields and methods to track who created an entity.
 */
@MappedSuperclass
public abstract class AuthorAndDateTracked extends DateTracked {
    /**
     * The ID of the user who authored this entity.
     */
    @Column(name = "author_id", updatable = false, nullable = false)
    @Schema(description = "The ID of the User that generated this")
    private UUID authorId;

    /**
     * The user who authored this entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", nullable = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnore
    @Schema(description = "The User that generated this")
    private User author;

    /**
     * Gets the ID of the author.
     *
     * @return The author's ID
     */
    public UUID getAuthorId() {
        return authorId;
    }

    /**
     * Gets the author user.
     *
     * @return The author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Sets the author of this entity.
     * Also updates the authorId field.
     *
     * @param author The user who authored this entity
     */
    public void setAuthor(User author) {
        this.author = author;
        authorId = author.getId();
    }

    /**
     * Checks if the given user is the author of this entity.
     *
     * @param user The user to check
     * @return true if the user is the author, false otherwise
     */
    public boolean isAuthor(User user) {
        return author.equals(user);
    }
}
