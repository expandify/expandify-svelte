package de.wittenbude.exportify.api.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.track.api.SavedTrack;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class SavedTrackSchema {
    @JsonProperty("saved_at")
    Instant savedAt;
    @JsonProperty("album")
    TrackSchema track;

    public static SavedTrackSchema from(SavedTrack track) {
        return new SavedTrackSchema(
                track.getSavedAt(),
                TrackSchema.from(track.getTrack()));
    }

    public static Collection<SavedTrackSchema> from(Set<SavedTrack> savedTracks) {
        return savedTracks
                .stream()
                .map(SavedTrackSchema::from)
                .collect(Collectors.toSet());
    }
}


