package net.delugan.teachly.excercisegenerator;

import net.delugan.teachly.excercise.Excercise;
import net.delugan.teachly.excercise.ExcerciseRepository;
import net.delugan.teachly.user.User;
import net.delugan.teachly.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/excercises/generators")
class ExcerciseGeneratorRestController {
    public final ExcerciseGeneratorRepository excerciseGeneratorRepository;
    public final ExcerciseRepository excerciseRepository;
    public final UserRepository userRepository;

    public ExcerciseGeneratorRestController(ExcerciseGeneratorRepository excerciseGeneratorRepository, ExcerciseRepository excerciseRepository, UserRepository userRepository) {
        this.excerciseGeneratorRepository = excerciseGeneratorRepository;
        this.excerciseRepository = excerciseRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Iterable<ExcerciseGenerator> getExcerciseGenerators() {
        return excerciseGeneratorRepository.findAll();
    }

    @GetMapping("{id}")
    public ExcerciseGenerator getExcerciseGeneratorById(@PathVariable UUID id) {
        return excerciseGeneratorRepository.findById(id).orElseThrow();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addExcerciseGenerator(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestBody ExcerciseGenerator new_excerciseGenerator) {
        ExcerciseGenerator excerciseGenerator = new ExcerciseGenerator(
                new_excerciseGenerator.getName(),
                new_excerciseGenerator.getType(),
                new_excerciseGenerator.getDifficulty(),
                new_excerciseGenerator.getBlocklyJsonCode()
        );
        excerciseGenerator.setAuthor(userRepository.getByOAuth2(oAuth2User));
        excerciseGenerator.updateLastModified();
        excerciseGeneratorRepository.save(excerciseGenerator);
    }

    @PutMapping("{id}")
    public void updateExcerciseGenerator(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id, @RequestBody ExcerciseGenerator new_excerciseGenerator) {
        User user = userRepository.getByOAuth2(oAuth2User);
        new_excerciseGenerator.setAuthor(user);
        ExcerciseGenerator excerciseGenerator = excerciseGeneratorRepository.findById(id).orElse(new_excerciseGenerator);
        if(!excerciseGenerator.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this excercise generator");
        }
        excerciseGenerator.setType(new_excerciseGenerator.getType());
        excerciseGenerator.setDifficulty(new_excerciseGenerator.getDifficulty());
        excerciseGenerator.setBlocklyJsonCode(new_excerciseGenerator.getBlocklyJsonCode());
        excerciseGenerator.updateLastModified();
        excerciseGeneratorRepository.save(excerciseGenerator);
    }

    @DeleteMapping("{id}")
    public void deleteExcerciseGenerator(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        ExcerciseGenerator excerciseGenerator = excerciseGeneratorRepository.findById(id).orElseThrow();
        if(!excerciseGenerator.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this excercise generator");
        }
        excerciseGeneratorRepository.deleteById(id);
    }

    @GetMapping("{id}/generate")
    public List<Excercise> generateExcercise(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        User user = userRepository.getByOAuth2(oAuth2User);
        ExcerciseGenerator excerciseGenerator = excerciseGeneratorRepository.findById(id).orElseThrow();
        if(!excerciseGenerator.isAuthor(user)) {
            throw new AuthorizationDeniedException("You are not the author of this excercise generator");
        }
        return excerciseGenerator.generateExcercise();
    }

    @GetMapping("{id}/generated")
    public List<Excercise> getGeneratedExcercises(@PathVariable UUID id) {
        return excerciseRepository.findAllByGeneratorId(id);
    }

    @DeleteMapping("{id}/generated")
    public int deleteGeneratedExcercises(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        User user = userRepository.getByOAuth2(oAuth2User);
        ExcerciseGenerator excerciseGenerator = excerciseGeneratorRepository.findById(id).orElseThrow();
        if(!excerciseGenerator.isAuthor(user)) {
            throw new AuthorizationDeniedException("You are not the author of this excercise generator");
        }
        List<Excercise> excercises = excerciseRepository.findAllByGeneratorId(id).stream()
                .filter(excercise -> excercise.isAuthor(user)).toList();
        excerciseRepository.deleteAll(excercises);
        return excercises.size();
    }
}
