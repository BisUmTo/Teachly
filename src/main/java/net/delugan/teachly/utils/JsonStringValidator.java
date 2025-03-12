package net.delugan.teachly.utils;

import com.nimbusds.jose.shaded.gson.JsonSyntaxException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.boot.json.JacksonJsonParser;

/**
 * Validator class for the JsonString annotation.
 * Validates that a string contains valid JSON format.
 */
public class JsonStringValidator implements ConstraintValidator<JsonString, String> {

    /**
     * Initializes the validator with the annotation instance.
     * 
     * @param jsonString The annotation instance
     */
    @Override
    public void initialize(JsonString jsonString) {
    }

    /**
     * Validates if the given string is in valid JSON format.
     * 
     * @param string The string to validate
     * @param context The constraint validator context
     * @return true if the string is valid JSON, false otherwise
     */
    @Override
    public boolean isValid(String string, ConstraintValidatorContext context) {

        try {
            new JacksonJsonParser().parseMap(string); 
            return true;                    
        } catch (JsonSyntaxException ex) {
        }
        return false;                     
    }
}
