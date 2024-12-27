package net.delugan.teachly.utils;

import com.nimbusds.jose.shaded.gson.JsonSyntaxException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.boot.json.JacksonJsonParser;

public class JsonStringValidator implements ConstraintValidator<JsonString, String> {

    @Override
    public void initialize(JsonString jsonString) {
    }

    @Override
    public boolean isValid(String string, ConstraintValidatorContext context) {
        // Use an implementation from step 1. A brief example:
        try {
            new JacksonJsonParser().parseMap(string); // parse the string
            return true;                    // valid JSON, return true
        } catch (JsonSyntaxException ex) {
            /* exception handling if needed */
        }
        return false;                       // invalid JSON, return false
    }
}
