package de.wittenbude.expandify.models.db;


import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.expandify.models.pojos.Copyright;
import de.wittenbude.expandify.models.pojos.Image;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import se.michaelthelin.spotify.enums.ModelObjectType;

import java.util.Arrays;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "shows.infos")
public class ShowInfo {
    private CountryCode[] availableMarkets;
    private Copyright[] copyrights;
    private String description;
    private Boolean explicit;
    private Map<String, String> externalUrls;
    private String href;

    @Id
    private String id;

    private Image[] images;
    private Boolean isExternallyHosted;
    private String[] languages;
    private String mediaType;
    private String name;
    private String publisher;
    private ModelObjectType type;
    private String uri;

    public ShowInfo(se.michaelthelin.spotify.model_objects.specification.ShowSimplified showSimplified) {
        this.availableMarkets = showSimplified.getAvailableMarkets();
        this.copyrights = Arrays.stream(showSimplified.getCopyrights()).map(Copyright::new).toArray(Copyright[]::new);
        this.description = showSimplified.getDescription();
        this.explicit = showSimplified.getExplicit();
        this.externalUrls = showSimplified.getExternalUrls().getExternalUrls();
        this.href = showSimplified.getHref();
        this.id = showSimplified.getId();
        this.images = Arrays.stream(showSimplified.getImages()).map(Image::new).toArray(Image[]::new);
        this.isExternallyHosted = showSimplified.getExternallyHosted();
        this.languages = showSimplified.getLanguages();
        this.mediaType = showSimplified.getMediaType();
        this.name = showSimplified.getName();
        this.publisher = showSimplified.getPublisher();
        this.type = showSimplified.getType();
        this.uri = showSimplified.getUri();
    }
}
