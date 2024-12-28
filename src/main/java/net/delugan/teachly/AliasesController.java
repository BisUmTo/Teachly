package net.delugan.teachly;

import net.delugan.teachly.user.User;
import net.delugan.teachly.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AliasesController {
    final UserRepository userRepository;

    public AliasesController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/api/v1")
    public String apiv1() {
        return "redirect:/swagger-ui/index.html";
    }

    @GetMapping("/api")
    public String api() {
        return "redirect:/swagger-ui/index.html";
    }

    @GetMapping("/docs")
    public String docs() {
        return "redirect:/swagger-ui/index.html";
    }

    @GetMapping("/dashboard/profile")
    public String profile(@AuthenticationPrincipal OAuth2User oAuth2User) {
        User user = userRepository.getByOAuth2(oAuth2User);
        return "redirect:/dashboard/users/show/"+user.getId();
    }
}
