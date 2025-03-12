package net.delugan.teachly.reward;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Reward entities.
 * Provides methods for accessing and managing rewards in the database.
 */
public interface RewardRepository extends JpaRepository<Reward, UUID> {
    /**
     * Retrieves all unique tags used across all rewards.
     *
     * @return A list of all unique tags
     */
    default List<String> getAllTags(){
        return findAll().stream().map(Reward::getTags).flatMap(List::stream).distinct().toList();
    }
}
