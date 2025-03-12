package net.delugan.teachly.trigger;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Trigger entities.
 * Provides methods for accessing and managing triggers in the database.
 */
public interface TriggerRepository extends JpaRepository<Trigger, UUID> {
    /**
     * Retrieves all unique tags used across all triggers.
     * 
     * @return A list of all unique tags
     */
    default List<String> getAllTags(){
        return findAll().stream().map(Trigger::getTags).flatMap(List::stream).distinct().toList();
    }
}
