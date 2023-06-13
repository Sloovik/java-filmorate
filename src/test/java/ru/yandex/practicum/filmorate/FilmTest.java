package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.ValidateService;

import java.time.LocalDate;

public class FilmTest {

    @Test
    void create() {
        Film film = new Film(1, "name", "description", LocalDate.of(1950, 10, 27), 80);
        Assertions.assertEquals(film.getId(), 1);
        Assertions.assertEquals(film.getName(), "name");
        Assertions.assertEquals(film.getDescription(), "description");
        Assertions.assertEquals(film.getDuration(), 80);
        Assertions.assertEquals(film.getReleaseDate(), LocalDate.of(1950, 10, 27));
    }

    @Test
    void incorrectFilmTest() {
        Film film = new Film(1, null, "description", LocalDate.of(1950, 10, 27), 80);
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> ValidateService.validateMovie(film));
        Assertions.assertEquals("Ошибка в названии фильма", exception.getMessage());
    }

    @Test
    void incorrectDescriptionTest() {
        Film film = new Film(1, "name", "/////////////////////////////////////////////////////////" +
                "//////////////////////////////////////////////////////////////////////////////////////////////////////" +
                "/////////////////////////////////////////////////////////////////", LocalDate.of(1950, 10, 27), 80);
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> ValidateService.validateMovie(film));
        Assertions.assertEquals("Ошибка в описании к фильму", exception.getMessage());
    }

    @Test
    void incorrectReleaseDateTest() {
        Film film = new Film(1, "name", "description", LocalDate.of(1350, 10, 27), 80);
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> ValidateService.validateMovie(film));
        Assertions.assertEquals("Ошибка в дате фильма", exception.getMessage());
    }

    @Test
    void incorrectDurationTest() {
        Film film = new Film(1, "name", "description", LocalDate.of(1950, 10, 27), -1);
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> ValidateService.validateMovie(film));
        Assertions.assertEquals("Ошибка в продолжительности фильма", exception.getMessage());
    }
}
