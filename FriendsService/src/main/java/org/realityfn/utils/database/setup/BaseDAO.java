package org.realityfn.utils.database.setup;

import java.util.List;
import java.util.Optional;

public interface BaseDAO<T> {
    T save(T entity);
    Optional<T> findById(String id);
    List<T> findAll();
    T update(String id, T entity);
    boolean delete(String id);
    List<T> findByField(String field, Object value);
}
