package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.ReleaseDateConstraint;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

//public class FilmTest {
//
//    @Test
//    void create() {
//        Film film = new Film(1, "name", "description", LocalDate.of(1950, 10, 27), 80);
//        Assertions.assertEquals(film.getId(), 1);
//        Assertions.assertEquals(film.getName(), "name");
//        Assertions.assertEquals(film.getDescription(), "description");
//        Assertions.assertEquals(film.getDuration(), 80);
//        Assertions.assertEquals(film.getReleaseDate(), LocalDate.of(1950, 10, 27));
//    }
//
//    @Test
//    void incorrectFilmTest() {
//        Film film = new Film(1, null, "description", LocalDate.of(1950, 10, 27), 80);
//        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> ValidateService.validateMovie(film));
//        Assertions.assertEquals("Ошибка в названии фильма", exception.getMessage());
//    }
//
//    @Test
//    void incorrectDescriptionTest() {
//        Film film = new Film(1, "name", "/////////////////////////////////////////////////////////" +
//                "//////////////////////////////////////////////////////////////////////////////////////////////////////" +
//                "/////////////////////////////////////////////////////////////////", LocalDate.of(1950, 10, 27), 80);
//        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> ValidateService.validateMovie(film));
//        Assertions.assertEquals("Ошибка в описании к фильму", exception.getMessage());
//    }
//
//    @Test
//    void incorrectReleaseDateTest() {
//        Film film = new Film(1, "name", "description", LocalDate.of(1350, 10, 27), 80);
//        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> ValidateService.validateMovie(film));
//        Assertions.assertEquals("Ошибка в дате фильма", exception.getMessage());
//    }
//
//    @Test
//    void incorrectDurationTest() {
//        Film film = new Film(1, "name", "description", LocalDate.of(1950, 10, 27), -1);
//        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> ValidateService.validateMovie(film));
//        Assertions.assertEquals("Ошибка в продолжительности фильма", exception.getMessage());
//    }
//}

public class FilmTest {
    private Set<ConstraintViolation<Film>> validates;
    private ConstraintViolation<Film> validate;
    private Film film;
    private static final Validator validator;

    static {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            validator = validatorFactory.usingContext().getValidator();
        }
    }


    @Test
    public void shouldValidateName() {
        film = new Film(1, null, "Description", LocalDate.parse("1990-12-24"), 120);
        validates = validator.validate(film);
        validate = validates.iterator().next();
        assertEquals(NotBlank.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "валидация на null");
        assertEquals("name", validate.getPropertyPath().toString(), "валидация на null, property");

        film = new Film(1, "", "Description", LocalDate.parse("1990-12-24"), 120);
        validates = validator.validate(film);
        validate = validates.iterator().next();
        assertEquals(NotBlank.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "валидация на empty");
        assertEquals("name", validate.getPropertyPath().toString(), "валидация на empty, property");

        film = new Film(1, "   ", "Description", LocalDate.parse("1990-12-24"), 120);
        validates = validator.validate(film);
        validate = validates.iterator().next();
        assertEquals(NotBlank.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "валидация на blank");
        assertEquals("name", validate.getPropertyPath().toString(), "валидация на blank, property");
    }

    @Test
    public void shouldValidateDescription() {
        film = new Film(1, "Name", null, LocalDate.parse("1990-12-24"), 120);
        validates = validator.validate(film);
        validate = validates.iterator().next();
        assertEquals(NotNull.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "валидация на null");
        assertEquals("description", validate.getPropertyPath().toString(), "валидация на null, property");

        film = new Film(1, "Name", "DescriptionDescriptionDescriptionDescriptionDescription" +
                "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription" +
                "DescriptionDescriptionDescriptionDescriptionDescription", LocalDate.parse("1990-12-24"), 120);
        validates = validator.validate(film);
        validate = validates.iterator().next();
        assertEquals(Size.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "описание более 200 символов");
        assertEquals("description", validate.getPropertyPath().toString(), "описание более 200 символов, property");
    }

    @Test
    public void shouldValidateReleaseDate() {
        film = new Film(1, "Name", "Description", null, 120);
        validates = validator.validate(film);
        validate = validates.iterator().next();
        assertEquals(ReleaseDateConstraint.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "валидация на null");
        assertEquals("releaseDate", validate.getPropertyPath().toString(), "валидация на null, property");

        film = new Film(1, "Name", "Description", LocalDate.parse("1800-12-24"), 120);
        validates = validator.validate(film);
        validate = validates.iterator().next();
        assertEquals(ReleaseDateConstraint.class, validate.getConstraintDescriptor().getAnnotation().annotationType(),
                "дата релиза фильма до дня рождения кино (1895-12-28)");
        assertEquals("releaseDate", validate.getPropertyPath().toString(),
                "дата релиза фильма до дня рождения кино (1895-12-28), property");
    }

    @DisplayName("валидировать продолжительность")
    @Test
    public void shouldValidateDuration() {
        film = new Film(1, "Name", "Description", LocalDate.parse("1990-12-24"), -1);
        validates = validator.validate(film);
        validate = validates.iterator().next();
        assertEquals(Positive.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "продолжительность <= 0");
        assertEquals("duration", validate.getPropertyPath().toString(), "продолжительность <= 0, property");

        film = new Film(1, "Name", "Description", LocalDate.parse("1990-12-24"), 0);
        validates = validator.validate(film);
        validate = validates.iterator().next();
        assertEquals(Positive.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "продолжительность <= 0");
        assertEquals("duration", validate.getPropertyPath().toString(), "продолжительность <= 0, property");
    }
}

