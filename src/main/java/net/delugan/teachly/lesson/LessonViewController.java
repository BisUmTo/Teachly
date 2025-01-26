package net.delugan.teachly.lesson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.delugan.teachly.lesson.LessonRepository;
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
@RequestMapping("/dashboard/lessons")
public class LessonViewController {
    final LessonRepository lessonRepository;
    final UserRepository userRepository;

    public LessonViewController(LessonRepository lessonRepository, UserRepository userRepository) {
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ModelAndView index(@AuthenticationPrincipal OAuth2User oAuth2User) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/lessons/list", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("lessons", lessonRepository.findAll());
        return modelAndView;
    }

    @GetMapping("create")
    public ModelAndView create(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestParam(value="id", required = false) UUID clone_id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/lessons/create", userRepository.getByOAuth2(oAuth2User));
        if (clone_id != null) {
            Lesson clone = lessonRepository.findById(clone_id).orElseThrow();
            modelAndView.addObject("lesson", clone);
        }
        return modelAndView;
    }

    @GetMapping("edit/{id}")
    public ModelAndView edit(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/lessons/edit", userRepository.getByOAuth2(oAuth2User));
        Lesson original = lessonRepository.findById(id).orElseThrow();
        modelAndView.addObject("lesson", original);
        return modelAndView;
    }

    @GetMapping("show/{id}")
    public ModelAndView show(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/lessons/show", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("lesson", lessonRepository.findById(id).orElseThrow());
        return modelAndView;
    }

}
