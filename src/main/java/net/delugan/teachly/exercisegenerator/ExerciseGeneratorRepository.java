package net.delugan.teachly.exercisegenerator;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExerciseGeneratorRepository extends JpaRepository<ExerciseGenerator, UUID> {

}
