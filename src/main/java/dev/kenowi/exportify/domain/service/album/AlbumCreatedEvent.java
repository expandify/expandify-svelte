package dev.kenowi.exportify.domain.service.album;

import dev.kenowi.exportify.domain.entities.Album;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Getter
@Slf4j
public class AlbumCreatedEvent extends ApplicationEvent {
    private final Album album;

    AlbumCreatedEvent(Album album) {
        super(AlbumEventService.class);
        this.album = album;
        log.info("Album created: {}", album.getName());
    }


}
