package net.delugan.teachly.trigger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import net.delugan.teachly.utils.AuthorAndDateTracked;
import net.delugan.teachly.utils.JsonString;

import java.util.List;
import java.util.UUID;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "triggers")
public class Trigger extends AuthorAndDateTracked {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    @Schema(description = "The name of the trigger", example = "When a mob is killed")
    private String name;

    @Schema(description = "The description of the trigger", example = "Triggers when a mob is killed by a player")
    private String description;

    @Column(name = "blockly_json_code", nullable = false, length = 10485760)
    @JsonString
    @Schema(description = "The Blockly JSON code of the reward", example = "{\"blocks\": {\"languageVersion\": 0,\"blocks\": [{...}]}}")
    private String blocklyJsonCode;

    @Column(name = "blockly_generated_code", nullable = true, length = 10485760)
    @Schema(description = "The Blockly generated JS code of the reward", example = "function reward() {\n  // code\n}")
    private String blocklyGeneratedCode;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "trigger_tags", joinColumns = @JoinColumn(name = "trigger_id"))
    @Schema(description = "The tags of the trigger", example = "[\"math\", \"geometry\"]")
    private List<String> tags;

    public Trigger(String name, String description, String blocklyJsonCode, String blocklyGeneratedCode, List<String> tags) {
        super();
        this.name = name;
        this.description = description;
        this.blocklyJsonCode = blocklyJsonCode;
        this.blocklyGeneratedCode = blocklyGeneratedCode;
        this.tags = tags;
    }

    protected Trigger() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBlocklyJsonCode() {
        return blocklyJsonCode;
    }

    public void setBlocklyJsonCode(String blocklyJsonCode) {
        this.blocklyJsonCode = blocklyJsonCode;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getBlocklyGeneratedCode() {
        return blocklyGeneratedCode;
    }

    public void setBlocklyGeneratedCode(String blocklyGeneratedCode) {
        this.blocklyGeneratedCode = blocklyGeneratedCode;
    }
}
