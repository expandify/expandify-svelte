package de.wittenbude.expandify.models.spotifydata;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;

import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "artistsSimplified")
public class SpotifyArtistSimplified {

    private Map<String, String> externalUrls;
    private String href;
    @Id
    private String id;
    private String name;
    private ModelObjectType type;
    private String uri;

    public SpotifyArtistSimplified(ArtistSimplified artistSimplified) {
        this.externalUrls = artistSimplified.getExternalUrls().getExternalUrls();
        this.href = artistSimplified.getHref();
        this.id = artistSimplified.getId();
        this.name = artistSimplified.getName();
        this.type = artistSimplified.getType();
        this.uri = artistSimplified.getUri();
    }
}
