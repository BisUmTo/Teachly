package net.delugan.teachly.excercise;

import net.delugan.teachly.user.UserRepository;
import net.delugan.teachly.utils.AuthenticatedModelAndView;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/dashboard/excercises")
public class ExcerciseViewController {
    final ExcerciseRepository excerciseRepository;
    final UserRepository userRepository;

    public ExcerciseViewController(ExcerciseRepository excerciseRepository, UserRepository userRepository) {
        this.excerciseRepository = excerciseRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ModelAndView index(@AuthenticationPrincipal OAuth2User oAuth2User) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/excercises/list", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("excercises", excerciseRepository.findAll());
        return modelAndView;
    }

    @GetMapping("create")
    public ModelAndView create(@AuthenticationPrincipal OAuth2User oAuth2User) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/excercises/create", userRepository.getByOAuth2(oAuth2User));
        return modelAndView;
    }

    @GetMapping("edit/{id}")
    public ModelAndView edit(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/excercises/edit", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("excercise", excerciseRepository.findById(id).orElseThrow());
        return modelAndView;
    }

    @GetMapping("show/{id}")
    public ModelAndView show(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/excercises/show", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("excercise", excerciseRepository.findById(id).orElseThrow());
        return modelAndView;
    }

}
