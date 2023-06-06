package ru.yandex.practicum.filmorate.controller;


import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FilmController {

    private List<Film> movies = new ArrayList<>();

    @PutMapping(path = "/movies")
    public Film addMovie(@Valid @RequestBody Film film) {
        movies.add(film);
        return film;
    }

    @RequestMapping(path = "movies/{id}", method = RequestMethod.PATCH)
    public Film update(@Valid @RequestBody Film film) {
        return movies.get(film.getId());
    }

    @GetMapping(path = "/movies")
    public List<Film> getMovies() {
        return movies;
    }

}
