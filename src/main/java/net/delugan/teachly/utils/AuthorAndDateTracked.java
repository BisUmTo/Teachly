package net.delugan.teachly.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import net.delugan.teachly.user.User;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@MappedSuperclass
public abstract class AuthorAndDateTracked extends DateTracked {
    @Column(name = "author_id", insertable = false, updatable = false, nullable = false)
    @Schema(description = "The ID of the User that generated this")
    private UUID authorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", nullable = false)
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
        authorId = author.getId();
    }

    public boolean isAuthor(User user) {
        return author.equals(user);
    }
}
