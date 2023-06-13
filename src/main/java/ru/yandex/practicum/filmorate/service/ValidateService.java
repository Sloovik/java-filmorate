package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

/**
 * Basic validation class for movies and users
 */
@Component
@Slf4j
public class ValidateService {
    public static void validateUser(User user) throws ValidationException {
        if (user.getId() < 0) {
            log.warn("Id должен быть целочисленным", user);
            throw new ValidationException("Ошибка в id");
        }
        if (user.getLogin() == null || user.getLogin().isBlank()) {
            log.warn("Логин пустой: {}", user);
            throw new ValidationException("Ошибка в логине");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Ошибка в дате рождения");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.warn("Некорректный адрес электронной почты");
            throw new ValidationException("Ошибка в электронной почте");
        }
    }

    public static void validateMovie(Film film) throws ValidationException {
        if (film.getId() < 0) {
            log.warn("Id должен быть целочисленным", film);
            throw new ValidationException("Ошибка в id фильма");
        }
        if (film.getName() == null || film.getName().isBlank()) {
            log.warn("Название фильма пустое: {} ", film);
            throw new ValidationException("Ошибка в названии фильма");
        }
        if (film.getDescription().length() > 200) {
            log.warn("Длина описания не должна быть более 200 символов");
            throw new ValidationException("Ошибка в описании к фильму");
        }
        if (film.getDuration() <= 0) {
            log.warn("Продолжительность фильма {} должна быть положительной", film.getName());
            throw new ValidationException("Ошибка в продолжительности фильма");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("В то время кино еще не снимали");
            throw new ValidationException("Ошибка в дате фильма");
        }
    }
}