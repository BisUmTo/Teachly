package net.delugan.teachly.exercisegenerator;

import net.delugan.teachly.exercise.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExerciseGeneratorRepository extends JpaRepository<ExerciseGenerator, UUID> {
    default List<String> getAllTags() {
        return findAll().stream().map(ExerciseGenerator::getTags).flatMap(List::stream).distinct().toList();
    }
}
