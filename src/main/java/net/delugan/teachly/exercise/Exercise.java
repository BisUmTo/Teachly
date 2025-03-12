package net.delugan.teachly.exercise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import net.delugan.teachly.exercisegenerator.ExerciseGenerator;
import net.delugan.teachly.utils.AuthorAndDateTracked;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.UUID;

/**
 * Entity class representing an exercise in the system.
 * Exercises are educational tasks that can be assigned to students.
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "exercises")
public class Exercise extends AuthorAndDateTracked {
    /**
     * Unique identifier for the exercise.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The name of the exercise.
     */
    @Column(nullable = false, unique = true)
    @Schema(description = "The name of the exercise", example = "Pythagorean theorem - exercise 1")
    private String name;

    /**
     * The question or problem statement of the exercise.
     */
    @Column(nullable = false)
    @Schema(description = "The question of the exercise", example = "What is the hypotenuse of a right triangle with catheti of 6 and 8?")
    private String question;

    /**
     * The type of exercise (e.g., multiple choice, open question).
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(description = "The type of the exercise", example = "OPEN_QUESTION")
    private ExerciseType type;

    /**
     * The difficulty level of the exercise.
     */
    @Enumerated(EnumType.STRING)
    @Schema(description = "The difficulty of the exercise", example = "EASY")
    private ExerciseDifficulty difficulty;

    /**
     * Tags associated with this exercise for categorization and searching.
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "exercise_tags", joinColumns = @JoinColumn(name = "exercise_id"))
    @Schema(description = "The tags of the exercise", example = "[\"math\", \"geometry\"]")
    private List<String> tags;

    /**
     * Hints for open questions or options for multiple choice questions.
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "exercise_hints", joinColumns = @JoinColumn(name = "exercise_id"))
    @Schema(description = "The hints of the exercise if type is OPEN_QUESTION, options if MULTIPLE_CHOICE", example = "[\"Try to use the Pythagorean theorem\"]")
    private List<String> hints;

    /**
     * Acceptable solutions for the exercise.
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "exercise_solutions", joinColumns = @JoinColumn(name = "exercise_id"))
    @Schema(description = "The solutions of the exercise", example = "[\"10\", \"10cm\", \"10 cm\"]")
    private List<String> solutions;

    /**
     * The ID of the generator that created this exercise.
     */
    @Column(name = "generator_id", insertable = false, updatable = false)
    @Schema(description = "The ID of the exercise generator that generated this exercise")
    private UUID generatorId;

    /**
     * The exercise generator that created this exercise.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generator")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @Schema(description = "The exercise generator that generated this exercise")
    private ExerciseGenerator generator;

    /**
     * Constructs a new Exercise with the specified details.
     *
     * @param name The name of the exercise
     * @param question The question or problem statement
     * @param type The type of exercise
     * @param difficulty The difficulty level
     * @param tags The tags associated with the exercise
     * @param hints The hints or options
     * @param solutions The acceptable solutions
     */
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

    /**
     * Protected constructor for JPA.
     */
    protected Exercise() {
        super();
    }

    /**
     * Gets the exercise's ID.
     *
     * @return The exercise's ID
     */
    public UUID getId() {
        return id;
    }

    /**
     * Gets the exercise's name.
     *
     * @return The exercise's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the exercise's name.
     *
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the exercise's question.
     *
     * @return The exercise's question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets the exercise's question.
     *
     * @param question The new question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Gets the exercise's type.
     *
     * @return The exercise's type
     */
    public ExerciseType getType() {
        return type;
    }

    /**
     * Sets the exercise's type.
     *
     * @param type The new type
     */
    public void setType(ExerciseType type) {
        this.type = type;
    }

    /**
     * Gets the exercise's difficulty.
     *
     * @return The exercise's difficulty
     */
    public ExerciseDifficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the exercise's difficulty.
     *
     * @param difficulty The new difficulty
     */
    public void setDifficulty(ExerciseDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Gets the tags associated with this exercise.
     *
     * @return The list of tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Sets the tags associated with this exercise.
     *
     * @param tags The new list of tags
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * Gets the hints or options for this exercise.
     *
     * @return The list of hints or options
     */
    public List<String> getHints() {
        return hints;
    }

    /**
     * Sets the hints or options for this exercise.
     *
     * @param hints The new list of hints or options
     */
    public void setHints(List<String> hints) {
        this.hints = hints;
    }

    /**
     * Gets the acceptable solutions for this exercise.
     *
     * @return The list of acceptable solutions
     */
    public List<String> getSolutions() {
        return solutions;
    }

    /**
     * Sets the acceptable solutions for this exercise.
     *
     * @param solutions The new list of acceptable solutions
     */
    public void setSolutions(List<String> solutions) {
        this.solutions = solutions;
    }

    /**
     * Gets the ID of the generator that created this exercise.
     * Returns null if no generator is associated with this exercise.
     *
     * @return The generator ID or null
     */
    public UUID getGeneratorId() {
        return this.generator != null ? this.generator.getId() : null;
    }

    /**
     * Gets the exercise generator that created this exercise.
     *
     * @return The exercise generator
     */
    public ExerciseGenerator getGenerator() {
        return generator;
    }

    /**
     * Sets the exercise generator for this exercise.
     * Also updates the generatorId field to maintain consistency.
     *
     * @param generator The new exercise generator
     */
    public void setGenerator(ExerciseGenerator generator) {
        this.generator = generator;
        if (generator != null) {
            this.generatorId = generator.getId();
        } else {
            this.generatorId = null;
        }
    }
}
