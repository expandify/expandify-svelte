package dev.kenowi.exportify.domain.service.playlist;

import dev.kenowi.exportify.domain.entities.Playlist;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Getter
@Slf4j
public class PlaylistCreatedEvent extends ApplicationEvent {
    private final Playlist playlist;

    PlaylistCreatedEvent(Playlist playlist) {
        super(PlaylistEventService.class);
        this.playlist = playlist;
        log.info("Playlist created: {}", playlist.getName());
    }
}
