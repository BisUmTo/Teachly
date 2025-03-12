package net.delugan.teachly.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

/**
 * REST controller for user-related operations.
 * Provides endpoints for retrieving user information.
 */
@RestController
@RequestMapping("/api/v1")
public class UserRestController {
    /**
     * Repository for accessing and managing users.
     */
    private final UserRepository userRepository;

    /**
     * Constructs a new UserRestController with the required repository.
     *
     * @param userRepository Repository for users
     */
    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves the authentication principal.
     *
     * @param principal The authentication principal
     * @return The authentication principal
     */
    @GetMapping("user/principal")
    public Principal user(Principal principal) {
        return principal;
    }

    /**
     * Retrieves the authenticated user.
     *
     * @param oAuth2User The authenticated OAuth2 user
     * @return The user corresponding to the authenticated OAuth2 user
     */
    @GetMapping("user")
    public User user(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return userRepository.getByOAuth2(oAuth2User);
    }

    /**
     * Retrieves all users.
     *
     * @return Iterable of all users
     */
    @GetMapping("users")
    public Iterable<User> users() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by ID, without exposing the email.
     *
     * @param id The ID of the user to retrieve
     * @return The user with the specified ID, with email field hidden
     * @throws java.util.NoSuchElementException if no user is found with the given ID
     */
    @GetMapping("users/{id}")
    public User getUserById(@PathVariable UUID id) {
        return userRepository.findByIdWithoutEmail(id).orElseThrow();
    }
}
