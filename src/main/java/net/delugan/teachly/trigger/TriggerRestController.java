package net.delugan.teachly.trigger;

import net.delugan.teachly.user.User;
import net.delugan.teachly.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing triggers.
 * Provides endpoints for CRUD operations on triggers.
 */
@RestController
@RequestMapping("/api/v1/triggers")
public class TriggerRestController {
    /**
     * Repository for accessing and managing triggers.
     */
    public final TriggerRepository triggerRepository;
    
    /**
     * Repository for accessing and managing users.
     */
    public final UserRepository userRepository;

    /**
     * Constructs a new TriggerRestController with the required repositories.
     *
     * @param triggerRepository Repository for triggers
     * @param userRepository Repository for users
     */
    public TriggerRestController(TriggerRepository triggerRepository, UserRepository userRepository) {
        this.triggerRepository = triggerRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all triggers.
     *
     * @return Iterable of all triggers
     */
    @GetMapping
    public Iterable<Trigger> getTriggers() {
        return triggerRepository.findAll();
    }

    /**
     * Retrieves a trigger by its ID.
     *
     * @param id The ID of the trigger to retrieve
     * @return The trigger with the specified ID
     * @throws java.util.NoSuchElementException if no trigger is found with the given ID
     */
    @GetMapping("{id}")
    public Trigger getTriggerById(@PathVariable UUID id) {
        return triggerRepository.findById(id).orElseThrow();
    }

    /**
     * Creates a new trigger.
     *
     * @param oAuth2User The authenticated user
     * @param new_trigger The trigger data to create
     * @return The created trigger
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Trigger addTrigger(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestBody Trigger new_trigger) {
        Trigger trigger = new Trigger(new_trigger.getName(), new_trigger.getDescription(), new_trigger.getBlocklyJsonCode(), new_trigger.getBlocklyGeneratedCode(), new_trigger.getTags());
        trigger.setAuthor(userRepository.getByOAuth2(oAuth2User));
        trigger.updateLastModified();
        triggerRepository.save(trigger);
        return trigger;
    }

    /**
     * Updates an existing trigger.
     *
     * @param oAuth2User The authenticated user
     * @param id The ID of the trigger to update
     * @param new_trigger The updated trigger data
     * @return The updated trigger
     * @throws AuthorizationDeniedException if the authenticated user is not the author of the trigger
     */
    @PutMapping("{id}")
    public Trigger updateTrigger(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id, @RequestBody Trigger new_trigger) {
        User user = userRepository.getByOAuth2(oAuth2User);
        new_trigger.setAuthor(user);
        Trigger trigger = triggerRepository.findById(id).orElse(new_trigger);
        if(!trigger.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this trigger");
        }
        trigger.setName(new_trigger.getName());
        trigger.setDescription(new_trigger.getDescription());
        trigger.setBlocklyJsonCode(new_trigger.getBlocklyJsonCode());
        trigger.setBlocklyGeneratedCode(new_trigger.getBlocklyGeneratedCode());
        trigger.updateLastModified();
        trigger.setTags(new_trigger.getTags());
        triggerRepository.save(trigger);
        return trigger;
    }

    /**
     * Deletes a trigger.
     *
     * @param oAuth2User The authenticated user
     * @param id The ID of the trigger to delete
     * @throws AuthorizationDeniedException if the authenticated user is not the author of the trigger
     */
    @DeleteMapping("{id}")
    public void deleteTrigger(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        Trigger trigger = triggerRepository.findById(id).orElseThrow();
        if(!trigger.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this trigger");
        }
        triggerRepository.deleteById(id);
    }

    /**
     * Retrieves all unique tags used in triggers.
     *
     * @return List of all unique tags
     */
    @GetMapping("/tags")
    public List<String> getTags() {
        return triggerRepository.getAllTags();
    }
}
