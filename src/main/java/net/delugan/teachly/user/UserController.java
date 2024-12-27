package net.delugan.teachly.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("user/principal")
    public Principal user(Principal principal) {
        return principal;
    }

    @GetMapping("user")
    public User user(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return userRepository.getByOAuth2(oAuth2User);
    }

    @GetMapping("users")
    public Iterable<User> users() {
        return userRepository.findAll();
    }

    @GetMapping("users/{id}")
    public User getUserById(@PathVariable UUID id) {
        return userRepository.findById(id).orElseThrow();
    }
}
