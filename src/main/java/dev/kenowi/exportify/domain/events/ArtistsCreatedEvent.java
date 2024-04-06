package dev.kenowi.exportify.domain.events;

import dev.kenowi.exportify.domain.entities.Artist;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.util.Collection;

@Getter
@Slf4j
public class ArtistsCreatedEvent extends ApplicationEvent {
    private final Collection<Artist> artists;

    public ArtistsCreatedEvent(Object source, Collection<Artist> artists) {
        super(source);
        this.artists = artists;
        log.info("Artists created: {}", artists.size());
    }
}
