package net.delugan.teachly.lesson;

import net.delugan.teachly.excercise.Excercise;
import net.delugan.teachly.excercise.ExcerciseRepository;
import net.delugan.teachly.excercisegenerator.ExcerciseGenerator;
import net.delugan.teachly.excercisegenerator.ExcerciseGeneratorRepository;
import net.delugan.teachly.lesson.Lesson;
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
class LessonController {
    public final LessonRepository lessonRepository;
    public final UserRepository userRepository;

    public LessonController(LessonRepository lessonRepository, UserRepository userRepository) {
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
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
    public void addLesson(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestBody Lesson new_lesson) {
        Lesson lesson = new Lesson(new_lesson.getName(), new_lesson.getDescription(), new_lesson.getExplanation());
        lesson.setLinks(new_lesson.getLinks());
        lesson.setExcercises(new_lesson.getExcercises());
        lesson.setTriggers(new_lesson.getTriggers());
        lesson.setCorrectReward(new_lesson.getCorrectReward());
        lesson.setWrongReward(new_lesson.getWrongReward());
        lesson.setAuthor(userRepository.getByOAuth2(oAuth2User));
        lesson.updateLastModified();
        lessonRepository.save(lesson);
    }

    @PutMapping("{id}")
    public void updateLesson(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id, @RequestBody Lesson new_lesson) {
        User user = userRepository.getByOAuth2(oAuth2User);
        new_lesson.setAuthor(user);
        Lesson lesson = lessonRepository.findById(id).orElse(new_lesson);
        if(!lesson.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this lesson");
        }
        lesson.setName(new_lesson.getName());
        lesson.setDescription(new_lesson.getDescription());
        lesson.setExplanation(new_lesson.getExplanation());
        lesson.updateLastModified();
        lessonRepository.save(lesson);
    }

    @DeleteMapping("{id}")
    public void deleteLesson(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow();
        if(!lesson.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this lesson");
        }
        lessonRepository.deleteById(id);
    }
}
