package net.delugan.teachly.trigger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/triggers")
public class TriggerController {
    public final TriggerRepository triggerRepository;

    public TriggerController(TriggerRepository triggerRepository) {
        this.triggerRepository = triggerRepository;
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
    public void addTrigger(@RequestBody Trigger new_trigger) {
        Trigger trigger = new Trigger(new_trigger.getName(), new_trigger.getDescription(), new_trigger.getBlocklyJsonCode());
        trigger.setAuthor(null); // TODO: author
        triggerRepository.save(trigger);
    }

    @PutMapping("{id}")
    public void updateTrigger(@PathVariable UUID id, @RequestBody Trigger new_trigger) {
        new_trigger.setAuthor(null); // TODO: author
        Trigger trigger = triggerRepository.findById(id).orElse(new_trigger);
        trigger.setName(new_trigger.getName());
        trigger.setDescription(new_trigger.getDescription());
        trigger.setBlocklyJsonCode(new_trigger.getBlocklyJsonCode());
        triggerRepository.save(trigger);
    }

    @DeleteMapping("{id}")
    public void deleteTrigger(@PathVariable UUID id) {
        triggerRepository.deleteById(id);
    }
}
