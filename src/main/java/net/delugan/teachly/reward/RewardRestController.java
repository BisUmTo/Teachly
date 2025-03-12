package net.delugan.teachly.reward;

import net.delugan.teachly.user.User;
import net.delugan.teachly.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for reward-related operations.
 * Provides endpoints for creating, retrieving, updating, and deleting rewards.
 */
@RestController
@RequestMapping("/api/v1/rewards")
public class RewardRestController {
    /**
     * Repository for accessing and managing rewards.
     */
    public final RewardRepository rewardRepository;
    
    /**
     * Repository for accessing and managing users.
     */
    public final UserRepository userRepository;

    /**
     * Constructs a new RewardRestController with the required repositories.
     *
     * @param rewardRepository Repository for rewards
     * @param userRepository Repository for users
     */
    public RewardRestController(RewardRepository rewardRepository, UserRepository userRepository) {
        this.rewardRepository = rewardRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all rewards.
     *
     * @return Iterable of all rewards
     */
    @GetMapping
    public Iterable<Reward> getRewards() {
        return rewardRepository.findAll();
    }

    /**
     * Retrieves a reward by its ID.
     *
     * @param id The ID of the reward to retrieve
     * @return The reward with the specified ID
     * @throws java.util.NoSuchElementException if no reward is found with the given ID
     */
    @GetMapping("{id}")
    public Reward getRewardById(@PathVariable UUID id) {
        return rewardRepository.findById(id).orElseThrow();
    }

    /**
     * Creates a new reward.
     *
     * @param oAuth2User The authenticated user
     * @param new_reward The reward data to create
     * @return The created reward
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reward addReward(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestBody Reward new_reward) {
        Reward reward = new Reward(new_reward.getName(), new_reward.getDescription(), new_reward.getBlocklyJsonCode(), new_reward.getBlocklyGeneratedCode(), new_reward.getTags());
        reward.setAuthor(userRepository.getByOAuth2(oAuth2User));
        reward.updateLastModified();
        rewardRepository.save(reward);
        return reward;
    }

    /**
     * Updates an existing reward.
     *
     * @param oAuth2User The authenticated user
     * @param id The ID of the reward to update
     * @param new_reward The updated reward data
     * @return The updated reward
     * @throws AuthorizationDeniedException if the authenticated user is not the author of the reward
     */
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
        reward.setBlocklyGeneratedCode(new_reward.getBlocklyGeneratedCode());
        reward.setTags(new_reward.getTags());
        reward.updateLastModified();
        rewardRepository.save(reward);
        return reward;
    }

    /**
     * Deletes a reward.
     *
     * @param oAuth2User The authenticated user
     * @param id The ID of the reward to delete
     * @throws AuthorizationDeniedException if the authenticated user is not the author of the reward
     * @throws java.util.NoSuchElementException if no reward is found with the given ID
     */
    @DeleteMapping("{id}")
    public void deleteReward(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        Reward reward = rewardRepository.findById(id).orElseThrow();
        if(!reward.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this reward");
        }
        rewardRepository.deleteById(id);
    }

    /**
     * Retrieves all unique tags used in rewards.
     *
     * @return List of all unique tags
     */
    @GetMapping("/tags")
    public List<String> getTags() {
        return rewardRepository.getAllTags();
    }
}
