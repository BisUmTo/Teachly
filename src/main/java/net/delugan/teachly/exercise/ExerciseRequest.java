package net.delugan.teachly.exercise;

import net.delugan.teachly.utils.AuthorAndDateTracked;

import java.util.List;

/**
 * Class representing a request to create or update an exercise.
 * Contains all the necessary data for exercise creation or modification.
 */
public class ExerciseRequest extends AuthorAndDateTracked {
    /**
     * The question or problem statement of the exercise.
     */
    private String question;
    
    /**
     * The type of exercise (e.g., multiple choice, open question).
     */
    private ExerciseType type;
    
    /**
     * The difficulty level of the exercise.
     */
    private ExerciseDifficulty difficulty;
    
    /**
     * Hints for open questions or options for multiple choice questions.
     */
    private List<String> hints;
    
    /**
     * Acceptable solutions for the exercise.
     */
    private List<String> solutions;

    /**
     * Gets the exercise's question.
     *
     * @return The exercise's question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets the exercise's question.
     *
     * @param question The new question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Gets the exercise's type.
     *
     * @return The exercise's type
     */
    public ExerciseType getType() {
        return type;
    }

    /**
     * Sets the exercise's type.
     *
     * @param type The new type
     */
    public void setType(ExerciseType type) {
        this.type = type;
    }

    /**
     * Gets the exercise's difficulty.
     *
     * @return The exercise's difficulty
     */
    public ExerciseDifficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the exercise's difficulty.
     *
     * @param difficulty The new difficulty
     */
    public void setDifficulty(ExerciseDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Gets the hints or options for this exercise.
     *
     * @return The list of hints or options
     */
    public List<String> getHints() {
        return hints;
    }

    /**
     * Sets the hints or options for this exercise.
     *
     * @param hints The new list of hints or options
     */
    public void setHints(List<String> hints) {
        this.hints = hints;
    }

    /**
     * Gets the acceptable solutions for this exercise.
     *
     * @return The list of acceptable solutions
     */
    public List<String> getSolutions() {
        return solutions;
    }

    /**
     * Sets the acceptable solutions for this exercise.
     *
     * @param solutions The new list of acceptable solutions
     */
    public void setSolutions(List<String> solutions) {
        this.solutions = solutions;
    }
}
