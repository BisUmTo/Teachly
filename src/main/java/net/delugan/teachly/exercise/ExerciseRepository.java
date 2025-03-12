package net.delugan.teachly.exercise;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Repository interface for Exercise entities.
 * Provides methods for accessing and managing exercises in the database.
 */
public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {
    /**
     * Finds all exercises created by a specific generator.
     *
     * @param generatorId The ID of the generator
     * @return A list of exercises created by the specified generator
     */
    default List<Exercise> findAllByGeneratorId(UUID generatorId){
        return findAll().stream().filter(exercise -> {
            if (exercise.getGenerator() == null) {
                return false;
            }
            return exercise.getGeneratorId().equals(generatorId);
        }).toList();
    }
    
    /**
     * Retrieves all unique tags used across all exercises.
     *
     * @return A list of all unique tags
     */
    default List<String> getAllTags(){
        return findAll().stream().map(Exercise::getTags).flatMap(List::stream).distinct().toList();
    }
    
    /**
     * Finds all exercises created by a specific generator and orders them by name.
     * The ordering is based on numeric suffixes in the names, if present.
     *
     * @param generatorId The ID of the generator
     * @return A sorted list of exercises created by the specified generator
     */
    default List<Exercise> findAllByGeneratorIdOrderByName(UUID generatorId) {
        return findAllByGeneratorId(generatorId)
                .stream()
                .sorted((e1, e2) -> {
                    int num1 = extractNumber(e1.getName());
                    int num2 = extractNumber(e2.getName());
                    int ret = Integer.compare(num1, num2);
                    if (ret == 0) {
                        return e1.getName().compareTo(e2.getName());
                    }
                    return ret;
                })
                .collect(Collectors.toList());
    }

    /**
     * Extracts a numeric suffix from a string, if present.
     * Used for natural sorting of exercise names.
     *
     * @param name The string to extract a number from
     * @return The extracted number, or 0 if no number is found
     */
    static int extractNumber(String name) {
        Pattern pattern = Pattern.compile(".*?(\\d+)$");
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }
}
