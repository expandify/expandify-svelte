package dev.kenowi.exportify.infrastructure.spotify.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

@Getter
@Setter
public class SpotifyPage<T> implements Iterable<T> {


    @JsonProperty("href")
    private String href;

    @JsonProperty("limit")
    private int limit;

    @JsonProperty("next")
    private String next;

    @JsonProperty("offset")
    private int offset;

    @JsonProperty("previous")
    private String previous;

    @JsonProperty("total")
    private int total;

    @JsonProperty("items")
    private List<T> items;

    public static <T> SpotifyPage<T> empty() {
        SpotifyPage<T> spotifyPage = new SpotifyPage<>();
        spotifyPage.href = null;
        spotifyPage.limit = 0;
        spotifyPage.next = null;
        spotifyPage.offset = 0;
        spotifyPage.previous = null;
        spotifyPage.total = 0;
        spotifyPage.items = new ArrayList<>();
        return spotifyPage;
    }

    public static <T> Stream<T> streamPagination(Function<Integer, SpotifyPage<T>> pageSupplier) {
        SpotifyPage<T> firstPage = pageSupplier.apply(null);
        if (firstPage == null || !firstPage.hasContent()) {
            return Stream.empty();
        }

        return Stream.iterate(firstPage, p -> !p.isEmpty(), p -> p.hasNext() ? pageSupplier.apply(p.getOffset() + p.getLimit()) : SpotifyPage.empty())
                .flatMap(p -> p.getItems().stream());
    }

    @Override
    @NonNull
    public Iterator<T> iterator() {
        return items.iterator();
    }

    boolean isEmpty() {
        return !iterator().hasNext();
    }

    public boolean hasNext() {
        return next != null;
    }

    public boolean hasContent() {
        return total > 0 && items != null && !items.isEmpty();
    }
}
