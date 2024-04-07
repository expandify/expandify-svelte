package dev.kenowi.exportify.domain.events;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.util.Collection;

@Getter
@Slf4j
public class ArtistIDsLoaded extends ApplicationEvent {
    private final Collection<String> artistsIDs;

    public ArtistIDsLoaded(Object source, Collection<String> artistsIDs) {
        super(source);
        this.artistsIDs = artistsIDs;
    }
}
