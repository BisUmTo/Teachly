package net.delugan.teachly.trigger;

import net.delugan.teachly.exercise.Exercise;
import net.delugan.teachly.reward.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TriggerRepository extends JpaRepository<Trigger, UUID> {
    default List<String> getAllTags(){
        return findAll().stream().map(Trigger::getTags).flatMap(List::stream).distinct().toList();
    }
}
