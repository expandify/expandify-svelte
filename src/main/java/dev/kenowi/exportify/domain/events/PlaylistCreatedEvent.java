package dev.kenowi.exportify.domain.events;

import dev.kenowi.exportify.domain.entities.Playlist;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Getter
@Slf4j
public class PlaylistCreatedEvent extends ApplicationEvent {
    private final Playlist playlist;

    public PlaylistCreatedEvent(Object source, Playlist playlist) {
        super(source);
        this.playlist = playlist;
        log.info("Playlist created: {}", playlist.getName());
    }
}
