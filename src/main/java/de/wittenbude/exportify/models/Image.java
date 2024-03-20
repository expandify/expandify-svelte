package de.wittenbude.exportify.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Image {
    private Integer height;
    private String url;
    private Integer width;
}
