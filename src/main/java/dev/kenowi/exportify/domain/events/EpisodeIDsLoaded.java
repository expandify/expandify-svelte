package dev.kenowi.exportify.domain.events;

import dev.kenowi.exportify.domain.entities.Playlist;
import dev.kenowi.exportify.domain.entities.valueobjects.PlaylistTrack;
import dev.kenowi.exportify.domain.entities.valueobjects.SpotifyObjectType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.util.Collection;
import java.util.List;

@Getter
@Slf4j
public class EpisodeIDsLoaded extends ApplicationEvent {
    private final Collection<String> episodeIDs;
    private final boolean showLoaded;

    private EpisodeIDsLoaded(Object source, Collection<String> episodeIDs, boolean showLoaded) {
        super(source);
        this.episodeIDs = episodeIDs;
        this.showLoaded = showLoaded;
    }

    public static EpisodeIDsLoaded fromPlaylists(Object source, Collection<Playlist> playlists) {
        List<String> trackIDs = playlists
                .stream()
                .map(Playlist::getTracks)
                .flatMap(List::stream)
                .filter(t -> SpotifyObjectType.EPISODE.equals(t.getSpotifyObjectType()))
                .map(PlaylistTrack::getSpotifyTrackID)
                .toList();
        return new EpisodeIDsLoaded(source, trackIDs, false);
    }
}
