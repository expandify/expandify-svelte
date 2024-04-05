package dev.kenowi.exportify.domain.events;

import dev.kenowi.exportify.domain.entities.Album;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlbumLoadedEvent {
    private Album album;
}
