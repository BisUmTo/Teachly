package net.delugan.teachly.exercise;

import net.delugan.teachly.utils.AuthorAndDateTracked;

import java.util.List;

public class ExerciseRequest extends AuthorAndDateTracked {
    private String question;
    private ExerciseType type;
    private ExerciseDifficulty difficulty;
    private List<String> hints;
    private List<String> solutions;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ExerciseType getType() {
        return type;
    }

    public void setType(ExerciseType type) {
        this.type = type;
    }

    public ExerciseDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(ExerciseDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public List<String> getHints() {
        return hints;
    }

    public void setHints(List<String> hints) {
        this.hints = hints;
    }

    public List<String> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<String> solutions) {
        this.solutions = solutions;
    }
}
