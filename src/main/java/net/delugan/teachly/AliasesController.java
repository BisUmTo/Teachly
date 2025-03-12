package net.delugan.teachly;

import net.delugan.teachly.user.User;
import net.delugan.teachly.user.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for handling URL aliases and redirects.
 * Provides convenient shortcuts to various parts of the application.
 */
@Controller
public class AliasesController {
    /**
     * Repository for accessing and managing users.
     */
    final UserRepository userRepository;

    /**
     * Constructs a new AliasesController with the required repository.
     *
     * @param userRepository Repository for users
     */
    public AliasesController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Handles requests to the root URL.
     * Redirects to the dashboard page.
     *
     * @return Redirect URL to the dashboard
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    /**
     * Handles requests to the API v1 URL.
     * Redirects to the Swagger UI documentation.
     *
     * @return Redirect URL to the Swagger UI
     */
    @GetMapping("/api/v1")
    public String apiv1() {
        return "redirect:/swagger-ui/index.html";
    }

    /**
     * Handles requests to the API URL.
     * Redirects to the Swagger UI documentation.
     *
     * @return Redirect URL to the Swagger UI
     */
    @GetMapping("/api")
    public String api() {
        return "redirect:/swagger-ui/index.html";
    }

    /**
     * Handles requests to the javadocs URL.
     * Redirects to the Java Documentation.
     *
     * @return Redirect URL to the Java Documentation
     */
    @GetMapping("/docs")
    public String docs() {
        return "redirect:/javadocs/index.html";
    }

    /**
     * Handles requests to the javadocs URL.
     * Redirects to the Java Documentation.
     *
     * @return Redirect URL to the Java Documentation
     */
    @GetMapping("/javadocs")
    public String javadocs() {
        return "redirect:/javadocs/index.html";
    }

    /**
     * Handles requests to the profile page.
     * Redirects to the user's profile page based on their authentication.
     *
     * @param oAuth2User The authenticated user
     * @return Redirect URL to the user's profile page
     */
    @GetMapping("/dashboard/profile")
    public String profile(@AuthenticationPrincipal OAuth2User oAuth2User) {
        User user = userRepository.getByOAuth2(oAuth2User);
        return "redirect:/dashboard/users/show/"+user.getId();
    }
}
