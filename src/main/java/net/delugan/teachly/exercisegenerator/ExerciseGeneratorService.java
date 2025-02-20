package net.delugan.teachly.exercisegenerator;

import net.delugan.teachly.exercise.Exercise;
import net.delugan.teachly.exercise.ExerciseRepository;
import net.delugan.teachly.exercise.ExerciseRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Service
public class ExerciseGeneratorService {
    private final ExerciseRepository exerciseRepository;

    public ExerciseGeneratorService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

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
