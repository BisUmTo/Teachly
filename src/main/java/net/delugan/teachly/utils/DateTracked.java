package net.delugan.teachly.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

public abstract class DateTracked {
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "The date when it was created", example = "2024-12-26T23:35:38Z")
    private Date createdAt;

    @Column(name = "last_modified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "The date when it was last modified", example = "2024-12-26T23:35:38Z")
    private Date lastModified;

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void updateLastModified() {
        this.lastModified = new Date();
    }

    protected DateTracked() {
        this.createdAt = new Date();
        this.lastModified = new Date();
    }
}
