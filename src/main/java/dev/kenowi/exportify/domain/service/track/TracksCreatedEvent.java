package dev.kenowi.exportify.domain.service.track;

import dev.kenowi.exportify.domain.entities.Track;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class TracksCreatedEvent extends ApplicationEvent {
    private final List<TrackData> tracks;

    private TracksCreatedEvent(Object source, List<Track> trackEvents, boolean albumsLoaded) {
        super(source);
        this.tracks = trackEvents
                .stream()
                .map(track -> new TrackData(track, albumsLoaded))
                .toList();
    }

    TracksCreatedEvent(List<Track> trackEvents) {
        this(TrackEventService.class, trackEvents, false);
    }

    TracksCreatedEvent(List<Track> trackEvents, boolean albumsLoaded) {
        this(TrackEventService.class, trackEvents, albumsLoaded);
    }

    public record TrackData(Track track, boolean albumLoaded) { }
}
