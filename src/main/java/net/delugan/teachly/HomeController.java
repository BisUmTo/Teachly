package net.delugan.teachly;

import net.delugan.teachly.user.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    UserRepository userRepository;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public ModelAndView index(@AuthenticationPrincipal OAuth2User oAuth2User) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("userCount", userRepository.count());
        modelAndView.addObject("me", userRepository.getByOAuth2(oAuth2User));
        return modelAndView;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/blockly")
    public String blockly() {
        return "blockly";
    }


}
