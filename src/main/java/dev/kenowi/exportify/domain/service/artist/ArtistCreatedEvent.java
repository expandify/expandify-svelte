package dev.kenowi.exportify.domain.service.artist;

import dev.kenowi.exportify.domain.entities.Artist;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Getter
@Slf4j
public class ArtistCreatedEvent extends ApplicationEvent {
    private final Artist artist;

    ArtistCreatedEvent(Artist artist) {
        super(ArtistEventService.class);
        this.artist = artist;
        log.info("Artist created: {}", artist.getName());
    }
}
