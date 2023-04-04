package de.wittenbude.expandify.models.pojos;

import lombok.*;

@Data
@NoArgsConstructor
public class Followers {
    private String href;
    private Integer total;

    public Followers(se.michaelthelin.spotify.model_objects.specification.Followers followers) {
        this.href = followers.getHref();
        this.total = followers.getTotal();
    }
}
