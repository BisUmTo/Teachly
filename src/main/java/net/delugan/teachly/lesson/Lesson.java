package net.delugan.teachly.lesson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import net.delugan.teachly.exercise.Exercise;
import net.delugan.teachly.reward.Reward;
import net.delugan.teachly.trigger.Trigger;
import net.delugan.teachly.utils.AuthorAndDateTracked;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Entity class representing a lesson in the system.
 * Lessons are educational units that contain explanations and exercises.
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "lessons")
public class Lesson extends AuthorAndDateTracked {
    /**
     * Unique identifier for the lesson.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The name of the lesson.
     */
    @Column(nullable = false, unique = true)
    @Schema(description = "The name of the lesson", example = "Pythagorean theorem")
    private String name;

    /**
     * A brief description of the lesson.
     */
    @Schema(description = "The description of the lesson", example = "Learn the Pythagorean theorem on right triangles, main formula and applications")
    private String description;

    /**
     * Detailed explanation of the lesson content.
     */
    @Schema(description = "The explanation of the lesson", example = "The Pythagorean theorem is a fundamental relation in Euclidean geometry among the three sides of a right triangle...")
    private String explanation;

    /**
     * External links related to the lesson content.
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "lesson_links", joinColumns = @JoinColumn(name = "lesson_id"))
    @Schema(description = "The links of the lesson", example = "[{\"name\": \"Wikipedia\", \"url\": \"https://en.wikipedia.org/wiki/Pythagorean_theorem\"}]")
    private List<Pair<String, URI>> links;

    /**
     * Exercises included in this lesson.
     */
    @ManyToMany
    @JoinTable(
            name = "lesson_exercises",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id"))
    @Schema(description = "The exercises that are part of the lesson")
    private List<Exercise> exercises;

    /**
     * Tags associated with this lesson for categorization and searching.
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "lesson_tags", joinColumns = @JoinColumn(name = "lesson_id"))
    @Schema(description = "The tags of the lesson", example = "[\"math\", \"geometry\"]")
    private List<String> tags;

    /**
     * Triggers that determine when to show exercises during the lesson.
     */
    @ManyToMany
    @JoinTable(
            name = "lesson_triggers",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "trigger_id"))
    @Schema(description = "The triggers to show an exercise during the lesson")
    private List<Trigger> triggers;

    /**
     * Reward given when a student answers correctly.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "correct_reward_id")
    @Schema(description = "The reward given when the student answers correctly")
    private Reward correctReward;

    /**
     * Reward given when a student answers incorrectly.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wrong_reward_id")
    @Schema(description = "The reward given when the student answers incorrectly")
    private Reward wrongReward;

    /**
     * The Blockly generated code for the lesson.
     */
    @Column(name = "blockly_generated_code", length = 10485760)
    @Schema(description = "The Blockly generated code of the lesson")
    private String blocklyGeneratedCode;

    @Column(name = "last_generation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastGeneration;

    /**
     * Constructs a new Lesson with the specified details.
     *
     * @param name The name of the lesson
     * @param description A brief description of the lesson
     * @param explanation Detailed explanation of the lesson content
     */
    public Lesson(String name, String description, String explanation) {
        super();
        this.name = name;
        this.description = description;
        this.explanation = explanation;
    }

    /**
     * Protected constructor for JPA.
     */
    protected Lesson() {
        super();
    }

    /**
     * Gets the lesson's ID.
     *
     * @return The lesson's ID
     */
    public UUID getId() {
        return id;
    }

    /**
     * Gets the lesson's name.
     *
     * @return The lesson's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the lesson's name.
     *
     * @param title The new name
     */
    public void setName(String title) {
        this.name = title;
    }

    /**
     * Gets the lesson's description.
     *
     * @return The lesson's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the lesson's description.
     *
     * @param description The new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the lesson's detailed explanation.
     *
     * @return The lesson's explanation
     */
    public String getExplanation() {
        return explanation;
    }

    /**
     * Sets the lesson's detailed explanation.
     *
     * @param explanation The new explanation
     */
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    /**
     * Gets the links associated with the lesson.
     *
     * @return The list of name-URL pairs
     */
    public List<Pair<String, URI>> getLinks() {
        return links;
    }

    /**
     * Sets the links associated with the lesson.
     *
     * @param links The new list of name-URL pairs
     */
    public void setLinks(List<Pair<String, URI>> links) {
        this.links = links;
    }

    /**
     * Gets the exercises that are part of this lesson.
     *
     * @return The list of exercises
     */
    public List<Exercise> getExercises() {
        return exercises;
    }

    /**
     * Sets the exercises that are part of this lesson.
     *
     * @param exercises The new list of exercises
     */
    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    /**
     * Gets the triggers that show exercises during the lesson.
     *
     * @return The list of triggers
     */
    public List<Trigger> getTriggers() {
        return triggers;
    }

    /**
     * Sets the triggers that show exercises during the lesson.
     *
     * @param triggers The new list of triggers
     */
    public void setTriggers(List<Trigger> triggers) {
        this.triggers = triggers;
    }

    /**
     * Gets the reward given when a student answers correctly.
     *
     * @return The correct answer reward
     */
    public Reward getCorrectReward() {
        return correctReward;
    }

    /**
     * Sets the reward given when a student answers correctly.
     *
     * @param correctReward The new correct answer reward
     */
    public void setCorrectReward(Reward correctReward) {
        this.correctReward = correctReward;
    }

    /**
     * Gets the reward given when a student answers incorrectly.
     *
     * @return The wrong answer reward
     */
    public Reward getWrongReward() {
        return wrongReward;
    }

    /**
     * Sets the reward given when a student answers incorrectly.
     *
     * @param wrongReward The new wrong answer reward
     */
    public void setWrongReward(Reward wrongReward) {
        this.wrongReward = wrongReward;
    }

    /**
     * Gets the generated JavaScript code for this lesson.
     *
     * @return The generated code
     */
    public String getBlocklyGeneratedCode() {
        return blocklyGeneratedCode;
    }

    /**
     * Sets the generated JavaScript code for this lesson.
     * Also updates the lastGeneration timestamp.
     *
     * @param blocklyGeneratedCode The new generated code
     */
    public void setBlocklyGeneratedCode(String blocklyGeneratedCode) {
        this.blocklyGeneratedCode = blocklyGeneratedCode;
        this.lastGeneration = new Date();
    }

    /**
     * Gets the timestamp of when the lesson code was last generated.
     *
     * @return The last generation timestamp
     */
    public Date getLastGeneration() {
        return lastGeneration;
    }

    /**
     * Gets the tags associated with the lesson.
     *
     * @return The list of tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Sets the tags associated with the lesson.
     *
     * @param tags The new list of tags
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
