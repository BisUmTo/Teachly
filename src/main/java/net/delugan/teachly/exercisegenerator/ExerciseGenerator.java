package net.delugan.teachly.exercisegenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import net.delugan.teachly.utils.AuthorAndDateTracked;
import net.delugan.teachly.utils.JsonString;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Entity class representing an exercise generator in the system.
 * Exercise generators are templates that can create multiple similar exercises.
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "exercise_generators")
public class ExerciseGenerator extends AuthorAndDateTracked {
    /**
     * Unique identifier for the exercise generator.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The name of the exercise generator.
     */
    @Column(nullable = false, unique = true)
    @Schema(description = "The name of the exercise generator", example = "Pythagorean triples generator")
    private String name;

    /**
     * A description of what the generator does.
     */
    @Schema(description = "The description of the exercise generator", example = "This generator creates exercises about Pythagorean triples")
    private String description;

    /**
     * Tags associated with this generator for categorization and searching.
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "exercise_generators_tags", joinColumns = @JoinColumn(name = "exercise_id"))
    @Schema(description = "The tags of the exercise", example = "[\"math\", \"geometry\"]")
    private List<String> tags;

    /**
     * The Blockly JSON code that defines the generator's logic.
     */
    @Column(name = "blockly_json_code", nullable = false, length = 10485760)
    @JsonString
    @Schema(description = "The Blockly JSON code of the reward", example = "{\"blocks\": {\"languageVersion\": 0,\"blocks\": [{...}]}}")
    private String blocklyJsonCode;

    /**
     * The timestamp of when the generator was last used to create an exercise.
     */
    @Column(name = "last_generation")
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "The date of the last generation of the exercise with this generator", example = "2024-12-26T23:35:38Z")
    private Date lastGeneration;

    /**
     * Constructs a new ExerciseGenerator with the specified details.
     *
     * @param name The name of the generator
     * @param description The description of what the generator does
     * @param tags The tags associated with the generator
     * @param blocklyJsonCode The Blockly JSON code for the generator
     */
    public ExerciseGenerator(String name, String description, List<String> tags, String blocklyJsonCode) {
        super();
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.blocklyJsonCode = blocklyJsonCode;
    }

    /**
     * Protected constructor for JPA.
     */
    protected ExerciseGenerator() {
        super();
    }

    /**
     * Gets the generator's ID.
     *
     * @return The generator's ID
     */
    public UUID getId() {
        return id;
    }

    /**
     * Gets the Blockly JSON code for this generator.
     *
     * @return The Blockly JSON code
     */
    public String getBlocklyJsonCode() {
        return blocklyJsonCode;
    }

    /**
     * Sets the Blockly JSON code for this generator.
     *
     * @param blocklyJsonCode The new Blockly JSON code
     */
    public void setBlocklyJsonCode(String blocklyJsonCode) {
        this.blocklyJsonCode = blocklyJsonCode;
    }

    /**
     * Gets the timestamp of when the generator was last used.
     *
     * @return The last generation timestamp
     */
    public Date getLastGeneration() {
        return lastGeneration;
    }

    /**
     * Sets the timestamp of when the generator was last used.
     *
     * @param lastGeneration The new last generation timestamp
     */
    public void setLastGeneration(Date lastGeneration) {
        this.lastGeneration = lastGeneration;
    }

    /**
     * Gets the generator's name.
     *
     * @return The generator's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the generator's name.
     *
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the tags associated with this generator.
     *
     * @return The list of tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Sets the tags associated with this generator.
     *
     * @param tags The new list of tags
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * Gets the generator's description.
     *
     * @return The generator's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the generator's description.
     *
     * @param description The new description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
