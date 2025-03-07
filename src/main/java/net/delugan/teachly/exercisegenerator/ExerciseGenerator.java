package net.delugan.teachly.exercisegenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import net.delugan.teachly.exercise.*;
import net.delugan.teachly.utils.AuthorAndDateTracked;
import net.delugan.teachly.utils.JsonString;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "exercise_generators")
public class ExerciseGenerator extends AuthorAndDateTracked {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    @Schema(description = "The name of the exercise generator", example = "Pythagorean triples generator")
    private String name;

    @Schema(description = "The description of the exercise generator", example = "This generator creates exercises about Pythagorean triples")
    private String description;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "exercise_generators_tags", joinColumns = @JoinColumn(name = "exercise_id"))
    @Schema(description = "The tags of the exercise", example = "[\"math\", \"geometry\"]")
    private List<String> tags;

    @Column(name = "blockly_json_code", nullable = false, length = 10485760)
    @JsonString
    @Schema(description = "The Blockly JSON code of the reward", example = "{\"blocks\": {\"languageVersion\": 0,\"blocks\": [{...}]}}")
    private String blocklyJsonCode;

    @Column(name = "last_generation")
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "The date of the last generation of the exercise with this generator", example = "2024-12-26T23:35:38Z")
    private Date lastGeneration;

    public ExerciseGenerator(String name, String description, List<String> tags, String blocklyJsonCode) {
        super();
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.blocklyJsonCode = blocklyJsonCode;
    }

    protected ExerciseGenerator() {
        super();
    }

    public UUID getId() {
        return id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
