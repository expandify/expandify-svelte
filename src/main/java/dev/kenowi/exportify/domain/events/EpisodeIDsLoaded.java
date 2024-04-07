package dev.kenowi.exportify.domain.events;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.util.Collection;
import java.util.UUID;

@Getter
@Slf4j
public class EpisodeIDsLoaded extends ApplicationEvent {
    private final Collection<String> episodeIDs;
    private final UUID showID;

    public EpisodeIDsLoaded(Object source, Collection<String> episodeIDs, UUID showID) {
        super(source);
        this.episodeIDs = episodeIDs;
        this.showID = showID;
    }

    public EpisodeIDsLoaded(Object source, Collection<String> episodeIDs) {
        this(source, episodeIDs, null);
    }
}
