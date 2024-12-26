package net.delugan.teachly;

import net.delugan.teachly.trigger.Trigger;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {
    @GetMapping("/")
    public ModelAndView index() {
        List<Trigger> triggers = List.of(
                new Trigger("Trigger 1", "Description 1", "Blockly JSON Code 1"),
                new Trigger("Trigger 2", "Description 2", "Blockly JSON Code 2"),
                new Trigger("Trigger 3", "Description 3", "Blockly JSON Code 3")
        );

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("triggers", triggers);

        return modelAndView;
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {
        model.addAttribute("name", principal.getAttribute("name"));
        model.addAttribute("email", principal.getAttribute("email"));
        model.addAttribute("picture", principal.getAttribute("picture"));
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
