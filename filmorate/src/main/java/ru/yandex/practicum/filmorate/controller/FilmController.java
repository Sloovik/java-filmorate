package ru.yandex.practicum.filmorate.controller;


import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies")
@Slf4j
public class FilmController {

    private final List<Film> movies = new ArrayList<>(); // исходя из условия тз, данные о фильмах храним пока тут?

    @PostMapping()
    public Film createMovie(@Valid @RequestBody Film film) {
        log.info("Create movie: {} - Started", film);
        movies.add(film);
        log.info("Create movie: {} - Finished", film);
        return film;
    }

    @PutMapping(path = "/{id}")
    public Film updateMovie(@Valid @RequestBody Film film) {
        log.info("Update movie: {} - Started", film);
        movies.add(film);
        log.info("Update movie: {} - Finished", film);
        return film;
    }

    @GetMapping()
    public List<Film> getMovies() {
        return movies;
    }

}
