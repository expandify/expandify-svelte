package de.wittenbude.exportify.models.embeds;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Followers {
    private String href;
    private Integer total;
}
