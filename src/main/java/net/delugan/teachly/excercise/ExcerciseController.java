package net.delugan.teachly.excercise;

import net.delugan.teachly.user.User;
import net.delugan.teachly.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/excercises")
class ExcerciseController {
    public final ExcerciseRepository excerciseRepository;
    private final UserRepository userRepository;

    public ExcerciseController(ExcerciseRepository excerciseRepository, UserRepository userRepository) {
        this.excerciseRepository = excerciseRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Iterable<Excercise> getExcercises() {
        return excerciseRepository.findAll();
    }

    @GetMapping("{id}")
    public Excercise getExcerciseById(@PathVariable UUID id) {
        return excerciseRepository.findById(id).orElseThrow();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addExcercise(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestBody Excercise new_excercise) {
        Excercise excercise = new Excercise(
                new_excercise.getName(),
                new_excercise.getQuestion(),
                new_excercise.getType(),
                new_excercise.getDifficulty(),
                new_excercise.getTags(),
                new_excercise.getHints(),
                new_excercise.getSolutions()
        );
        excercise.setAuthor(userRepository.getByOAuth2(oAuth2User));
        excercise.updateLastModified();
        excerciseRepository.save(excercise);
    }

    @PutMapping("{id}")
    public void updateExcercise(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id, @RequestBody Excercise new_excercise) {
        User user = userRepository.getByOAuth2(oAuth2User);
        new_excercise.setAuthor(user);
        Excercise excercise = excerciseRepository.findById(id).orElse(new_excercise);
        if(!excercise.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this excercise");
        }
        excercise.setName(new_excercise.getName());
        excercise.setDifficulty(new_excercise.getDifficulty());
        excercise.setType(new_excercise.getType());
        excercise.setGenerator(new_excercise.getGenerator());
        excercise.setTags(new_excercise.getTags());
        excercise.setHints(new_excercise.getHints());
        excercise.setSolutions(new_excercise.getSolutions());
        excercise.setQuestion(new_excercise.getQuestion());
        excercise.updateLastModified();
        excerciseRepository.save(excercise);
    }

    @DeleteMapping("{id}")
    public void deleteExcercise(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        Excercise excercise = excerciseRepository.findById(id).orElseThrow();
        if(!excercise.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this excercise");
        }
        excerciseRepository.deleteById(id);
    }
}

