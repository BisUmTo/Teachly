package net.delugan.teachly.trigger;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TriggerRepository extends JpaRepository<Trigger, UUID> {

}
