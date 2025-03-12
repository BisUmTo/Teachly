package net.delugan.teachly.reward;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import net.delugan.teachly.utils.AuthorAndDateTracked;
import net.delugan.teachly.utils.JsonString;

import java.util.List;
import java.util.UUID;

/**
 * Entity class representing a reward in the system.
 * Rewards are given to students based on their performance in exercises.
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "rewards")
public class Reward extends AuthorAndDateTracked {
    /**
     * Unique identifier for the reward.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The name of the reward.
     */
    @Column(nullable = false, unique = true)
    @Schema(description = "The name of the reward", example = "Give a candy")
    private String name;

    /**
     * The description of the reward.
     */
    @Schema(description = "The description of the reward", example = "Spawns a candy near the student")
    private String description;

    /**
     * The Blockly JSON code that defines the reward's behavior.
     */
    @Column(name = "blockly_json_code", nullable = false, length = 10485760)
    @JsonString
    @Schema(description = "The Blockly JSON code of the reward", example = "{\"blocks\": {\"languageVersion\": 0,\"blocks\": [{...}]}}")
    private String blocklyJsonCode;

    /**
     * The JavaScript code generated from the Blockly code.
     */
    @Column(name = "blocklu_generated_code", nullable = true, length = 10485760)
    @Schema(description = "The Blockly generated JS code of the reward", example = "function reward() {\n  // code\n}")
    private String blocklyGeneratedCode;

    /**
     * Tags associated with this reward for categorization and searching.
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "reward_tags", joinColumns = @JoinColumn(name = "reward_id"))
    @Schema(description = "The tags of the reward", example = "[\"math\", \"geometry\"]")
    private List<String> tags;

    /**
     * Constructs a new Reward with the specified details.
     *
     * @param name The name of the reward
     * @param description The description of the reward
     * @param blocklyJsonCode The Blockly JSON code
     * @param blocklyGeneratedCode The generated JavaScript code
     * @param tags The tags associated with the reward
     */
    public Reward(String name, String description, String blocklyJsonCode, String blocklyGeneratedCode, List<String> tags) {
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
    protected Reward() {
        super();
    }

    /**
     * Gets the reward's ID.
     *
     * @return The reward's ID
     */
    public UUID getId() {
        return id;
    }

    /**
     * Gets the reward's name.
     *
     * @return The reward's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the reward's name.
     *
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the reward's description.
     *
     * @return The reward's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the reward's description.
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
     * Gets the tags associated with this reward.
     *
     * @return The list of tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Sets the tags associated with this reward.
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
