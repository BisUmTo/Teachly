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

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "lessons")
public class Lesson extends AuthorAndDateTracked {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    @Schema(description = "The name of the lesson", example = "Pythagorean theorem")
    private String name;

    @Schema(description = "The description of the lesson", example = "Learn the Pythagorean theorem on right triangles, main formula and applications")
    private String description;

    @Schema(description = "The explanation of the lesson", example = "The Pythagorean theorem is a fundamental relation in Euclidean geometry among the three sides of a right triangle...")
    private String explanation;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "lesson_links", joinColumns = @JoinColumn(name = "lesson_id"))
    @Schema(description = "The links of the lesson", example = "[{\"name\": \"Wikipedia\", \"url\": \"https://en.wikipedia.org/wiki/Pythagorean_theorem\"}]")
    private List<Pair<String, URI>> links;

    @ManyToMany
    @JoinTable(
            name = "lesson_exercises",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id"))
    @Schema(description = "The exercises that are part of the lesson")
    private List<Exercise> exercises;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "lesson_tags", joinColumns = @JoinColumn(name = "lesson_id"))
    @Schema(description = "The tags of the lesson", example = "[\"math\", \"geometry\"]")
    private List<String> tags;

    @ManyToMany
    @JoinTable(
            name = "lesson_triggers",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "trigger_id"))
    @Schema(description = "The triggers to show an exercise during the lesson")
    private List<Trigger> triggers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "correct_reward_id")
    @Schema(description = "The reward given when the student answers correctly")
    private Reward correctReward;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wrong_reward_id")
    @Schema(description = "The reward given when the student answers incorrectly")
    private Reward wrongReward;

    @Column(name = "javascript_generated_code", length = 10485760)
    private String javascriptGeneratedCode;

    @Column(name = "last_generation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastGeneration;

    public Lesson(String name, String description, String explanation) {
        super();
        this.name = name;
        this.description = description;
        this.explanation = explanation;
    }

    protected Lesson() {
        super();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public List<Pair<String, URI>> getLinks() {
        return links;
    }

    public void setLinks(List<Pair<String, URI>> links) {
        this.links = links;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public List<Trigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<Trigger> triggers) {
        this.triggers = triggers;
    }

    public Reward getCorrectReward() {
        return correctReward;
    }

    public void setCorrectReward(Reward correctReward) {
        this.correctReward = correctReward;
    }

    public Reward getWrongReward() {
        return wrongReward;
    }

    public void setWrongReward(Reward wrongReward) {
        this.wrongReward = wrongReward;
    }

    public String getJavascriptGeneratedCode() {
        return javascriptGeneratedCode;
    }

    public void setJavascriptGeneratedCode(String javascriptGeneratedCode) {
        this.javascriptGeneratedCode = javascriptGeneratedCode;
        this.lastGeneration = new Date();
    }

    public Date getLastGeneration() {
        return lastGeneration;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
