package dev.kenowi.exportify.domain.events;

import dev.kenowi.exportify.infrastructure.spotify.data.SpotifyArtist;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ArtistsLoadedEvent {
    private List<SpotifyArtist> artists;
}
