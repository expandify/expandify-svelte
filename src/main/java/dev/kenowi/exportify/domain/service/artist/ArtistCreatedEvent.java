package dev.kenowi.exportify.domain.service.artist;

import dev.kenowi.exportify.domain.entities.Artist;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ArtistCreatedEvent extends ApplicationEvent {
    private final Artist artist;

    private ArtistCreatedEvent(Object artistEventService, Artist artist) {
        super(artistEventService);
        this.artist = artist;
    }

    ArtistCreatedEvent(Artist artist) {
        this(ArtistEventService.class, artist);
    }
}
