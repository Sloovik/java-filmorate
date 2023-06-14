package ru.yandex.practicum.filmorate.controller;


import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.service.ValidateService;

import java.util.ArrayList;
import java.util.List;

/**
 * Endpoints for films
 */
@RestController
@RequestMapping("films")
@Slf4j
public class FilmController {

    private final FilmRepository repository;
    public FilmController(FilmRepository repository) {
        this.repository = repository;
    }

    @PostMapping()
    public Film createMovie(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Create movie: {} - Started", film);
        //ValidateService.validateMovie(film);
        repository.create(film);
        log.info("Create movie: {} - Finished", film);
        return film;
    }

    @PutMapping()
    public Film updateMovie(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Update movie: {} - Started", film);
        int id = film.getId();
        if (id == 0) {
            throw new ValidationException("Ошибка в id фильма");
        }
        //ValidateService.validateMovie(film);
        Film updatedFilm = repository.update(film);
        log.info("Update movie: {} - Finished", film);
        return updatedFilm;
    }

    @GetMapping()
    public List<Film> getMovies() {
        return repository.read();
    }

}
