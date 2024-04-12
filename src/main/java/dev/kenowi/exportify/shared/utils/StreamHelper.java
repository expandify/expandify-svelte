package dev.kenowi.exportify.shared.utils;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class StreamHelper {

    public static <T> Collector<T, List<List<T>>, Stream<List<T>>> chunked(int chunkSize) {
        return Collector.of(
                ArrayList::new,
                (outerList, item) -> {
                    if (outerList.isEmpty() || outerList.getLast().size() >= chunkSize) {
                        outerList.add(new ArrayList<>(chunkSize));
                    }
                    outerList.getLast().add(item);
                },
                (a, b) -> {
                    a.addAll(b);
                    return a;
                },
                List::stream
        );
    }

    public static <T> Collector<T, List<Set<T>>, Stream<Set<T>>> chunkedSet(int chunkSize) {
        return Collector.of(
                ArrayList::new,
                (outerList, item) -> {
                    if (outerList.isEmpty() || outerList.getLast().size() >= chunkSize) {
                        outerList.add(new HashSet<>(chunkSize));
                    }
                    outerList.getLast().add(item);
                },
                (a, b) -> {
                    a.addAll(b);
                    return a;
                },
                Collection::stream
        );
    }

}
