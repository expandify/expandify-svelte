package dev.kenowi.exportify.domain.events;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.util.Collection;
import java.util.UUID;

@Getter
@Slf4j
public class TrackIDsLoaded extends ApplicationEvent {
    private final Collection<String> trackIDs;
    private final UUID albumID;

    public TrackIDsLoaded(Object source, Collection<String> trackIDs, UUID albumID) {
        super(source);
        this.trackIDs = trackIDs;
        this.albumID = albumID;
    }

    public TrackIDsLoaded(Object source, Collection<String> trackIDs) {
        this(source, trackIDs, null);
    }
}
