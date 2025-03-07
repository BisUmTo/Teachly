package net.delugan.teachly.exercisegenerator;

import net.delugan.teachly.exercise.ExerciseRepository;
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
@RequestMapping("/dashboard/exercises/generators")
public class ExerciseGeneratorViewController {
    private static final String DEFAULT_BLOCKLY_CODE = "{}" ;

    final ExerciseGeneratorRepository exerciseGeneratorRepository;
    final ExerciseRepository exerciseRepository;
    final UserRepository userRepository;

    public ExerciseGeneratorViewController(ExerciseGeneratorRepository exerciseGeneratorRepository, ExerciseRepository exerciseRepository, UserRepository userRepository) {
        this.exerciseGeneratorRepository = exerciseGeneratorRepository;
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ModelAndView index(@AuthenticationPrincipal OAuth2User oAuth2User) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/exercises/generators/list", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("generators", exerciseGeneratorRepository.findAll());
        return modelAndView;
    }

    @GetMapping("create")
    public ModelAndView create(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestParam(value="id", required = false) UUID clone_id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/exercises/generators/create", userRepository.getByOAuth2(oAuth2User));
        if (clone_id != null) {
            ExerciseGenerator clone = exerciseGeneratorRepository.findById(clone_id).orElseThrow();
            modelAndView.addObject("generator", clone);
            modelAndView.addObject("blocklyCode", clone.getBlocklyJsonCode());
        } else {
            modelAndView.addObject("blocklyCode", DEFAULT_BLOCKLY_CODE);
        }
        return modelAndView;
    }

    @GetMapping("edit/{id}")
    public ModelAndView edit(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/exercises/generators/edit", userRepository.getByOAuth2(oAuth2User));
        ExerciseGenerator original = exerciseGeneratorRepository.findById(id).orElseThrow();
        modelAndView.addObject("generator", original);
        modelAndView.addObject("blocklyCode", original.getBlocklyJsonCode());
        return modelAndView;
    }

    @GetMapping("show/{id}")
    public ModelAndView show(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/exercises/generators/show", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("generator", exerciseGeneratorRepository.findById(id).orElseThrow());
        modelAndView.addObject("generatedExercises", exerciseRepository.findAllByGeneratorIdOrderByName(id));
        return modelAndView;
    }

}
