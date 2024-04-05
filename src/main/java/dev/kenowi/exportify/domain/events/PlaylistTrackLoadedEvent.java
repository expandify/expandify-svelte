package dev.kenowi.exportify.domain.events;

import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyPlaylistTrackItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaylistTrackLoadedEvent {
    private SpotifyPlaylistTrackItem track;
}
