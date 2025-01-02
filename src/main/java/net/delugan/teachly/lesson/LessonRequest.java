package net.delugan.teachly.lesson;

import java.util.List;
import java.util.UUID;

public class LessonRequest {
    private String name;
    private String description;
    private String explanation;
    private List<String> tags;
    private List<UUID> triggers;
    private List<UUID> exercises;
    private List<UUID> correctReward;
    private List<UUID> wrongReward;

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

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<UUID> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<UUID> triggers) {
        this.triggers = triggers;
    }

    public List<UUID> getExercises() {
        return exercises;
    }

    public void setExercises(List<UUID> exercises) {
        this.exercises = exercises;
    }

    public List<UUID> getCorrectReward() {
        return correctReward;
    }

    public void setCorrectReward(List<UUID> correctReward) {
        this.correctReward = correctReward;
    }

    public List<UUID> getWrongReward() {
        return wrongReward;
    }

    public void setWrongReward(List<UUID> wrongReward) {
        this.wrongReward = wrongReward;
    }

}
