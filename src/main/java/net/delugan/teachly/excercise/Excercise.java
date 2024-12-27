package net.delugan.teachly.excercise;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import net.delugan.teachly.excercisegenerator.ExcerciseGenerator;
import net.delugan.teachly.utils.AuthorAndDateTracked;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "excercises")
public class Excercise extends AuthorAndDateTracked {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    @Schema(description = "The name of the excercise", example = "Pythagorean theorem - exercise 1")
    private String name;

    @Column(nullable = false)
    @Schema(description = "The question of the excercise", example = "What is the hypotenuse of a right triangle with catheti of 6 and 8?")
    private String question;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(description = "The type of the excercise", example = "OPEN_ANSWER")
    private ExcerciseType type;

    @Enumerated(EnumType.STRING)
    @Schema(description = "The difficulty of the excercise", example = "EASY")
    private ExcerciseDifficulty difficulty;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "excercise_tags", joinColumns = @JoinColumn(name = "excercise_id"))
    @Schema(description = "The tags of the excercise", example = "[\"math\", \"geometry\"]")
    private List<String> tags;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "excercise_hints", joinColumns = @JoinColumn(name = "excercise_id"))
    @Schema(description = "The hints of the excercise", example = "[\"Try to use the Pythagorean theorem\"]")
    private List<String> hints;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "excercise_solutions", joinColumns = @JoinColumn(name = "excercise_id"))
    @Schema(description = "The solutions of the excercise", example = "[\"10\", \"10cm\", \"10 cm\"]")
    private List<String> solutions;

    @Column(name = "generator_id", insertable = false, updatable = false)
    @Schema(description = "The ID of the excercise generator that generated this excercise")
    private UUID generatorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generator")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @Schema(description = "The excercise generator that generated this excercise")
    private ExcerciseGenerator generator;

    public Excercise(String name, String question, ExcerciseType type, ExcerciseDifficulty difficulty, List<String> tags, List<String> hints, List<String> solutions) {
        super();
        this.name = name;
        this.question = question;
        this.type = type;
        this.difficulty = difficulty;
        this.tags = tags;
        this.hints = hints;
        this.solutions = solutions;
    }

    protected Excercise() {
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

    public ExcerciseType getType() {
        return type;
    }

    public void setType(ExcerciseType type) {
        this.type = type;
    }

    public ExcerciseDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(ExcerciseDifficulty difficulty) {
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

    public ExcerciseGenerator getGenerator() {
        return generator;
    }

    public void setGenerator(ExcerciseGenerator generator) {
        this.generator = generator;
        if (generator != null) {
            this.generatorId = generator.getId();  // Assicurati che 'getId' restituisca l'UUID
        } else {
            this.generatorId = null;  // Se 'author' è null, imposta anche 'authorId' su null
        }
    }
}
