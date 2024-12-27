package net.delugan.teachly.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByGoogleId(String sub);

    default User getByOAuth2(OAuth2User oAuth2User) {
        String sub = oAuth2User.getAttribute("sub");
        return findByGoogleId(sub).orElseThrow();
    }
}
