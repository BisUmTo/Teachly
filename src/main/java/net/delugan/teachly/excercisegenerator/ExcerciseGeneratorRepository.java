package net.delugan.teachly.excercisegenerator;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExcerciseGeneratorRepository extends JpaRepository<ExcerciseGenerator, UUID> {

}
