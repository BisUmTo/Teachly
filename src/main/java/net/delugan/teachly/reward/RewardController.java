package net.delugan.teachly.reward;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rewards")
public class RewardController {
    public final RewardRepository rewardRepository;

    public RewardController(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
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
    public void addReward(@RequestBody Reward new_reward) {
        Reward reward = new Reward(
                new_reward.getName(),
                new_reward.getDescription(),
                new_reward.getBlocklyJsonCode()
        );
        reward.setAuthor(null); // TODO: author
        rewardRepository.save(reward);
    }

    @PutMapping("{id}")
    public void updateReward(@PathVariable UUID id, @RequestBody Reward new_reward) {
        new_reward.setAuthor(null); // TODO: author
        Reward reward = rewardRepository.findById(id).orElse(new_reward);
        reward.setName(new_reward.getName());
        reward.setDescription(new_reward.getDescription());
        reward.setBlocklyJsonCode(new_reward.getBlocklyJsonCode());
        rewardRepository.save(reward);
    }

    @DeleteMapping("{id}")
    public void deleteReward(@PathVariable UUID id) {
        rewardRepository.deleteById(id);
    }
}
