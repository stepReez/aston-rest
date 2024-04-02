package org.aston.task.repository;

import org.aston.task.exceptions.NotFoundException;

import java.util.List;

public interface Repository<T, K> {
    T findById(K id);

    boolean deleteById(K id);

    List<T> findAll();

    T save(T t);

    T update(T t, K k);

    void check(K id) throws NotFoundException;
}
