package net.delugan.teachly.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

/**
 * Abstract base class that provides date tracking functionality for entities.
 * Tracks creation and modification dates for database entities.
 */
@MappedSuperclass
public abstract class DateTracked {
    /**
     * The date when the entity was created.
     * This field is immutable after creation.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "The date when it was created", example = "2024-12-26T23:35:38Z")
    private Date createdAt;

    /**
     * The date when the entity was last modified.
     * This field is updated whenever the entity is modified.
     */
    @Column(name = "last_modified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "The date when it was last modified", example = "2024-12-26T23:35:38Z")
    private Date lastModified;

    /**
     * Default constructor that initializes creation and modification dates to the current time.
     */
    protected DateTracked() {
        this.createdAt = new Date();
        this.lastModified = new Date();
    }

    /**
     * Gets the creation date of the entity.
     *
     * @return The date when the entity was created
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the last modification date of the entity.
     *
     * @return The date when the entity was last modified
     */
    public Date getLastModified() {
        return lastModified;
    }

    /**
     * Updates the last modification date to the current time.
     * Should be called whenever the entity is modified.
     */
    public void updateLastModified() {
        this.lastModified = new Date();
    }
}
