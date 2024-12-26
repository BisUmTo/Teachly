package net.delugan.teachly.trigger;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "triggers")
public class Trigger {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String name;
    private String description;
    @Column(name = "blockly_json_code", nullable = false)
    private String blocklyJsonCode;

}
