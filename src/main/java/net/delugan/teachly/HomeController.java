package net.delugan.teachly;

import net.delugan.teachly.user.UserRepository;
import net.delugan.teachly.utils.AuthenticatedModelAndView;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller class for handling basic navigation and page requests.
 * Provides endpoints for the dashboard, login, and various static pages.
 */
@Controller
public class HomeController {
    /**
     * Repository for accessing and managing users.
     */
    final UserRepository userRepository;

    /**
     * Constructs a new HomeController with the required repository.
     *
     * @param userRepository Repository for users
     */
    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Handles requests to the dashboard page.
     * Adds user information and statistics to the model.
     *
     * @param oAuth2User The authenticated user
     * @return ModelAndView for the dashboard page
     */
    @GetMapping("/dashboard")
    public ModelAndView index(@AuthenticationPrincipal OAuth2User oAuth2User) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("index", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("userCount", userRepository.count());
        return modelAndView;
    }

    /**
     * Handles requests to the login page.
     *
     * @return View name for the login page
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Handles requests to the Blockly editor page.
     *
     * @return View name for the Blockly editor
     */
    @GetMapping("/dashboard/blockly")
    public String blockly() {
        return "blockly";
    }

    /**
     * Handles requests to the cookies policy page.
     *
     * @return View name for the cookies policy
     */
    @GetMapping("/cookies")
    public String cookies() {
        return "cookies";
    }

    /**
     * Handles requests to the privacy policy page.
     *
     * @return View name for the privacy policy
     */
    @GetMapping("/privacy")
    public String privacy() {
        return "privacy";
    }

    /**
     * Handles requests to the terms of service page.
     *
     * @return View name for the terms of service
     */
    @GetMapping("/tos")
    public String tos() {
        return "tos";
    }
}
