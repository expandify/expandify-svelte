package de.wittenbude.exportify.domain.events;

import de.wittenbude.exportify.infrastructure.spotify.data.SpotifyPlaylistTrackItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaylistTrackLoadedEvent {
    private SpotifyPlaylistTrackItem track;
}
