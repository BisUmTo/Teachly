package net.delugan.teachly.utils;

import net.delugan.teachly.user.User;
import org.springframework.web.servlet.ModelAndView;


public class AuthenticatedModelAndView extends ModelAndView {
    public AuthenticatedModelAndView(String name, User user) {
        super(name);
        this.addObject("me", user);
    }
}
