package net.delugan.teachly.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Annotation that can be used to validate if a string field contains valid JSON.
 * Fields annotated with @JsonString will be validated using JsonStringValidator.
 */
@Documented
@Constraint(validatedBy = JsonStringValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonString {
    /**
     * Error message to be used when the validation fails.
     * 
     * @return The error message
     */
    String message() default "The String is not in JSON format";

    /**
     * Groups for this constraint.
     * 
     * @return The groups
     */
    Class<?>[] groups() default {};

    /**
     * Payload for this constraint.
     * 
     * @return The payload
     */
    Class<? extends Payload>[] payload() default {};
}