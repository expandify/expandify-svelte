package dev.kenowi.exportify.domain.events;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.util.Collection;

@Getter
@Slf4j
public class AlbumIDsLoaded extends ApplicationEvent {
    private final Collection<String> albumIDs;

    public AlbumIDsLoaded(Object source, Collection<String> albumIDs) {
        super(source);
        this.albumIDs = albumIDs;
    }
}
