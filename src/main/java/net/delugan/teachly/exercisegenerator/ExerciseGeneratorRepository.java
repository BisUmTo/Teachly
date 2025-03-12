package net.delugan.teachly.exercisegenerator;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for ExerciseGenerator entities.
 * Provides methods for accessing and managing exercise generators in the database.
 */
public interface ExerciseGeneratorRepository extends JpaRepository<ExerciseGenerator, UUID> {
    /**
     * Retrieves all unique tags used across all exercise generators.
     *
     * @return A list of all unique tags
     */
    default List<String> getAllTags(){
        return findAll().stream().map(ExerciseGenerator::getTags).flatMap(List::stream).distinct().toList();
    }
}
