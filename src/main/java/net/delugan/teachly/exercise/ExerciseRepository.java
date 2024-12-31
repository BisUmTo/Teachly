package net.delugan.teachly.exercise;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {
    List<Exercise> findAllByGeneratorId(UUID generatorId);
    default List<String> getAllTags(){
        return findAll().stream().map(Exercise::getTags).flatMap(List::stream).distinct().toList();
    }
}
