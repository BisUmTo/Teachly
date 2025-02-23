package net.delugan.teachly.exercise;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {
    default List<Exercise> findAllByGeneratorId(UUID generatorId){
        return findAll().stream().filter(exercise -> {
            if (exercise.getGenerator() == null) {
                return false;
            }
            return exercise.getGeneratorId().equals(generatorId);
        }).toList();
    }
    default List<String> getAllTags(){
        return findAll().stream().map(Exercise::getTags).flatMap(List::stream).distinct().toList();
    }
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

    static int extractNumber(String name) {
        Pattern pattern = Pattern.compile(".*?(\\d+)$");
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }
}
