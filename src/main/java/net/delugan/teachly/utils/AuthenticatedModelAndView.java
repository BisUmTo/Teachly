package net.delugan.teachly.utils;

import net.delugan.teachly.user.User;
import org.springframework.web.servlet.ModelAndView;

/**
 * Extension of ModelAndView that automatically adds the authenticated user to the model.
 * Simplifies controller methods by handling the common task of adding the current user.
 */
public class AuthenticatedModelAndView extends ModelAndView {
    /**
     * Creates a new AuthenticatedModelAndView with the given view name and user.
     * Automatically adds the user to the model with the key "me".
     *
     * @param name The view name
     * @param user The authenticated user
     */
    public AuthenticatedModelAndView(String name, User user) {
        super(name);
        this.addObject("me", user);
    }
}
