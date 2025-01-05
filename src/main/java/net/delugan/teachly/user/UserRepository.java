package net.delugan.teachly.user;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByGoogleId(String sub);

    default User getByOAuth2(OAuth2User oAuth2User) {
        String sub = oAuth2User.getAttribute("sub");
        return findByGoogleId(sub).orElseThrow();
    }

    default List<User> findAllWithoutEmail(){
        List<User> users = findAll();
        users.forEach(user -> user.setEmail(null));
        return users;
    }

    default Optional<User> findByIdWithoutEmail(UUID id){
        Optional<User> user = findById(id);
        user.ifPresent(value -> value.setEmail(null));
        return user;
    }
}
