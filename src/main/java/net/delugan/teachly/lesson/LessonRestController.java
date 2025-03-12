package net.delugan.teachly.lesson;

import net.delugan.teachly.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for lesson-related operations.
 * Provides endpoints for creating, retrieving, updating, and deleting lessons.
 */
@RestController
@RequestMapping("/api/v1/lessons")
class LessonRestController {
    /**
     * Repository for accessing and managing lessons.
     */
    public final LessonRepository lessonRepository;
    
    /**
     * Repository for accessing and managing users.
     */
    public final UserRepository userRepository;
    
    /**
     * Service for lesson-related operations.
     */
    public final LessonService lessonService;

    /**
     * Constructs a new LessonRestController with the required repositories and service.
     *
     * @param lessonRepository Repository for lessons
     * @param userRepository Repository for users
     * @param lessonService Service for lesson operations
     */
    public LessonRestController(LessonRepository lessonRepository, UserRepository userRepository, LessonService lessonService) {
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
        this.lessonService = lessonService;
    }
    
    /**
     * Retrieves all lessons.
     *
     * @return List of all lessons
     */
    @GetMapping
    public List<Lesson> getLessons() {
        return lessonRepository.findAll();
    }
    
    /**
     * Retrieves a lesson by its ID.
     *
     * @param id The ID of the lesson to retrieve
     * @return The lesson with the specified ID
     * @throws java.util.NoSuchElementException if no lesson is found with the given ID
     */
    @GetMapping("{id}")
    public Lesson getLesson(@PathVariable UUID id) {
        return lessonRepository.findById(id).orElseThrow();
    }

    /**
     * Creates a new lesson.
     *
     * @param oAuth2User The authenticated user
     * @param lessonRequest The lesson data to create
     * @return The created lesson
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Lesson addLesson(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestBody LessonRequest lessonRequest) {
        Lesson lesson = lessonService.createLesson(lessonRequest, oAuth2User);
        lesson.updateLastModified();
        lessonRepository.save(lesson);
        return lesson;
    }

    /**
     * Updates an existing lesson.
     *
     * @param oAuth2User The authenticated user
     * @param id The ID of the lesson to update
     * @param lessonRequest The updated lesson data
     * @return The updated lesson
     * @throws AuthorizationDeniedException if the authenticated user is not the author of the lesson
     */
    @PutMapping("{id}")
    public Lesson updateLesson(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id, @RequestBody LessonRequest lessonRequest) {
        Lesson new_lesson = lessonService.createLesson(lessonRequest, oAuth2User);
        Lesson lesson = lessonRepository.findById(id).orElse(new_lesson);
        if(!lesson.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this lesson");
        }
        lesson = lessonService.updateLesson(lesson, lessonRequest);
        lesson.updateLastModified();
        lessonRepository.save(lesson);
        return lesson;
    }

    /**
     * Deletes a lesson.
     *
     * @param oAuth2User The authenticated user
     * @param id The ID of the lesson to delete
     * @throws AuthorizationDeniedException if the authenticated user is not the author of the lesson
     * @throws java.util.NoSuchElementException if no lesson is found with the given ID
     */
    @DeleteMapping("{id}")
    public void deleteLesson(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow();
        if(!lesson.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this lesson");
        }
        lessonRepository.deleteById(id);
    }

    /**
     * Generates code for a lesson.
     *
     * @param oAuth2User The authenticated user
     * @param id The ID of the lesson to generate code for
     * @return The lesson with generated code
     * @throws AuthorizationDeniedException if the authenticated user is not the author of the lesson
     * @throws RuntimeException if an error occurs during code generation
     * @throws java.util.NoSuchElementException if no lesson is found with the given ID
     */
    @PostMapping("{id}/generate")
    public Lesson generateLesson(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow();
        if(!lesson.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this lesson");
        }
        try {
            lessonService.generateLesson(lesson);
        } catch (Exception e) {
            throw new RuntimeException("Error while generating the lesson", e);
        }
        lessonRepository.save(lesson);
        return lesson;
    }

    /**
     * Retrieves all unique tags used in lessons.
     *
     * @return List of all unique tags
     */
    @GetMapping("/tags")
    public List<String> getTags() {
        return lessonRepository.getAllTags();
    }
}
