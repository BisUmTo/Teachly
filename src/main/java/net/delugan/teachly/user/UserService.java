package net.delugan.teachly.user;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * Service class for managing user authentication and registration.
 * Handles OAuth2 authentication flow and user creation/updates.
 */
@Service
public class UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    /**
     * Repository for accessing and managing users.
     */
    private final UserRepository userRepository;

    /**
     * Constructs a new UserService with the required repository.
     *
     * @param userRepository Repository for users
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads the user from the OAuth2 authentication process.
     * If the user doesn't exist, creates a new user record.
     * Updates the last login time for existing users.
     *
     * @param userRequest The OAuth2 user request
     * @return The authenticated OAuth2User
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");

        User user = userRepository.findByEmail(email).orElseGet(() -> new User(
                (String) attributes.get("name"),
                email,
                (String) attributes.get("picture"),
                attributes.get("sub").toString()
        ));
        user.setLastLogin(new Date());
        userRepository.save(user);
        return oAuth2User;
    }
}
