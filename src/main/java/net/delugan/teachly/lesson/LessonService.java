package net.delugan.teachly.lesson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.persistence.EntityNotFoundException;
import net.delugan.teachly.exercise.Exercise;
import net.delugan.teachly.exercise.ExerciseRepository;
import net.delugan.teachly.exercisegenerator.ExerciseGenerator;
import net.delugan.teachly.reward.Reward;
import net.delugan.teachly.reward.RewardRepository;
import net.delugan.teachly.trigger.Trigger;
import net.delugan.teachly.trigger.TriggerRepository;
import net.delugan.teachly.user.User;
import net.delugan.teachly.user.UserRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Service class for lesson-related operations.
 * Provides methods for creating, updating, and generating lessons.
 */
@Service
public class LessonService {

    /**
     * Repository for accessing and managing triggers.
     */
    private final TriggerRepository triggerRepository;

    /**
     * Repository for accessing and managing exercises.
     */
    private final ExerciseRepository exerciseRepository;

    /**
     * Repository for accessing and managing rewards.
     */
    private final RewardRepository rewardRepository;

    /**
     * Repository for accessing and managing users.
     */
    private final UserRepository userRepository;

    /**
     * Constructs a new LessonService with the required repositories.
     *
     * @param triggerRepository Repository for triggers
     * @param exerciseRepository Repository for exercises
     * @param rewardRepository Repository for rewards
     * @param userRepository Repository for users
     */
    public LessonService(TriggerRepository triggerRepository, ExerciseRepository exerciseRepository, RewardRepository rewardRepository, UserRepository userRepository) {
        this.triggerRepository = triggerRepository;
        this.exerciseRepository = exerciseRepository;
        this.rewardRepository = rewardRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new lesson from a lesson request.
     *
     * @param lessonRequest The lesson request containing lesson data
     * @param oAuth2User The authenticated user who will be the author
     * @return The created lesson
     * @throws EntityNotFoundException if referenced entities are not found
     */
    public Lesson createLesson(LessonRequest lessonRequest, OAuth2User oAuth2User) {
        Lesson lesson = new Lesson();
        lesson.setName(lessonRequest.getName());
        lesson.setDescription(lessonRequest.getDescription());
        lesson.setExplanation(lessonRequest.getExplanation());
        lesson.setTags(lessonRequest.getTags());

        // Resolve the author
        User author = userRepository.getByOAuth2(oAuth2User);
        lesson.setAuthor(author);

        // Resolve triggers
        List<Trigger> triggers = triggerRepository.findAllById(lessonRequest.getTriggers());
        lesson.setTriggers(triggers);

        // Resolve exercises
        List<Exercise> exercises = exerciseRepository.findAllById(lessonRequest.getExercises());
        lesson.setExercises(exercises);

        // Resolve rewards
        Reward correctReward = rewardRepository.findById(lessonRequest.getCorrectReward().get(0))
                .orElseThrow(() -> new EntityNotFoundException("Correct reward not found"));
        Reward wrongReward = rewardRepository.findById(lessonRequest.getWrongReward().get(0))
                .orElseThrow(() -> new EntityNotFoundException("Wrong reward not found"));
        lesson.setCorrectReward(correctReward);
        lesson.setWrongReward(wrongReward);

        return lesson;
    }

    /**
     * Updates an existing lesson with data from a lesson request.
     *
     * @param lesson The lesson to update
     * @param lessonRequest The lesson request containing updated data
     * @return The updated lesson
     * @throws EntityNotFoundException if referenced entities are not found
     */
    public Lesson updateLesson(Lesson lesson, LessonRequest lessonRequest) {
        lesson.setName(lessonRequest.getName());
        lesson.setDescription(lessonRequest.getDescription());
        lesson.setExplanation(lessonRequest.getExplanation());
        lesson.setTags(lessonRequest.getTags());

        // Resolve triggers
        List<Trigger> triggers = triggerRepository.findAllById(lessonRequest.getTriggers());
        lesson.setTriggers(triggers);

        // Resolve exercises
        List<Exercise> exercises = exerciseRepository.findAllById(lessonRequest.getExercises());
        lesson.setExercises(exercises);

        // Resolve rewards
        Reward correctReward = rewardRepository.findById(lessonRequest.getCorrectReward().get(0))
                .orElseThrow(() -> new EntityNotFoundException("Correct reward not found"));
        Reward wrongReward = rewardRepository.findById(lessonRequest.getWrongReward().get(0))
                .orElseThrow(() -> new EntityNotFoundException("Wrong reward not found"));
        lesson.setCorrectReward(correctReward);
        lesson.setWrongReward(wrongReward);

        return lesson;
    }

    /**
     * Generates code for a lesson based on its components.
     * Creates JavaScript code that combines exercises, triggers, and rewards.
     *
     * @param lesson The lesson to generate code for
     * @throws JsonProcessingException if there's an error processing JSON
     */
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
            ExerciseGenerator generator = exercise.getGenerator();
            exercise.setGenerator(null);
            String json = ow.writeValueAsString(exercise);
            exercise.setGenerator(generator);
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