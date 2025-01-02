package net.delugan.teachly.lesson;

import net.delugan.teachly.exercise.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    default List<String> getAllTags(){
        return findAll().stream().map(Lesson::getTags).flatMap(List::stream).distinct().toList();
    }
}
