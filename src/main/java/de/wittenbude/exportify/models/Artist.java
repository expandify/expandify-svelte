package de.wittenbude.exportify.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
public class Artist {
    private UUID id;
    private Map<String, String> externalUrls;
    private Followers followers;
    private List<String> genres;
    private String href;
    private String spotifyID;
    private List<Image> images;
    private String name;
    private Integer popularity;
    private ObjectType type;
    private String uri;

}
