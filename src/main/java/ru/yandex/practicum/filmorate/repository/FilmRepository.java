package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Repository for film controller
 */
@Repository
public class FilmRepository implements CrudRepository<Film> {
    private final Map<Long, Film> films = new HashMap<>();
    private Long nextFilmId = 1L;

    @Override
    public Film create(Film film) {
        Film newFilm = film
                .toBuilder()
                .id(nextFilmId++)
                .likedUsers(new HashSet<>())
                .build();
        films.put(newFilm.getId(), newFilm);
        return newFilm;
    }

    @Override
    public List<Film> getAll() {
        return List.copyOf(films.values());
    }

    @Override
    public Film update(Film film) {
        Film existingFilm = films.get(film.getId());
        if (existingFilm == null) {
            throw new NotFoundException("Несуществующий id фильма");
        }
        if (film.getName() != null) {
            existingFilm.setName(film.getName());
        }
        if (film.getDescription() != null) {
            existingFilm.setDescription(film.getDescription());
        }
        if (film.getDuration() != null) {
            existingFilm.setDuration(film.getDuration());
        }
        if (film.getReleaseDate() != null) {
            existingFilm.setReleaseDate(film.getReleaseDate());
        }

        return existingFilm;
    }

    @Override
    public Film getById(Long id) {
        Film film = films.get(id);
        if (film == null) {
            throw new NotFoundException("Несуществующий id фильма: " + id);
        }
        return film;
    }
}