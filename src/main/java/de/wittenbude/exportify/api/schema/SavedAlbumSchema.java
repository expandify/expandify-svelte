package de.wittenbude.exportify.api.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.album.api.SavedAlbum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class SavedAlbumSchema {
    @JsonProperty("saved_at")
    Instant savedAt;
    @JsonProperty("album")
    AlbumSchema album;

    public static SavedAlbumSchema from(SavedAlbum album) {
        return new SavedAlbumSchema(
                album.getSavedAt(),
                AlbumSchema.from(album.getAlbum()));
    }

    public static Collection<SavedAlbumSchema> from(Set<SavedAlbum> savedAlbums) {
        return savedAlbums
                .stream()
                .map(SavedAlbumSchema::from)
                .collect(Collectors.toSet());
    }
}


