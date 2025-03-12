package net.delugan.teachly.exercise;

import net.delugan.teachly.user.User;
import net.delugan.teachly.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for exercise-related operations.
 * Provides endpoints for creating, retrieving, updating, and deleting exercises.
 */
@RestController
@RequestMapping("/api/v1/exercises")
class ExerciseRestController {
    /**
     * Repository for accessing and managing exercises.
     */
    public final ExerciseRepository exerciseRepository;
    
    /**
     * Repository for accessing and managing users.
     */
    private final UserRepository userRepository;

    /**
     * Constructs a new ExerciseRestController with the required repositories.
     *
     * @param exerciseRepository Repository for exercises
     * @param userRepository Repository for users
     */
    public ExerciseRestController(ExerciseRepository exerciseRepository, UserRepository userRepository) {
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all exercises.
     *
     * @return Iterable of all exercises
     */
    @GetMapping
    public Iterable<Exercise> getExercises() {
        return exerciseRepository.findAll();
    }

    /**
     * Retrieves an exercise by its ID.
     *
     * @param id The ID of the exercise to retrieve
     * @return The exercise with the specified ID
     * @throws java.util.NoSuchElementException if no exercise is found with the given ID
     */
    @GetMapping("{id}")
    public Exercise getExerciseById(@PathVariable UUID id) {
        return exerciseRepository.findById(id).orElseThrow();
    }

    /**
     * Creates a new exercise.
     *
     * @param oAuth2User The authenticated user
     * @param new_exercise The exercise data to create
     * @return The created exercise
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Exercise addExercise(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestBody Exercise new_exercise) {
        Exercise exercise = new Exercise(
                new_exercise.getName(),
                new_exercise.getQuestion(),
                new_exercise.getType(),
                new_exercise.getDifficulty(),
                new_exercise.getTags(),
                new_exercise.getHints(),
                new_exercise.getSolutions()
        );
        exercise.setAuthor(userRepository.getByOAuth2(oAuth2User));
        exercise.updateLastModified();
        exerciseRepository.save(exercise);
        return exercise;
    }

    /**
     * Updates an existing exercise.
     *
     * @param oAuth2User The authenticated user
     * @param id The ID of the exercise to update
     * @param new_exercise The updated exercise data
     * @return The updated exercise
     * @throws AuthorizationDeniedException if the authenticated user is not the author of the exercise
     */
    @PutMapping("{id}")
    public Exercise updateExercise(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id, @RequestBody Exercise new_exercise) {
        User user = userRepository.getByOAuth2(oAuth2User);
        new_exercise.setAuthor(user);
        Exercise exercise = exerciseRepository.findById(id).orElse(new_exercise);
        if(!exercise.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this exercise");
        }
        exercise.setName(new_exercise.getName());
        exercise.setDifficulty(new_exercise.getDifficulty());
        exercise.setType(new_exercise.getType());
        exercise.setGenerator(new_exercise.getGenerator());
        exercise.setTags(new_exercise.getTags());
        exercise.setHints(new_exercise.getHints());
        exercise.setSolutions(new_exercise.getSolutions());
        exercise.setQuestion(new_exercise.getQuestion());
        exercise.updateLastModified();
        exerciseRepository.save(exercise);
        return exercise;
    }

    /**
     * Deletes an exercise.
     *
     * @param oAuth2User The authenticated user
     * @param id The ID of the exercise to delete
     * @throws AuthorizationDeniedException if the authenticated user is not the author of the exercise
     * @throws java.util.NoSuchElementException if no exercise is found with the given ID
     */
    @DeleteMapping("{id}")
    public void deleteExercise(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow();
        if(!exercise.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this exercise");
        }
        exerciseRepository.deleteById(id);
    }

    /**
     * Retrieves all unique tags used in exercises.
     *
     * @return List of all unique tags
     */
    @GetMapping("/tags")
    public List<String> getTags() {
        return exerciseRepository.getAllTags();
    }
}

