package net.delugan.teachly.excercise;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/excercises")
class ExcerciseController {
    public final ExcerciseRepository excerciseRepository;

    public ExcerciseController(ExcerciseRepository excerciseRepository) {
        this.excerciseRepository = excerciseRepository;
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
    public void addExcercise(@RequestBody Excercise new_excercise) {
        Excercise excercise = new Excercise(
                new_excercise.getName(),
                new_excercise.getQuestion(),
                new_excercise.getType(),
                new_excercise.getDifficulty(),
                new_excercise.getTags(),
                new_excercise.getHints(),
                new_excercise.getSolutions()
        );
        excercise.setAuthor(null); // TODO: author
        excerciseRepository.save(excercise);
    }

    @PutMapping("{id}")
    public void updateExcercise(@PathVariable UUID id, @RequestBody Excercise new_excercise) {
        new_excercise.setAuthor(null); // TODO: author
        Excercise excercise = excerciseRepository.findById(id).orElse(new_excercise);
        excercise.setName(new_excercise.getName());
        excercise.setDifficulty(new_excercise.getDifficulty());
        excercise.setType(new_excercise.getType());
        excercise.setGenerator(new_excercise.getGenerator());
        excercise.setTags(new_excercise.getTags());
        excercise.setHints(new_excercise.getHints());
        excercise.setSolutions(new_excercise.getSolutions());
        excercise.setQuestion(new_excercise.getQuestion());
        excerciseRepository.save(excercise);
    }

    @DeleteMapping("{id}")
    public void deleteExcercise(@PathVariable UUID id) {
        excerciseRepository.deleteById(id);
    }
}

