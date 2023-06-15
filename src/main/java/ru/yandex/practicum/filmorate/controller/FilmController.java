package ru.yandex.practicum.filmorate.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

import javax.validation.Valid;
import java.util.List;

/**
 * Endpoints for films
 */
@RestController
@RequestMapping("films")
@Slf4j
@RequiredArgsConstructor
public class FilmController {

    private final FilmRepository repository;

    @PostMapping()
    public Film createMovie(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Create movie: {} - Started", film);
        repository.create(film);
        log.info("Create movie: {} - Finished", film);
        return film;
    }

    @PutMapping()
    public Film updateMovie(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Update movie: {} - Started", film);
        int id = film.getId();
        if (id < 0) {
            throw new ValidationException("Ошибка в id фильма");
        }
        Film updatedFilm = repository.update(film);
        log.info("Update movie: {} - Finished", film);
        return updatedFilm;
    }

    @GetMapping()
    public List<Film> getMovies() {
        return repository.read();
    }

}
