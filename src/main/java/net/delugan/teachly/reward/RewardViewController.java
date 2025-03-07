package net.delugan.teachly.reward;

import net.delugan.teachly.reward.RewardRepository;
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
@RequestMapping("/dashboard/rewards")
public class RewardViewController {
    private static final String DEFAULT_BLOCKLY_CODE = "{\"blocks\":{\"blocks\":[{\"type\":\"reward_procedure\",\"x\":0,\"y\":0}]}}" ;

    final RewardRepository rewardRepository;
    final UserRepository userRepository;

    public RewardViewController(RewardRepository rewardRepository, UserRepository userRepository) {
        this.rewardRepository = rewardRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ModelAndView index(@AuthenticationPrincipal OAuth2User oAuth2User) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/rewards/list", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("rewards", rewardRepository.findAll());
        return modelAndView;
    }

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

    @GetMapping("edit/{id}")
    public ModelAndView edit(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/rewards/edit", userRepository.getByOAuth2(oAuth2User));
        Reward original = rewardRepository.findById(id).orElseThrow();
        modelAndView.addObject("reward", original);
        modelAndView.addObject("blocklyCode", original.getBlocklyJsonCode());
        return modelAndView;
    }

    @GetMapping("show/{id}")
    public ModelAndView show(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        AuthenticatedModelAndView modelAndView = new AuthenticatedModelAndView("dashboard/rewards/show", userRepository.getByOAuth2(oAuth2User));
        modelAndView.addObject("reward", rewardRepository.findById(id).orElseThrow());
        return modelAndView;
    }

}
