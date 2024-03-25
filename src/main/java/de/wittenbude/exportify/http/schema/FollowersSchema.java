package de.wittenbude.exportify.http.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wittenbude.exportify.models.Followers;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowersSchema {

    @JsonProperty("href")
    private final String href;

    @JsonProperty("total")
    private final Integer total;


    public static FollowersSchema from(Followers followers) {
        return FollowersSchema
                .builder()
                .total(followers.getTotal())
                .href(followers.getHref())
                .build();
    }
}
