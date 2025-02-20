package net.delugan.teachly.lesson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.persistence.EntityNotFoundException;
import net.delugan.teachly.exercise.Exercise;
import net.delugan.teachly.exercise.ExerciseRepository;
import net.delugan.teachly.reward.Reward;
import net.delugan.teachly.reward.RewardRepository;
import net.delugan.teachly.trigger.Trigger;
import net.delugan.teachly.trigger.TriggerRepository;
import net.delugan.teachly.user.User;
import net.delugan.teachly.user.UserRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public void generateLesson(Lesson lesson) throws JsonProcessingException {
        Date now = new Date();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        StringBuilder generatedCode = new StringBuilder();
        generatedCode.append("// Generated code for lesson: ").append(lesson.getName()).append("\n");
        generatedCode.append("// Last modified: ").append(now).append("\n\n");

        generatedCode.append("// Exercises\n");
        generatedCode.append("const EXERCISES = [\n");
        for (Exercise exercise : lesson.getExercises()) {
            // TOFIX: This is a workaround to ignore @JsonIgnore properties
            exercise.setGenerator(null);
            String json = ow.writeValueAsString(exercise);
            json = json.replaceAll("(?m)^", "\t");
            generatedCode.append(json).append(",\n");
        }
        generatedCode.append("];\n");

        generatedCode.append("\n// Triggers\n");
        for (Trigger trigger : lesson.getTriggers()) {
            generatedCode.append(trigger.getBlocklyGeneratedCode());
        }

        generatedCode.append("\n// Correct reward\n");
        generatedCode.append("function onCorrectAnswer(event) {\n  ");
        generatedCode.append(lesson.getCorrectReward().getBlocklyGeneratedCode());
        generatedCode.append("}\n");
        generatedCode.append("$.subscribe('CorrectAnswerEvent', 'onCorrectAnswer');\n");

        generatedCode.append("\n// Wrong reward\n");
        generatedCode.append("function onWrongAnswer(event) {\n   ");
        generatedCode.append(lesson.getWrongReward().getBlocklyGeneratedCode());
        generatedCode.append("}\n");
        generatedCode.append("$.subscribe('WrongAnswerEvent', 'onWrongAnswer');\n");


        lesson.setBlocklyGeneratedCode(generatedCode.toString());
    }
}