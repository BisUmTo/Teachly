package net.delugan.teachly.user;

import net.delugan.teachly.utils.AuthenticatedModelAndView;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

/**
 * Controller for user-related views.
 * Handles requests for viewing users and user profiles.
 */
@Controller
@RequestMapping("/dashboard/users")
public class UserViewController {
    /**
     * Repository for accessing and managing users.
     */
    final UserRepository userRepository;

    /**
     * Constructs a new UserViewController with the required repository.
     *
     * @param userRepository Repository for users
     */
    public UserViewController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Handles requests to view the list of users.
     *
     * @param oAuth2User The authenticated user
     * @return ModelAndView for the users list page
     */
    @GetMapping
    public ModelAndView index(@AuthenticationPrincipal OAuth2User oAuth2User) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/users/list", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("users", userRepository.findAll());
        return modelAndView;
    }

    /**
     * Handles requests to view a specific user's profile.
     *
     * @param oAuth2User The authenticated user
     * @param id The ID of the user to view
     * @return ModelAndView for the user profile page
     * @throws java.util.NoSuchElementException if no user is found with the given ID
     */
    @GetMapping("show/{id}")
    public ModelAndView show(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/users/show", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("user", userRepository.findById(id).orElseThrow());
        return modelAndView;
    }
}
