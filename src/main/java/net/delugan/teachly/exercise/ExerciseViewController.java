package net.delugan.teachly.exercise;

import net.delugan.teachly.user.UserRepository;
import net.delugan.teachly.utils.AuthenticatedModelAndView;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

/**
 * Controller for exercise-related views.
 * Handles requests for viewing, creating, editing, and displaying exercises.
 */
@Controller
@RequestMapping("/dashboard/exercises")
public class ExerciseViewController {
    /**
     * Repository for accessing and managing exercises.
     */
    final ExerciseRepository exerciseRepository;
    
    /**
     * Repository for accessing and managing users.
     */
    final UserRepository userRepository;

    /**
     * Constructs a new ExerciseViewController with the required repositories.
     *
     * @param exerciseRepository Repository for exercises
     * @param userRepository Repository for users
     */
    public ExerciseViewController(ExerciseRepository exerciseRepository, UserRepository userRepository) {
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
    }

    /**
     * Handles requests to view the list of exercises.
     *
     * @param oAuth2User The authenticated user
     * @return ModelAndView for the exercises list page
     */
    @GetMapping
    public ModelAndView index(@AuthenticationPrincipal OAuth2User oAuth2User) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/exercises/list", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("exercises", exerciseRepository.findAll());
        return modelAndView;
    }

    /**
     * Handles requests to create a new exercise.
     * Optionally clones an existing exercise if an ID is provided.
     *
     * @param oAuth2User The authenticated user
     * @param clone_id Optional ID of an exercise to clone
     * @return ModelAndView for the exercise creation page
     */
    @GetMapping("create")
    public ModelAndView create(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestParam(value="id", required = false) UUID clone_id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/exercises/create", userRepository.getByOAuth2(oAuth2User));
        if (clone_id != null) {
            Exercise clone = exerciseRepository.findById(clone_id).orElseThrow();
            modelAndView.addObject("exercise", clone);
        }
        return modelAndView;
    }

    /**
     * Handles requests to edit an existing exercise.
     *
     * @param oAuth2User The authenticated user
     * @param id The ID of the exercise to edit
     * @return ModelAndView for the exercise edit page
     * @throws java.util.NoSuchElementException if no exercise is found with the given ID
     */
    @GetMapping("edit/{id}")
    public ModelAndView edit(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/exercises/edit", userRepository.getByOAuth2(oAuth2User));
        Exercise original = exerciseRepository.findById(id).orElseThrow();
        modelAndView.addObject("exercise", original);
        return modelAndView;
    }

    /**
     * Handles requests to view details of a specific exercise.
     *
     * @param oAuth2User The authenticated user
     * @param id The ID of the exercise to view
     * @return ModelAndView for the exercise details page
     * @throws java.util.NoSuchElementException if no exercise is found with the given ID
     */
    @GetMapping("show/{id}")
    public ModelAndView show(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/exercises/show", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("exercise", exerciseRepository.findById(id).orElseThrow());
        return modelAndView;
    }
}
