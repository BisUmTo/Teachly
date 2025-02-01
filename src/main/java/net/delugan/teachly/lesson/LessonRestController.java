package net.delugan.teachly.lesson;

import net.delugan.teachly.user.User;
import net.delugan.teachly.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/lessons")
class LessonRestController {
    public final LessonRepository lessonRepository;
    public final UserRepository userRepository;
    public final LessonService lessonService;

    public LessonRestController(LessonRepository lessonRepository, UserRepository userRepository, LessonService lessonService) {
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
        this.lessonService = lessonService;
    }
    
    @GetMapping
    public List<Lesson> getLessons() {
        return lessonRepository.findAll();
    }
    
    @GetMapping("{id}")
    public Lesson getLesson(@PathVariable UUID id) {
        return lessonRepository.findById(id).orElseThrow();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Lesson addLesson(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestBody LessonRequest lessonRequest) {
        Lesson lesson = lessonService.createLesson(lessonRequest, oAuth2User);
        lesson.updateLastModified();
        lessonRepository.save(lesson);
        return lesson;
    }

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

    @DeleteMapping("{id}")
    public void deleteLesson(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow();
        if(!lesson.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this lesson");
        }
        lessonRepository.deleteById(id);
    }

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

    @GetMapping("/tags")
    public List<String> getTags() {
        return lessonRepository.getAllTags();
    }
}
