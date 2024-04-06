package dev.kenowi.exportify.domain.service.album;

import dev.kenowi.exportify.domain.entities.Album;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AlbumCreatedEvent extends ApplicationEvent {
    private final Album album;

    private AlbumCreatedEvent(Object source, Album album) {
        super(source);
        this.album = album;
    }

    AlbumCreatedEvent(Album album) {
        this(AlbumEventService.class, album);
    }
}
