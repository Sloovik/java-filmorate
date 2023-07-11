package ru.yandex.practicum.filmorate.repository;

import java.util.List;

/**
 * CRUD repo interface for films/users repos
 *
 * @param <T>
 */
public interface CrudRepository<T> {
    T create(T t);

    List<T> getAll();

    T update(T t);

    T getById(Long id);
}
