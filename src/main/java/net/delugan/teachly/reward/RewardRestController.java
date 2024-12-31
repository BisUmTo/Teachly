package net.delugan.teachly.reward;

import net.delugan.teachly.user.User;
import net.delugan.teachly.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rewards")
public class RewardRestController {
    public final RewardRepository rewardRepository;
    public final UserRepository userRepository;

    public RewardRestController(RewardRepository rewardRepository, UserRepository userRepository) {
        this.rewardRepository = rewardRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Iterable<Reward> getRewards() {
        return rewardRepository.findAll();
    }

    @GetMapping("{id}")
    public Reward getRewardById(@PathVariable UUID id) {
        return rewardRepository.findById(id).orElseThrow();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reward addReward(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestBody Reward new_reward) {
        Reward reward = new Reward(new_reward.getName(), new_reward.getDescription(), new_reward.getBlocklyJsonCode());
        reward.setAuthor(userRepository.getByOAuth2(oAuth2User));
        reward.updateLastModified();
        rewardRepository.save(reward);
        return reward;
    }

    @PutMapping("{id}")
    public Reward updateReward(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id, @RequestBody Reward new_reward) {
        User user = userRepository.getByOAuth2(oAuth2User);
        new_reward.setAuthor(user);
        Reward reward = rewardRepository.findById(id).orElse(new_reward);
        if(!reward.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this reward");
        }
        reward.setName(new_reward.getName());
        reward.setDescription(new_reward.getDescription());
        reward.setBlocklyJsonCode(new_reward.getBlocklyJsonCode());
        reward.updateLastModified();
        rewardRepository.save(reward);
        return reward;
    }

    @DeleteMapping("{id}")
    public void deleteReward(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        Reward reward = rewardRepository.findById(id).orElseThrow();
        if(!reward.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this reward");
        }
        rewardRepository.deleteById(id);
    }
}
