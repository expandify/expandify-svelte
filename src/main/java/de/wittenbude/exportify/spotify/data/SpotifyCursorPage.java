package de.wittenbude.exportify.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.util.Streamable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

@Getter
@Setter
public class SpotifyCursorPage<T> implements Streamable<T> {


    @JsonProperty("href")
    private String href;

    @JsonProperty("limit")
    private int limit;

    @JsonProperty("next")
    private String next;

    @JsonProperty("cursors")
    private Cursors cursors;

    @JsonProperty("total")
    private int total;

    @JsonProperty("items")
    private List<T> items;

    public static <T> SpotifyCursorPage<T> empty() {
        SpotifyCursorPage<T> spotifyCursorPage = new SpotifyCursorPage<>();
        spotifyCursorPage.href = null;
        spotifyCursorPage.limit = 0;
        spotifyCursorPage.next = null;
        spotifyCursorPage.cursors = new Cursors();
        spotifyCursorPage.cursors.after = null;
        spotifyCursorPage.cursors.before = null;
        spotifyCursorPage.total = 0;
        spotifyCursorPage.items = new ArrayList<>();
        return spotifyCursorPage;
    }

    public static <T> Stream<T> streamPagination(Function<String, SpotifyCursorPage<T>> pageSupplier) {
        SpotifyCursorPage<T> firstPage = pageSupplier.apply(null);
        if (firstPage == null || !firstPage.hasContent()) {
            return Stream.empty();
        }

        return Stream.iterate(firstPage, p -> !p.isEmpty(), p -> p.hasNext() ? pageSupplier.apply(p.getCursors().getAfter()) : SpotifyCursorPage.empty())
                .flatMap(p -> p.getItems().stream());
    }

    @Override
    @NonNull
    public Iterator<T> iterator() {
        return items.iterator();
    }

    public boolean hasNext() {
        return next != null && cursors != null && cursors.after != null;
    }

    public boolean hasContent() {
        return total > 0 && items != null && !items.isEmpty();
    }



    @Getter
    @Setter
    public static class Cursors {
        @JsonProperty("after")
        private String after;

        @JsonProperty("before")
        private String before;
    }
}
