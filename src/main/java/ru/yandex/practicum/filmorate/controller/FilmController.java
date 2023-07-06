package ru.yandex.practicum.filmorate.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Endpoints for films
 */
@RestController
@RequestMapping("films")
@Slf4j
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @PostMapping()
    public Film createMovie(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Post request to create film, {}", film);
        return filmService.createFilm(film);
    }

    @PutMapping()
    public Film updateMovie(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Put request to update film, {}", film);
        Long id = film.getId();
        if (id == 0) {
            throw new ValidationException("Incorrect film id");
        }
        return filmService.updateFilm(film);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) {
        log.info("Get request to get film: {}", id);
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Put request to add like: {} from user: {}", id, userId);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Delete request to delete like: {} from user: {}", id, userId);
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getMostPopularFilms(@RequestParam(defaultValue = "10") @Positive Integer count) {
        return filmService.getMostPopularFilms(count);
    }

    @GetMapping()
    public List<Film> getMovies() {
        return filmService.getFilms();
    }

}
