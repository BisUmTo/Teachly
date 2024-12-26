package net.delugan.teachly.reward;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "rewards")
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String name;
    private String description;
    @Column(name = "blockly_json_code", nullable = false)
    private String blocklyJsonCode;

    public Reward(String name, String description, String blocklyJsonCode) {
        this.name = name;
        this.description = description;
        this.blocklyJsonCode = blocklyJsonCode;
    }

    protected Reward() {
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
}
