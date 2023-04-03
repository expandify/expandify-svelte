package de.wittenbude.expandify.models.spotifydata;


import de.wittenbude.expandify.models.spotifydata.helper.Image;
import de.wittenbude.expandify.models.spotifydata.helper.ResumePoint;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.enums.ReleaseDatePrecision;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "episode")
public class Episode {
    private String audioPreviewUrl;
    private String description;
    private Integer durationMs;
    private Boolean explicit;
    private Map<String, String> externalUrls;
    private String href;

    @Id
    private String id;

    private Image[] images;
    private Boolean isExternallyHosted;
    private Boolean isPlayable;
    private String[] languages;
    private String name;
    private String releaseDate;
    private ReleaseDatePrecision releaseDatePrecision;
    private ResumePoint resumePoint;

    @DocumentReference(lazy = true)
    private ShowSimplified show;

    private ModelObjectType type;
    private String uri;


    public Episode(se.michaelthelin.spotify.model_objects.specification.Episode episode) {
        this.audioPreviewUrl = episode.getAudioPreviewUrl();
        this.description = episode.getDescription();
        this.durationMs = episode.getDurationMs();
        this.explicit = episode.getExplicit();
        this.externalUrls = episode.getExternalUrls().getExternalUrls();
        this.href = episode.getHref();
        this.id = episode.getId();
        this.images = episode.getImages() == null ? null : Arrays.stream(episode.getImages()).map(Image::new).toArray(Image[]::new);
        this.isExternallyHosted = episode.getExternallyHosted();
        this.isPlayable = episode.getPlayable();
        this.languages = episode.getLanguages();
        this.name = episode.getName();
        this.releaseDate = episode.getReleaseDate();
        this.releaseDatePrecision = episode.getReleaseDatePrecision();
        this.resumePoint = episode.getResumePoint() == null ? null : new ResumePoint(episode.getResumePoint());
        this.show = episode.getShow() == null ? null : new ShowSimplified(episode.getShow());
        this.type = episode.getType();
        this.uri = episode.getUri();
    }
}
