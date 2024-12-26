package net.delugan.teachly.excercise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface ExcerciseRepository extends JpaRepository<Excercise, UUID> {
    List<Excercise> findAllByGeneratorId(UUID generatorId);
}
