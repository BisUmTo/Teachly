package net.delugan.teachly.exercisegenerator;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import net.delugan.teachly.exercise.Exercise;
import net.delugan.teachly.exercise.ExerciseDifficulty;
import net.delugan.teachly.exercise.ExerciseType;
import net.delugan.teachly.utils.AuthorAndDateTracked;
import net.delugan.teachly.utils.JsonString;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "exercise_generators")
public class ExerciseGenerator extends AuthorAndDateTracked {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    @Schema(description = "The name of the exercise generator", example = "Pythagorean triples generator")
    private String name;

    @Column(nullable = false)
    @Schema(description = "The type of the exercise", example = "MULTIPLE_CHOICE")
    private ExerciseType type;

    @Enumerated(EnumType.STRING)
    @Schema(description = "The difficulty of the exercise", example = "EASY")
    private ExerciseDifficulty difficulty;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "exercise_generators_tags", joinColumns = @JoinColumn(name = "exercise_id"))
    @Schema(description = "The tags of the exercise", example = "[\"math\", \"geometry\"]")
    private List<String> tags;

    @Column(name = "blockly_json_code", nullable = false)
    @JsonString
    @Schema(description = "The Blockly JSON code of the reward", example = "{\"blocks\": {\"languageVersion\": 0,\"blocks\": [{...}]}}")
    private String blocklyJsonCode;

    @Column(name = "last_generation")
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "The date of the last generation of the exercise with this generator", example = "2024-12-26T23:35:38Z")
    private Date lastGeneration;

    public ExerciseGenerator(String name, ExerciseType type, ExerciseDifficulty difficulty, List<String> tags, String blocklyJsonCode) {
        super();
        this.name = name;
        this.type = type;
        this.difficulty = difficulty;
        this.tags = tags;
        this.blocklyJsonCode = blocklyJsonCode;
    }

    protected ExerciseGenerator() {
        super();
    }

    public UUID getId() {
        return id;
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

    public String getBlocklyJsonCode() {
        return blocklyJsonCode;
    }

    public void setBlocklyJsonCode(String blocklyJsonCode) {
        this.blocklyJsonCode = blocklyJsonCode;
    }

    public Date getLastGeneration() {
        return lastGeneration;
    }

    public void setLastGeneration(Date lastGeneration) {
        this.lastGeneration = lastGeneration;
    }

    public List<Exercise> generateExercise() {
        setLastGeneration(new Date());
        List<Exercise> exercises = null;
        // TODO: Implement exercise generation
        return exercises;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
