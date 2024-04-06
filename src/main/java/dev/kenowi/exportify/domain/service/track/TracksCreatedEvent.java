package dev.kenowi.exportify.domain.service.track;

import dev.kenowi.exportify.domain.entities.Track;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
@Slf4j
public class TracksCreatedEvent extends ApplicationEvent {
    private final List<TrackData> tracks;

    TracksCreatedEvent(List<Track> trackEvents, boolean albumsLoaded) {
        super(TrackEventService.class);
        this.tracks = trackEvents
                .stream()
                .map(track -> new TrackData(track, albumsLoaded))
                .toList();
        log.info("Tracks created {}", tracks.size());
    }

    TracksCreatedEvent(List<Track> trackEvents) {
        this(trackEvents, false);
    }

    public record TrackData(Track track, boolean albumLoaded) { }
}
