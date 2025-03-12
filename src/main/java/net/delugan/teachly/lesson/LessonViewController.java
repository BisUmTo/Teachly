package net.delugan.teachly.lesson;

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
 * Controller for lesson-related views.
 * Handles requests for viewing, creating, editing, and displaying lessons.
 */
@Controller
@RequestMapping("/dashboard/lessons")
public class LessonViewController {
    /**
     * Repository for accessing and managing lessons.
     */
    final LessonRepository lessonRepository;
    
    /**
     * Repository for accessing and managing users.
     */
    final UserRepository userRepository;

    /**
     * Constructs a new LessonViewController with the required repositories.
     *
     * @param lessonRepository Repository for lessons
     * @param userRepository Repository for users
     */
    public LessonViewController(LessonRepository lessonRepository, UserRepository userRepository) {
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
    }

    /**
     * Handles requests to view the list of lessons.
     *
     * @param oAuth2User The authenticated user
     * @return ModelAndView for the lessons list page
     */
    @GetMapping
    public ModelAndView index(@AuthenticationPrincipal OAuth2User oAuth2User) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/lessons/list", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("lessons", lessonRepository.findAll());
        return modelAndView;
    }

    /**
     * Handles requests to create a new lesson.
     * Optionally clones an existing lesson if an ID is provided.
     *
     * @param oAuth2User The authenticated user
     * @param clone_id Optional ID of a lesson to clone
     * @return ModelAndView for the lesson creation page
     */
    @GetMapping("create")
    public ModelAndView create(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestParam(value="id", required = false) UUID clone_id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/lessons/create", userRepository.getByOAuth2(oAuth2User));
        if (clone_id != null) {
            Lesson clone = lessonRepository.findById(clone_id).orElseThrow();
            modelAndView.addObject("lesson", clone);
        }
        return modelAndView;
    }

    /**
     * Handles requests to edit an existing lesson.
     *
     * @param oAuth2User The authenticated user
     * @param id The ID of the lesson to edit
     * @return ModelAndView for the lesson edit page
     * @throws java.util.NoSuchElementException if no lesson is found with the given ID
     */
    @GetMapping("edit/{id}")
    public ModelAndView edit(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/lessons/edit", userRepository.getByOAuth2(oAuth2User));
        Lesson original = lessonRepository.findById(id).orElseThrow();
        modelAndView.addObject("lesson", original);
        return modelAndView;
    }

    /**
     * Handles requests to view details of a specific lesson.
     *
     * @param oAuth2User The authenticated user
     * @param id The ID of the lesson to view
     * @return ModelAndView for the lesson details page
     * @throws java.util.NoSuchElementException if no lesson is found with the given ID
     */
    @GetMapping("show/{id}")
    public ModelAndView show(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/lessons/show", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("lesson", lessonRepository.findById(id).orElseThrow());
        return modelAndView;
    }
}
