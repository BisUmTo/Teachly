package net.delugan.teachly.excercisegenerator;

import net.delugan.teachly.excercise.Excercise;
import net.delugan.teachly.excercise.ExcerciseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/excercises/generators")
class ExcerciseGeneratorController {
    public final ExcerciseGeneratorRepository excerciseGeneratorRepository;
    public final ExcerciseRepository excerciseRepository;

    public ExcerciseGeneratorController(ExcerciseGeneratorRepository excerciseGeneratorRepository, ExcerciseRepository excerciseRepository) {
        this.excerciseGeneratorRepository = excerciseGeneratorRepository;
        this.excerciseRepository = excerciseRepository;
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
    public void addExcerciseGenerator(@RequestBody ExcerciseGenerator new_excerciseGenerator) {
        ExcerciseGenerator excerciseGenerator = new ExcerciseGenerator(new_excerciseGenerator.getType(), new_excerciseGenerator.getDifficulty(), new_excerciseGenerator.getBlocklyJsonCode());
        excerciseGenerator.setAuthor(null); // TODO: author
        excerciseGeneratorRepository.save(excerciseGenerator);
    }

    @PutMapping("{id}")
    public void updateExcerciseGenerator(@PathVariable UUID id, @RequestBody ExcerciseGenerator new_excerciseGenerator) {
        new_excerciseGenerator.setAuthor(null); // TODO: author
        ExcerciseGenerator excerciseGenerator = excerciseGeneratorRepository.findById(id).orElse(new_excerciseGenerator);
        excerciseGenerator.setType(new_excerciseGenerator.getType());
        excerciseGenerator.setDifficulty(new_excerciseGenerator.getDifficulty());
        excerciseGenerator.setBlocklyJsonCode(new_excerciseGenerator.getBlocklyJsonCode());
        excerciseGeneratorRepository.save(excerciseGenerator);
    }

    @DeleteMapping("{id}")
    public void deleteExcerciseGenerator(@PathVariable UUID id) {
        excerciseGeneratorRepository.deleteById(id);
    }

    @GetMapping("{id}/generate")
    public List<Excercise> generateExcercise(@PathVariable UUID id) {
        ExcerciseGenerator excerciseGenerator = excerciseGeneratorRepository.findById(id).orElseThrow();
        return excerciseGenerator.generateExcercise();
    }

    @GetMapping("{id}/generated")
    public List<Excercise> getGeneratedExcercises(@PathVariable UUID id) {
        return excerciseRepository.findAllByGeneratorId(id);
    }

    @DeleteMapping("{id}/generated")
    public void deleteGeneratedExcercises(@PathVariable UUID id) {
        List<Excercise> excercises = excerciseRepository.findAllByGeneratorId(id);
        excerciseRepository.deleteAll(excercises);
    }
}
