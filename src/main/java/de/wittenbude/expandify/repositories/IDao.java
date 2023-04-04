package de.wittenbude.expandify.repositories;

import java.util.List;
import java.util.Optional;

public interface IDao<T> {
    Optional<T> find(String id);
    Optional<T> find(T t);
    T save(T t);
    List<T> saveAll(List<T> ts);

}
