package net.delugan.teachly.trigger;

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
 * Controller for trigger-related views.
 * Handles requests for viewing, creating, editing, and displaying triggers.
 */
@Controller
@RequestMapping("/dashboard/triggers")
public class TriggerViewController {
    /**
     * Default Blockly code for new triggers.
     */
    private static final String DEFAULT_BLOCKLY_CODE = "{\"blocks\":{\"blocks\":[{\"type\":\"assign_random_exercise\",\"x\":0,\"y\":0}]}}" ;
    
    /**
     * Repository for accessing and managing triggers.
     */
    final TriggerRepository triggerRepository;
    
    /**
     * Repository for accessing and managing users.
     */
    final UserRepository userRepository;

    /**
     * Constructs a new TriggerViewController with the required repositories.
     *
     * @param triggerRepository Repository for triggers
     * @param userRepository Repository for users
     */
    public TriggerViewController(TriggerRepository triggerRepository, UserRepository userRepository) {
        this.triggerRepository = triggerRepository;
        this.userRepository = userRepository;
    }

    /**
     * Handles requests to view the list of triggers.
     *
     * @param oAuth2User The authenticated user
     * @return ModelAndView for the triggers list page
     */
    @GetMapping
    public ModelAndView index(@AuthenticationPrincipal OAuth2User oAuth2User) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/triggers/list", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("triggers", triggerRepository.findAll());
        return modelAndView;
    }

    /**
     * Handles requests to create a new trigger.
     * Optionally clones an existing trigger if an ID is provided.
     *
     * @param oAuth2User The authenticated user
     * @param clone_id Optional ID of a trigger to clone
     * @return ModelAndView for the trigger creation page
     */
    @GetMapping("create")
    public ModelAndView create(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestParam(value="id", required = false) UUID clone_id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/triggers/create", userRepository.getByOAuth2(oAuth2User));
        if (clone_id != null) {
            Trigger clone = triggerRepository.findById(clone_id).orElseThrow();
            modelAndView.addObject("trigger", clone);
            modelAndView.addObject("blocklyCode", clone.getBlocklyJsonCode());
        } else {
            modelAndView.addObject("blocklyCode", DEFAULT_BLOCKLY_CODE);
        }
        return modelAndView;
    }

    /**
     * Handles requests to edit an existing trigger.
     *
     * @param oAuth2User The authenticated user
     * @param id The ID of the trigger to edit
     * @return ModelAndView for the trigger edit page
     */
    @GetMapping("edit/{id}")
    public ModelAndView edit(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/triggers/edit", userRepository.getByOAuth2(oAuth2User));
        Trigger original = triggerRepository.findById(id).orElseThrow();
        modelAndView.addObject("trigger", original);
        modelAndView.addObject("blocklyCode", original.getBlocklyJsonCode());
        return modelAndView;
    }

    /**
     * Handles requests to view details of a specific trigger.
     *
     * @param oAuth2User The authenticated user
     * @param id The ID of the trigger to view
     * @return ModelAndView for the trigger details page
     */
    @GetMapping("show/{id}")
    public ModelAndView show(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/triggers/show", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("trigger", triggerRepository.findById(id).orElseThrow());
        return modelAndView;
    }
}
