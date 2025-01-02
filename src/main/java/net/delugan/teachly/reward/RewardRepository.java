package net.delugan.teachly.reward;

import net.delugan.teachly.exercise.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RewardRepository extends JpaRepository<Reward, UUID> {
    default List<String> getAllTags(){
        return findAll().stream().map(Reward::getTags).flatMap(List::stream).distinct().toList();
    }
}
