package de.wittenbude.expandify.models.spotifydata.helper;

import lombok.Data;
import lombok.NoArgsConstructor;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.model_objects.specification.ExternalUrl;
import se.michaelthelin.spotify.model_objects.specification.TrackLink;

@Data
@NoArgsConstructor
public class SpotifyTrackLink {
    private ExternalUrl externalUrls;
    private String href;
    private String id;
    private ModelObjectType type;
    private String uri;

    public SpotifyTrackLink(TrackLink trackLink) {
        this.externalUrls = trackLink.getExternalUrls();
        this.href = trackLink.getHref();
        this.id = trackLink.getId();
        this.type = trackLink.getType();
        this.uri = trackLink.getUri();
    }
}
