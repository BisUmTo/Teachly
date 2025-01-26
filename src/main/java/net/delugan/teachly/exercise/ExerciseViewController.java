package net.delugan.teachly.exercise;

import net.delugan.teachly.reward.Reward;
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

@Controller
@RequestMapping("/dashboard/exercises")
public class ExerciseViewController {
    final ExerciseRepository exerciseRepository;
    final UserRepository userRepository;

    public ExerciseViewController(ExerciseRepository exerciseRepository, UserRepository userRepository) {
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ModelAndView index(@AuthenticationPrincipal OAuth2User oAuth2User) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/exercises/list", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("exercises", exerciseRepository.findAll());
        return modelAndView;
    }

    @GetMapping("create")
    public ModelAndView create(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestParam(value="id", required = false) UUID clone_id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/exercises/create", userRepository.getByOAuth2(oAuth2User));
        if (clone_id != null) {
            Exercise clone = exerciseRepository.findById(clone_id).orElseThrow();
            modelAndView.addObject("exercise", clone);
        }
        return modelAndView;
    }

    @GetMapping("edit/{id}")
    public ModelAndView edit(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/exercises/edit", userRepository.getByOAuth2(oAuth2User));
        Exercise original = exerciseRepository.findById(id).orElseThrow();
        modelAndView.addObject("exercise", original);
        return modelAndView;
    }

    @GetMapping("show/{id}")
    public ModelAndView show(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/exercises/show", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("exercise", exerciseRepository.findById(id).orElseThrow());
        return modelAndView;
    }

}
