package net.delugan.teachly.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import net.delugan.teachly.user.User;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

public abstract class AuthorAndDateTracked extends DateTracked {
    @Column(name = "author_id", insertable = false, updatable = false)
    @Schema(description = "The ID of the User that generated this")
    private UUID authorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnore
    @Schema(description = "The User that generated this")
    private User author;

    public UUID getAuthorId() {
        return authorId;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
        if (author != null) {
            this.authorId = author.getId();  // Assicurati che 'getId' restituisca l'UUID
        } else {
            this.authorId = null;  // Se 'author' è null, imposta anche 'authorId' su null
        }
    }
}
