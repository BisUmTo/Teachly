package net.delugan.teachly.exercise;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import net.delugan.teachly.exercisegenerator.ExerciseGenerator;
import net.delugan.teachly.utils.AuthorAndDateTracked;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "exercises")
public class Exercise extends AuthorAndDateTracked {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    @Schema(description = "The name of the exercise", example = "Pythagorean theorem - exercise 1")
    private String name;

    @Column(nullable = false)
    @Schema(description = "The question of the exercise", example = "What is the hypotenuse of a right triangle with catheti of 6 and 8?")
    private String question;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(description = "The type of the exercise", example = "OPEN_QUESTION")
    private ExerciseType type;

    @Enumerated(EnumType.STRING)
    @Schema(description = "The difficulty of the exercise", example = "EASY")
    private ExerciseDifficulty difficulty;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "exercise_tags", joinColumns = @JoinColumn(name = "exercise_id"))
    @Schema(description = "The tags of the exercise", example = "[\"math\", \"geometry\"]")
    private List<String> tags;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "exercise_hints", joinColumns = @JoinColumn(name = "exercise_id"))
    @Schema(description = "The hints of the exercise", example = "[\"Try to use the Pythagorean theorem\"]")
    private List<String> hints;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "exercise_solutions", joinColumns = @JoinColumn(name = "exercise_id"))
    @Schema(description = "The solutions of the exercise", example = "[\"10\", \"10cm\", \"10 cm\"]")
    private List<String> solutions;

    @Column(name = "generator_id", insertable = false, updatable = false)
    @Schema(description = "The ID of the exercise generator that generated this exercise")
    private UUID generatorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generator")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @Schema(description = "The exercise generator that generated this exercise")
    private ExerciseGenerator generator;

    public Exercise(String name, String question, ExerciseType type, ExerciseDifficulty difficulty, List<String> tags, List<String> hints, List<String> solutions) {
        super();
        this.name = name;
        this.question = question;
        this.type = type;
        this.difficulty = difficulty;
        this.tags = tags;
        this.hints = hints;
        this.solutions = solutions;
    }

    protected Exercise() {
        super();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ExerciseType getType() {
        return type;
    }

    public void setType(ExerciseType type) {
        this.type = type;
    }

    public ExerciseDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(ExerciseDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getHints() {
        return hints;
    }

    public void setHints(List<String> hints) {
        this.hints = hints;
    }

    public List<String> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<String> solutions) {
        this.solutions = solutions;
    }

    public UUID getGeneratorId() {
        return generatorId;
    }

    public ExerciseGenerator getGenerator() {
        return generator;
    }

    public void setGenerator(ExerciseGenerator generator) {
        this.generator = generator;
        if (generator != null) {
            this.generatorId = generator.getId();  // Assicurati che 'getId' restituisca l'UUID
        } else {
            this.generatorId = null;  // Se 'author' è null, imposta anche 'authorId' su null
        }
    }
}
