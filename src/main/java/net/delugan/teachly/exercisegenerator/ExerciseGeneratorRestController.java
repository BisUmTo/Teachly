package net.delugan.teachly.exercisegenerator;

import net.delugan.teachly.exercise.Exercise;
import net.delugan.teachly.exercise.ExerciseRepository;
import net.delugan.teachly.exercise.ExerciseRequest;
import net.delugan.teachly.user.User;
import net.delugan.teachly.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for exercise generator-related operations.
 * Provides endpoints for creating, retrieving, updating, and deleting exercise generators,
 * as well as generating exercises from them.
 */
@RestController
@RequestMapping("/api/v1/exercises/generators")
class ExerciseGeneratorRestController {
    /**
     * Repository for accessing and managing exercise generators.
     */
    public final ExerciseGeneratorRepository exerciseGeneratorRepository;
    
    /**
     * Repository for accessing and managing exercises.
     */
    public final ExerciseRepository exerciseRepository;
    
    /**
     * Repository for accessing and managing users.
     */
    public final UserRepository userRepository;
    
    /**
     * Service for generating exercises from generators.
     */
    public final ExerciseGeneratorService exerciseGeneratorService;

    /**
     * Constructs a new ExerciseGeneratorRestController with the required repositories and services.
     *
     * @param exerciseGeneratorRepository Repository for exercise generators
     * @param exerciseRepository Repository for exercises
     * @param userRepository Repository for users
     * @param exerciseGeneratorService Service for generating exercises
     */
    public ExerciseGeneratorRestController(ExerciseGeneratorRepository exerciseGeneratorRepository, ExerciseRepository exerciseRepository, UserRepository userRepository, ExerciseGeneratorService exerciseGeneratorService) {
        this.exerciseGeneratorRepository = exerciseGeneratorRepository;
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
        this.exerciseGeneratorService = exerciseGeneratorService;
    }

    /**
     * Retrieves all exercise generators.
     *
     * @return Iterable of all exercise generators
     */
    @GetMapping
    public Iterable<ExerciseGenerator> getExerciseGenerators() {
        return exerciseGeneratorRepository.findAll();
    }

    /**
     * Retrieves an exercise generator by its ID.
     *
     * @param id The ID of the generator to retrieve
     * @return The generator with the specified ID
     * @throws java.util.NoSuchElementException if no generator is found with the given ID
     */
    @GetMapping("{id}")
    public ExerciseGenerator getExerciseGeneratorById(@PathVariable UUID id) {
        return exerciseGeneratorRepository.findById(id).orElseThrow();
    }

    /**
     * Creates a new exercise generator.
     *
     * @param oAuth2User The authenticated user
     * @param new_exerciseGenerator The generator data to create
     * @return The created generator
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExerciseGenerator addExerciseGenerator(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestBody ExerciseGenerator new_exerciseGenerator) {
        ExerciseGenerator exerciseGenerator = new ExerciseGenerator(
                new_exerciseGenerator.getName(),
                new_exerciseGenerator.getDescription(),
                new_exerciseGenerator.getTags(),
                new_exerciseGenerator.getBlocklyJsonCode()
        );
        exerciseGenerator.setAuthor(userRepository.getByOAuth2(oAuth2User));
        exerciseGenerator.updateLastModified();
        exerciseGeneratorRepository.save(exerciseGenerator);
        return exerciseGenerator;
    }

    /**
     * Updates an existing exercise generator.
     *
     * @param oAuth2User The authenticated user
     * @param id The ID of the generator to update
     * @param new_exerciseGenerator The updated generator data
     * @return The updated generator
     * @throws AuthorizationDeniedException if the authenticated user is not the author of the generator
     */
    @PutMapping("{id}")
    public ExerciseGenerator updateExerciseGenerator(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id, @RequestBody ExerciseGenerator new_exerciseGenerator) {
        User user = userRepository.getByOAuth2(oAuth2User);
        new_exerciseGenerator.setAuthor(user);
        ExerciseGenerator exerciseGenerator = exerciseGeneratorRepository.findById(id).orElse(new_exerciseGenerator);
        if(!exerciseGenerator.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this exercise generator");
        }
        exerciseGenerator.setName(new_exerciseGenerator.getName());
        exerciseGenerator.setDescription(new_exerciseGenerator.getDescription());
        exerciseGenerator.setBlocklyJsonCode(new_exerciseGenerator.getBlocklyJsonCode());
        exerciseGenerator.updateLastModified();
        exerciseGeneratorRepository.save(exerciseGenerator);
        return exerciseGenerator;
    }

    /**
     * Deletes an exercise generator.
     *
     * @param oAuth2User The authenticated user
     * @param id The ID of the generator to delete
     * @throws AuthorizationDeniedException if the authenticated user is not the author of the generator
     * @throws java.util.NoSuchElementException if no generator is found with the given ID
     */
    @DeleteMapping("{id}")
    public void deleteExerciseGenerator(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        ExerciseGenerator exerciseGenerator = exerciseGeneratorRepository.findById(id).orElseThrow();
        if(!exerciseGenerator.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this exercise generator");
        }
        exerciseGeneratorRepository.deleteById(id);
    }

    /**
     * Generates a new exercise using the specified generator.
     *
     * @param oAuth2User The authenticated user
     * @param id The ID of the generator to use
     * @param exerciseRequest The request containing exercise data
     * @return The newly generated exercise
     * @throws AuthorizationDeniedException if the authenticated user is not the author of the generator
     * @throws java.util.NoSuchElementException if no generator is found with the given ID
     */
    @Transactional
    @PostMapping("{id}/generate")
    public Exercise generateExercise(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id, @RequestBody ExerciseRequest exerciseRequest) {
        User user = userRepository.getByOAuth2(oAuth2User);
        ExerciseGenerator exerciseGenerator = exerciseGeneratorRepository.findById(id).orElseThrow();
        if(!exerciseGenerator.isAuthor(user)) {
            throw new AuthorizationDeniedException("You are not the author of this exercise generator");
        }
        return exerciseGeneratorService.generateExercise(exerciseGenerator, exerciseRequest);
    }

    /**
     * Retrieves all exercises created by a specific generator.
     *
     * @param id The ID of the generator
     * @return A list of exercises created by the specified generator
     */
    @GetMapping("{id}/generated")
    public List<Exercise> getGeneratedExercises(@PathVariable UUID id) {
        return exerciseRepository.findAllByGeneratorId(id);
    }

    /**
     * Deletes all exercises created by a specific generator.
     *
     * @param oAuth2User The authenticated user
     * @param id The ID of the generator
     * @return The number of exercises deleted
     * @throws AuthorizationDeniedException if the authenticated user is not the author of the generator
     * @throws java.util.NoSuchElementException if no generator is found with the given ID
     */
    @DeleteMapping("{id}/generated")
    public int deleteGeneratedExercises(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        User user = userRepository.getByOAuth2(oAuth2User);
        ExerciseGenerator exerciseGenerator = exerciseGeneratorRepository.findById(id).orElseThrow();
        if(!exerciseGenerator.isAuthor(user)) {
            throw new AuthorizationDeniedException("You are not the author of this exercise generator");
        }
        List<Exercise> exercises = exerciseRepository.findAllByGeneratorId(id).stream()
                .filter(exercise -> exercise.isAuthor(user)).toList();
        exerciseRepository.deleteAll(exercises);
        return exercises.size();
    }

    /**
     * Retrieves all unique tags used across all exercise generators.
     *
     * @return A list of all unique tags
     */
    @GetMapping("/tags")
    public List<String> getTags() {
        return exerciseGeneratorRepository.getAllTags();
    }
}
