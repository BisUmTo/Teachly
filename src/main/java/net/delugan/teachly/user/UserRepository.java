package net.delugan.teachly.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for accessing and managing User entities.
 * Extends JpaRepository to inherit standard CRUD operations.
 */
public interface UserRepository extends JpaRepository<User, UUID> {
    /**
     * Finds a user by their email address.
     *
     * @param email The email address to search for
     * @return An Optional containing the user if found, or empty if not found
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Finds a user by their Google ID (sub).
     *
     * @param sub The Google ID to search for
     * @return An Optional containing the user if found, or empty if not found
     */
    Optional<User> findByGoogleId(String sub);

    /**
     * Gets a user from an OAuth2User object.
     * Extracts the Google ID (sub) from the OAuth2User and finds the corresponding user.
     *
     * @param oAuth2User The OAuth2User to get the user for
     * @return The corresponding User entity
     * @throws java.util.NoSuchElementException If no user is found
     */
    default User getByOAuth2(OAuth2User oAuth2User) {
        String sub = oAuth2User.getAttribute("sub");
        return findByGoogleId(sub).orElseThrow();
    }

    /**
     * Finds all users and removes their email addresses for privacy.
     * Used when returning user lists to clients that shouldn't see email addresses.
     *
     * @return A list of all users with email addresses set to null
     */
    default List<User> findAllWithoutEmail(){
        List<User> users = findAll();
        users.forEach(user -> user.setEmail(null));
        return users;
    }

    /**
     * Finds a user by ID and removes their email address for privacy.
     * Used when returning a user to clients that shouldn't see the email address.
     *
     * @param id The ID of the user to find
     * @return An Optional containing the user with email set to null if found, or empty if not found
     */
    default Optional<User> findByIdWithoutEmail(UUID id){
        Optional<User> user = findById(id);
        user.ifPresent(value -> value.setEmail(null));
        return user;
    }
}
