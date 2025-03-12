package net.delugan.teachly.exercise;

/**
 * Enumeration of possible exercise types.
 * Defines the different formats that exercises can take.
 */

 public enum ExerciseType {
    /**
     * Represents a multiple choice exercise.
     * Students select one or more answers from a list of options.
     */
    MULTIPLE_CHOICE, 
    
    /**
     * Represents an open question exercise.
     * Students provide a free-form answer to a question.
     */
    OPEN_QUESTION, 
    
    /**
     * Represents a true/false exercise.
     * Students determine whether a statement is true or false.
     */
    TRUE_FALSE
}
