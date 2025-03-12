package net.delugan.teachly.trigger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import net.delugan.teachly.utils.AuthorAndDateTracked;
import net.delugan.teachly.utils.JsonString;

import java.util.List;
import java.util.UUID;

/**
 * Entity class representing a trigger in the system.
 * Triggers define conditions that initiate actions in lessons.
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "triggers")
public class Trigger extends AuthorAndDateTracked {
    /**
     * Unique identifier for the trigger.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The name of the trigger.
     */
    @Column(nullable = false, unique = true)
    @Schema(description = "The name of the trigger", example = "When a mob is killed")
    private String name;

    /**
     * The description of the trigger.
     */
    @Schema(description = "The description of the trigger", example = "Triggers when a mob is killed by a player")
    private String description;

    /**
     * The Blockly JSON code that defines the trigger's behavior.
     */
    @Column(name = "blockly_json_code", nullable = false, length = 10485760)
    @JsonString
    @Schema(description = "The Blockly JSON code of the reward", example = "{\"blocks\": {\"languageVersion\": 0,\"blocks\": [{...}]}}")
    private String blocklyJsonCode;

    /**
     * The JavaScript code generated from the Blockly code.
     */
    @Column(name = "blockly_generated_code", nullable = true, length = 10485760)
    @Schema(description = "The Blockly generated JS code of the reward", example = "function reward() {\n  // code\n}")
    private String blocklyGeneratedCode;

    /**
     * Tags associated with this trigger for categorization and searching.
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "trigger_tags", joinColumns = @JoinColumn(name = "trigger_id"))
    @Schema(description = "The tags of the trigger", example = "[\"math\", \"geometry\"]")
    private List<String> tags;

    /**
     * Constructs a new Trigger with the specified details.
     *
     * @param name The name of the trigger
     * @param description The description of the trigger
     * @param blocklyJsonCode The Blockly JSON code
     * @param blocklyGeneratedCode The generated JavaScript code
     * @param tags The tags associated with the trigger
     */
    public Trigger(String name, String description, String blocklyJsonCode, String blocklyGeneratedCode, List<String> tags) {
        super();
        this.name = name;
        this.description = description;
        this.blocklyJsonCode = blocklyJsonCode;
        this.blocklyGeneratedCode = blocklyGeneratedCode;
        this.tags = tags;
    }

    /**
     * Protected constructor for JPA.
     */
    protected Trigger() {
        super();
    }

    /**
     * Gets the trigger's ID.
     *
     * @return The trigger's ID
     */
    public UUID getId() {
        return id;
    }

    /**
     * Gets the trigger's name.
     *
     * @return The trigger's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the trigger's name.
     *
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the trigger's description.
     *
     * @return The trigger's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the trigger's description.
     *
     * @param description The new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the Blockly JSON code.
     *
     * @return The Blockly JSON code
     */
    public String getBlocklyJsonCode() {
        return blocklyJsonCode;
    }

    /**
     * Sets the Blockly JSON code.
     *
     * @param blocklyJsonCode The new Blockly JSON code
     */
    public void setBlocklyJsonCode(String blocklyJsonCode) {
        this.blocklyJsonCode = blocklyJsonCode;
    }

    /**
     * Gets the tags associated with this trigger.
     *
     * @return The list of tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Sets the tags associated with this trigger.
     *
     * @param tags The new list of tags
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * Gets the generated JavaScript code.
     *
     * @return The generated JavaScript code
     */
    public String getBlocklyGeneratedCode() {
        return blocklyGeneratedCode;
    }

    /**
     * Sets the generated JavaScript code.
     *
     * @param blocklyGeneratedCode The new generated JavaScript code
     */
    public void setBlocklyGeneratedCode(String blocklyGeneratedCode) {
        this.blocklyGeneratedCode = blocklyGeneratedCode;
    }
}
