package de.wittenbude.exportify.domain.events;

import de.wittenbude.exportify.domain.entities.Album;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlbumLoadedEvent {
    private Album album;
}
