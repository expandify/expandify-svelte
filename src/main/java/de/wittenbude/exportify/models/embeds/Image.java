package de.wittenbude.exportify.models.embeds;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Embeddable
public class Image {

    private String url;
    private Integer height;
    private Integer width;

}
