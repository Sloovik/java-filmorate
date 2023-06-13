package ru.yandex.practicum.filmorate.controller;


import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
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

    private final List<Film> movies = new ArrayList<>();

    @PostMapping()
    public Film createMovie(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Create movie: {} - Started", film);
        ValidateService.validateMovie(film);
        movies.add(film);
        log.info("Create movie: {} - Finished", film);
        return film;
    }

    @PutMapping()
    public Film updateMovie(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Update movie: {} - Started", film);
        ValidateService.validateMovie(film);
        movies.add(film);
        log.info("Update movie: {} - Finished", film);
        return film;
    }

    @GetMapping()
    public List<Film> getMovies() {
        return movies;
    }

}
