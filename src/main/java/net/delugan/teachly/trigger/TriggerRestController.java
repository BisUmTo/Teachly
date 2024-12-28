package net.delugan.teachly.trigger;

import net.delugan.teachly.user.User;
import net.delugan.teachly.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/triggers")
public class TriggerRestController {
    public final TriggerRepository triggerRepository;
    public final UserRepository userRepository;

    public TriggerRestController(TriggerRepository triggerRepository, UserRepository userRepository) {
        this.triggerRepository = triggerRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Iterable<Trigger> getTriggers() {
        return triggerRepository.findAll();
    }

    @GetMapping("{id}")
    public Trigger getTriggerById(@PathVariable UUID id) {
        return triggerRepository.findById(id).orElseThrow();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addTrigger(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestBody Trigger new_trigger) {
        Trigger trigger = new Trigger(new_trigger.getName(), new_trigger.getDescription(), new_trigger.getBlocklyJsonCode());
        trigger.setAuthor(userRepository.getByOAuth2(oAuth2User));
        trigger.updateLastModified();
        triggerRepository.save(trigger);
    }

    @PutMapping("{id}")
    public void updateTrigger(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id, @RequestBody Trigger new_trigger) {
        User user = userRepository.getByOAuth2(oAuth2User);
        new_trigger.setAuthor(user);
        Trigger trigger = triggerRepository.findById(id).orElse(new_trigger);
        if(!trigger.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this trigger");
        }
        trigger.setName(new_trigger.getName());
        trigger.setDescription(new_trigger.getDescription());
        trigger.setBlocklyJsonCode(new_trigger.getBlocklyJsonCode());
        trigger.updateLastModified();
        triggerRepository.save(trigger);
    }

    @DeleteMapping("{id}")
    public void deleteTrigger(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable UUID id) {
        Trigger trigger = triggerRepository.findById(id).orElseThrow();
        if(!trigger.isAuthor(userRepository.getByOAuth2(oAuth2User))) {
            throw new AuthorizationDeniedException("You are not the author of this trigger");
        }
        triggerRepository.deleteById(id);
    }
}
