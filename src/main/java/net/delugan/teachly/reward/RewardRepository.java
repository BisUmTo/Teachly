package net.delugan.teachly.reward;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RewardRepository extends JpaRepository<Reward, UUID> {

}
