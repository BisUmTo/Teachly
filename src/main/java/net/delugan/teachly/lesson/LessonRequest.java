package net.delugan.teachly.lesson;

import java.util.List;
import java.util.UUID;

/**
 * Class representing a request to create or update a lesson.
 * Contains all the necessary data for lesson creation or modification.
 */
public class LessonRequest {
    /**
     * The name of the lesson.
     */
    private String name;
    
    /**
     * A brief description of the lesson.
     */
    private String description;
    
    /**
     * Detailed explanation of the lesson content.
     */
    private String explanation;
    
    /**
     * Tags associated with this lesson for categorization and searching.
     */
    private List<String> tags;
    
    /**
     * List of trigger IDs associated with this lesson.
     */
    private List<UUID> triggers;
    
    /**
     * List of exercise IDs included in this lesson.
     */
    private List<UUID> exercises;
    
    /**
     * List of reward IDs to be given for correct answers.
     */
    private List<UUID> correctReward;
    
    /**
     * List of reward IDs to be given for incorrect answers.
     */
    private List<UUID> wrongReward;

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
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
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
     * Gets the tags associated with this lesson.
     *
     * @return The list of tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Sets the tags associated with this lesson.
     *
     * @param tags The new list of tags
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * Gets the trigger IDs associated with this lesson.
     *
     * @return The list of trigger IDs
     */
    public List<UUID> getTriggers() {
        return triggers;
    }

    /**
     * Sets the trigger IDs associated with this lesson.
     *
     * @param triggers The new list of trigger IDs
     */
    public void setTriggers(List<UUID> triggers) {
        this.triggers = triggers;
    }

    /**
     * Gets the exercise IDs included in this lesson.
     *
     * @return The list of exercise IDs
     */
    public List<UUID> getExercises() {
        return exercises;
    }

    /**
     * Sets the exercise IDs included in this lesson.
     *
     * @param exercises The new list of exercise IDs
     */
    public void setExercises(List<UUID> exercises) {
        this.exercises = exercises;
    }

    /**
     * Gets the reward IDs for correct answers.
     *
     * @return The list of reward IDs for correct answers
     */
    public List<UUID> getCorrectReward() {
        return correctReward;
    }

    /**
     * Sets the reward IDs for correct answers.
     *
     * @param correctReward The new list of reward IDs for correct answers
     */
    public void setCorrectReward(List<UUID> correctReward) {
        this.correctReward = correctReward;
    }

    /**
     * Gets the reward IDs for incorrect answers.
     *
     * @return The list of reward IDs for incorrect answers
     */
    public List<UUID> getWrongReward() {
        return wrongReward;
    }

    /**
     * Sets the reward IDs for incorrect answers.
     *
     * @param wrongReward The new list of reward IDs for incorrect answers
     */
    public void setWrongReward(List<UUID> wrongReward) {
        this.wrongReward = wrongReward;
    }
}
