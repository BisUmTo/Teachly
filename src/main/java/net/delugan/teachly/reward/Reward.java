package net.delugan.teachly.reward;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import net.delugan.teachly.utils.AuthorAndDateTracked;
import net.delugan.teachly.utils.JsonString;

import java.util.List;
import java.util.UUID;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "rewards")
public class Reward extends AuthorAndDateTracked {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    @Schema(description = "The name of the reward", example = "Give a candy")
    private String name;

    @Schema(description = "The description of the reward", example = "Spawns a candy near the student")
    private String description;

    @Column(name = "blockly_json_code", nullable = false, length = 10485760)
    @JsonString
    @Schema(description = "The Blockly JSON code of the reward", example = "{\"blocks\": {\"languageVersion\": 0,\"blocks\": [{...}]}}")
    private String blocklyJsonCode;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "reward_tags", joinColumns = @JoinColumn(name = "reward_id"))
    @Schema(description = "The tags of the reward", example = "[\"math\", \"geometry\"]")
    private List<String> tags;

    public Reward(String name, String description, String blocklyJsonCode) {
        super();
        this.name = name;
        this.description = description;
        this.blocklyJsonCode = blocklyJsonCode;
    }

    protected Reward() {
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
}
