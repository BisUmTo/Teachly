package net.delugan.teachly.lesson;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Lesson entities.
 * Provides methods for accessing and managing lessons in the database.
 */
public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    /**
     * Retrieves all unique tags used across all lessons.
     *
     * @return A list of all unique tags
     */
    default List<String> getAllTags(){
        return findAll().stream().map(Lesson::getTags).flatMap(List::stream).distinct().toList();
    }
}
