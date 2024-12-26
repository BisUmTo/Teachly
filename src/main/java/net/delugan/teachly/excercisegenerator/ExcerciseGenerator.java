package net.delugan.teachly.excercisegenerator;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import net.delugan.teachly.excercise.Excercise;
import net.delugan.teachly.excercise.ExcerciseDifficulty;
import net.delugan.teachly.excercise.ExcerciseType;
import net.delugan.teachly.utils.AuthorAndDateTracked;
import net.delugan.teachly.utils.JsonString;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "excercise_generators")
public class ExcerciseGenerator extends AuthorAndDateTracked {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @Schema(description = "The type of the excercise", example = "MULTIPLE_CHOICE")
    private ExcerciseType type;

    @Enumerated(EnumType.STRING)
    @Schema(description = "The difficulty of the excercise", example = "EASY")
    private ExcerciseDifficulty difficulty;

    @Column(name = "blockly_json_code", nullable = false)
    @JsonString
    @Schema(description = "The Blockly JSON code of the reward", example = "{\"blocks\": {\"languageVersion\": 0,\"blocks\": [{...}]}}")
    private String blocklyJsonCode;

    @Column(name = "last_generation")
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "The date of the last generation of the excercise with this generator", example = "2024-12-26T23:35:38Z")
    private Date lastGeneration;

    public ExcerciseGenerator(ExcerciseType type, ExcerciseDifficulty difficulty, String blocklyJsonCode) {
        super();
        this.type = type;
        this.difficulty = difficulty;
        this.blocklyJsonCode = blocklyJsonCode;
    }

    protected ExcerciseGenerator() {
        super();
    }

    public UUID getId() {
        return id;
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

    public List<Excercise> generateExcercise() {
        setLastGeneration(new Date());
        List<Excercise> excercises = null;
        // TODO: Implement excercise generation
        return excercises;
    }
}
