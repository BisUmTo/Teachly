package net.delugan.teachly.reward;

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
 * Controller for reward-related views.
 * Handles requests for viewing, creating, editing, and displaying rewards.
 */
@Controller
@RequestMapping("/dashboard/rewards")
public class RewardViewController {
    /**
     * Default Blockly code for new rewards.
     */
    private static final String DEFAULT_BLOCKLY_CODE = "{\"blocks\":{\"blocks\":[{\"type\":\"reward_procedure\",\"x\":0,\"y\":0}]}}" ;

    /**
     * Repository for accessing and managing rewards.
     */
    final RewardRepository rewardRepository;
    
    /**
     * Repository for accessing and managing users.
     */
    final UserRepository userRepository;

    /**
     * Constructs a new RewardViewController with the required repositories.
     *
     * @param rewardRepository Repository for rewards
     * @param userRepository Repository for users
     */
    public RewardViewController(RewardRepository rewardRepository, UserRepository userRepository) {
        this.rewardRepository = rewardRepository;
        this.userRepository = userRepository;
    }

    /**
     * Handles requests to view the list of rewards.
     *
     * @param oAuth2User The authenticated user
     * @return ModelAndView for the rewards list page
     */
    @GetMapping
    public ModelAndView index(@AuthenticationPrincipal OAuth2User oAuth2User) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/rewards/list", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("rewards", rewardRepository.findAll());
        return modelAndView;
    }

    /**
     * Handles requests to create a new reward.
     * Optionally clones an existing reward if an ID is provided.
     *
     * @param oAuth2User The authenticated user
     * @param clone_id Optional ID of a reward to clone
     * @return ModelAndView for the reward creation page
     */
    @GetMapping("create")
    public ModelAndView create(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestParam(value="id", required = false) UUID clone_id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/rewards/create", userRepository.getByOAuth2(oAuth2User));
        if (clone_id != null) {
            Reward clone = rewardRepository.findById(clone_id).orElseThrow();
            modelAndView.addObject("reward", clone);
            modelAndView.addObject("blocklyCode", clone.getBlocklyJsonCode());
        } else {
            modelAndView.addObject("blocklyCode", DEFAULT_BLOCKLY_CODE);
        }
        return modelAndView;
    }

    /**
     * Handles requests to edit an existing reward.
     *
     * @param oAuth2User The authenticated user
     * @param id The ID of the reward to edit
     * @return ModelAndView for the reward edit page
     * @throws java.util.NoSuchElementException if no reward is found with the given ID
     */
    @GetMapping("edit/{id}")
    public ModelAndView edit(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/rewards/edit", userRepository.getByOAuth2(oAuth2User));
        Reward original = rewardRepository.findById(id).orElseThrow();
        modelAndView.addObject("reward", original);
        modelAndView.addObject("blocklyCode", original.getBlocklyJsonCode());
        return modelAndView;
    }

    /**
     * Handles requests to view details of a specific reward.
     *
     * @param oAuth2User The authenticated user
     * @param id The ID of the reward to view
     * @return ModelAndView for the reward details page
     * @throws java.util.NoSuchElementException if no reward is found with the given ID
     */
    @GetMapping("show/{id}")
    public ModelAndView show(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/rewards/show", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("reward", rewardRepository.findById(id).orElseThrow());
        return modelAndView;
    }
}
