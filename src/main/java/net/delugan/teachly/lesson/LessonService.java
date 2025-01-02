package net.delugan.teachly.lesson;

import jakarta.persistence.EntityNotFoundException;
import net.delugan.teachly.exercise.Exercise;
import net.delugan.teachly.exercise.ExerciseRepository;
import net.delugan.teachly.reward.Reward;
import net.delugan.teachly.reward.RewardRepository;
import net.delugan.teachly.trigger.Trigger;
import net.delugan.teachly.trigger.TriggerRepository;
import net.delugan.teachly.user.User;
import net.delugan.teachly.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LessonService {

    private final TriggerRepository triggerRepository;

    private final ExerciseRepository exerciseRepository;

    private final RewardRepository rewardRepository;

    private final UserRepository userRepository;

    public LessonService(TriggerRepository triggerRepository, ExerciseRepository exerciseRepository, RewardRepository rewardRepository, UserRepository userRepository) {
        this.triggerRepository = triggerRepository;
        this.exerciseRepository = exerciseRepository;
        this.rewardRepository = rewardRepository;
        this.userRepository = userRepository;
    }

    public Lesson createLesson(LessonRequest lessonRequest, OAuth2User oAuth2User) {
        Lesson lesson = new Lesson();
        lesson.setName(lessonRequest.getName());
        lesson.setDescription(lessonRequest.getDescription());
        lesson.setExplanation(lessonRequest.getExplanation());
        lesson.setTags(lessonRequest.getTags());

        // Risolvi l'autore
        User author = userRepository.getByOAuth2(oAuth2User);
        lesson.setAuthor(author);

        // Risolvi i trigger
        List<Trigger> triggers = triggerRepository.findAllById(lessonRequest.getTriggers());
        lesson.setTriggers(triggers);

        // Risolvi gli esercizi
        List<Exercise> exercises = exerciseRepository.findAllById(lessonRequest.getExercises());
        lesson.setExercises(exercises);

        // Risolvi le ricompense
        Reward correctReward = rewardRepository.findById(lessonRequest.getCorrectReward().get(0))
                .orElseThrow(() -> new EntityNotFoundException("Correct reward not found"));
        Reward wrongReward = rewardRepository.findById(lessonRequest.getWrongReward().get(0))
                .orElseThrow(() -> new EntityNotFoundException("Wrong reward not found"));
        lesson.setCorrectReward(correctReward);
        lesson.setWrongReward(wrongReward);

        return lesson;
    }

    public Lesson updateLesson(Lesson lesson, LessonRequest lessonRequest) {
        lesson.setName(lessonRequest.getName());
        lesson.setDescription(lessonRequest.getDescription());
        lesson.setExplanation(lessonRequest.getExplanation());
        lesson.setTags(lessonRequest.getTags());

        // Risolvi i trigger
        List<Trigger> triggers = triggerRepository.findAllById(lessonRequest.getTriggers());
        lesson.setTriggers(triggers);

        // Risolvi gli esercizi
        List<Exercise> exercises = exerciseRepository.findAllById(lessonRequest.getExercises());
        lesson.setExercises(exercises);

        // Risolvi le ricompense
        Reward correctReward = rewardRepository.findById(lessonRequest.getCorrectReward().get(0))
                .orElseThrow(() -> new EntityNotFoundException("Correct reward not found"));
        Reward wrongReward = rewardRepository.findById(lessonRequest.getWrongReward().get(0))
                .orElseThrow(() -> new EntityNotFoundException("Wrong reward not found"));
        lesson.setCorrectReward(correctReward);
        lesson.setWrongReward(wrongReward);

        return lesson;
    }
}