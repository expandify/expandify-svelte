package de.wittenbude.exportify.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
public class PublicUser {
    private UUID id;
    private String displayName;
    private Map<String, String> externalUrls;
    private Followers followers;
    private String href;
    private String spotifyID;
    private List<Image> images;
    private ObjectType type;
    private String uri;
}
