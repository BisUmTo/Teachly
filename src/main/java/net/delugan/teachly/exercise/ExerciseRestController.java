package net.delugan.teachly.exercise;

import net.delugan.teachly.user.User;
import net.delugan.teachly.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/exercises")
class ExerciseRestController {
    public final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    public ExerciseRestController(ExerciseRepository exerciseRepository, UserRepository userRepository) {
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Iterable<Exercise> getExercises() {
        return exerciseRepository.findAll();
    }

    @GetMapping("{id}")
    public Exercise getExerciseById(@PathVariable UUID id) {
        return exerciseRepository.findById(id).orElseThrow();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addExercise(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestBody Exercise new_exercise) {
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
        System.out.println(exercise.getAuthorId());
        exercise.updateLastModified();
        exerciseRepository.save(exercise);
    }

    @PutMapping("{id}")
    public void updateExercise(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id, @RequestBody Exercise new_exercise) {
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
    }

    @DeleteMapping("{id}")
    public void deleteExercise(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow();
        if(!exercise.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this exercise");
        }
        exerciseRepository.deleteById(id);
    }
}

