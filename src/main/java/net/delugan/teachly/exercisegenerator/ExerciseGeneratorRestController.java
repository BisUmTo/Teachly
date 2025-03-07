package net.delugan.teachly.exercisegenerator;

import net.delugan.teachly.exercise.Exercise;
import net.delugan.teachly.exercise.ExerciseRepository;
import net.delugan.teachly.exercise.ExerciseRequest;
import net.delugan.teachly.lesson.LessonRequest;
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

@RestController
@RequestMapping("/api/v1/exercises/generators")
class ExerciseGeneratorRestController {
    public final ExerciseGeneratorRepository exerciseGeneratorRepository;
    public final ExerciseRepository exerciseRepository;
    public final UserRepository userRepository;
    public final ExerciseGeneratorService exerciseGeneratorService;

    public ExerciseGeneratorRestController(ExerciseGeneratorRepository exerciseGeneratorRepository, ExerciseRepository exerciseRepository, UserRepository userRepository, ExerciseGeneratorService exerciseGeneratorService) {
        this.exerciseGeneratorRepository = exerciseGeneratorRepository;
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
        this.exerciseGeneratorService = exerciseGeneratorService;
    }

    @GetMapping
    public Iterable<ExerciseGenerator> getExerciseGenerators() {
        return exerciseGeneratorRepository.findAll();
    }

    @GetMapping("{id}")
    public ExerciseGenerator getExerciseGeneratorById(@PathVariable UUID id) {
        return exerciseGeneratorRepository.findById(id).orElseThrow();
    }

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

    @DeleteMapping("{id}")
    public void deleteExerciseGenerator(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        ExerciseGenerator exerciseGenerator = exerciseGeneratorRepository.findById(id).orElseThrow();
        if(!exerciseGenerator.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this exercise generator");
        }
        exerciseGeneratorRepository.deleteById(id);
    }

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

    @GetMapping("{id}/generated")
    public List<Exercise> getGeneratedExercises(@PathVariable UUID id) {
        return exerciseRepository.findAllByGeneratorId(id);
    }

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

    @GetMapping("/tags")
    public List<String> getTags() {
        return exerciseGeneratorRepository.getAllTags();
    }
}
