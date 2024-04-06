package dev.kenowi.exportify.domain.service.playlist;

import dev.kenowi.exportify.domain.entities.Playlist;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PlaylistCreatedEvent extends ApplicationEvent {
    private final Playlist playlist;

    private PlaylistCreatedEvent(Object source, Playlist playlist) {
        super(source);
        this.playlist = playlist;
    }

    PlaylistCreatedEvent(Playlist playlist) {
        this(PlaylistEventService.class, playlist);
    }
}
