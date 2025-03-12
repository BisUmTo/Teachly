package net.delugan.teachly.exercisegenerator;

import net.delugan.teachly.exercise.Exercise;
import net.delugan.teachly.exercise.ExerciseRepository;
import net.delugan.teachly.exercise.ExerciseRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Service class for generating exercises from exercise generators.
 * Provides methods for creating new exercises based on generator templates.
 */
@Transactional
@Service
public class ExerciseGeneratorService {
    /**
     * Repository for accessing and managing exercises.
     */
    private final ExerciseRepository exerciseRepository;

    /**
     * Constructs a new ExerciseGeneratorService with the required repository.
     *
     * @param exerciseRepository Repository for exercises
     */
    public ExerciseGeneratorService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    /**
     * Generates a new exercise from an exercise generator and request data.
     * The exercise is automatically numbered based on existing exercises from the same generator.
     *
     * @param exerciseGenerator The generator to use for creating the exercise
     * @param exerciseRequest The request containing exercise data
     * @return The newly created exercise
     */
    public Exercise generateExercise(ExerciseGenerator exerciseGenerator, ExerciseRequest exerciseRequest) {
        Exercise exercise;
        synchronized (this) {
            List<Exercise> exercises = exerciseRepository.findAllByGeneratorId(exerciseGenerator.getId());
            int number = exercises.size() + 1;
            exerciseGenerator.setLastGeneration(new Date());
            exercise = new Exercise(
                    exerciseGenerator.getName() + " #" + number,
                    exerciseRequest.getQuestion(),
                    exerciseRequest.getType(),
                    exerciseRequest.getDifficulty(),
                    exerciseGenerator.getTags().stream().toList(),
                    exerciseRequest.getHints(),
                    exerciseRequest.getSolutions()
            );
            exercise.setGenerator(exerciseGenerator);
            exercise.setAuthor(exerciseGenerator.getAuthor());
            exerciseRepository.save(exercise);
        }
        return exercise;
    }
}
