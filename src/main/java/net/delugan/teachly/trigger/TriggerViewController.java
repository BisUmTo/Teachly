package net.delugan.teachly.trigger;

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
@RequestMapping("/dashboard/triggers")
public class TriggerViewController {
    final TriggerRepository triggerRepository;
    final UserRepository userRepository;

    public TriggerViewController(TriggerRepository triggerRepository, UserRepository userRepository) {
        this.triggerRepository = triggerRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ModelAndView index(@AuthenticationPrincipal OAuth2User oAuth2User) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/triggers/list", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("triggers", triggerRepository.findAll());
        return modelAndView;
    }

    @GetMapping("create")
    public ModelAndView create(@AuthenticationPrincipal OAuth2User oAuth2User) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/triggers/create", userRepository.getByOAuth2(oAuth2User));
        return modelAndView;
    }

    @GetMapping("edit/{id}")
    public ModelAndView edit(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/triggers/edit", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("trigger", triggerRepository.findById(id).orElseThrow());
        return modelAndView;
    }

    @GetMapping("show/{id}")
    public ModelAndView show(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/triggers/show", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("trigger", triggerRepository.findById(id).orElseThrow());
        return modelAndView;
    }

}
