package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Test;
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
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void filmIsValid() {
        Set<Long> users = new HashSet<>();
        users.add(1L);

        film = new Film(1L, "Name", "Description", LocalDate.parse("1990-12-24"), 120, users, 1);
        Set<ConstraintViolation<Film>> constraintViolations = validator.validate(film);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void shouldValidateName() {
        Set<Long> users = new HashSet<>();
        users.add(1L);
        film = new Film(1L, null, "Description", LocalDate.parse("1990-12-24"), 120, users, 1);
        validates = validator.validate(film);
        validate = validates.iterator().next();
        assertEquals(NotBlank.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "валидация на null");
        assertEquals("name", validate.getPropertyPath().toString(), "валидация на null, property");

        film = new Film(1L, "", "Description", LocalDate.parse("1990-12-24"), 120, users, 1);
        validates = validator.validate(film);
        validate = validates.iterator().next();
        assertEquals(NotBlank.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "валидация на empty");
        assertEquals("name", validate.getPropertyPath().toString(), "валидация на empty, property");

        film = new Film(1L, "   ", "Description", LocalDate.parse("1990-12-24"), 120, users, 1);
        validates = validator.validate(film);
        validate = validates.iterator().next();
        assertEquals(NotBlank.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "валидация на blank");
        assertEquals("name", validate.getPropertyPath().toString(), "валидация на blank, property");
    }

    @Test
    public void shouldValidateDescription() {
        Set<Long> users = new HashSet<>();
        users.add(1L);
        film = new Film(1L, "Name", null, LocalDate.parse("1990-12-24"), 120, users, 1);
        validates = validator.validate(film);
        validate = validates.iterator().next();
        assertEquals(NotNull.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "валидация на null");
        assertEquals("description", validate.getPropertyPath().toString(), "валидация на null, property");

        film = new Film(1L, "Name", "DescriptionDescriptionDescriptionDescriptionDescription" +
                "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription" +
                "DescriptionDescriptionDescriptionDescriptionDescription", LocalDate.parse("1990-12-24"), 120, users, 1);
        validates = validator.validate(film);
        validate = validates.iterator().next();
        assertEquals(Size.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "описание более 200 символов");
        assertEquals("description", validate.getPropertyPath().toString(), "описание более 200 символов, property");
    }

    @Test
    public void shouldValidateReleaseDate() {
        Set<Long> users = new HashSet<>();
        users.add(1L);
        film = new Film(1L, "Name", "Description", null, 120, users, 1);
        validates = validator.validate(film);
        validate = validates.iterator().next();
        assertEquals(ReleaseDateConstraint.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "валидация на null");
        assertEquals("releaseDate", validate.getPropertyPath().toString(), "валидация на null, property");

        film = new Film(1L, "Name", "Description", LocalDate.parse("1800-12-24"), 120, users, 1);
        validates = validator.validate(film);
        validate = validates.iterator().next();
        assertEquals(ReleaseDateConstraint.class, validate.getConstraintDescriptor().getAnnotation().annotationType(),
                "дата релиза фильма до дня рождения кино (1895-12-28)");
        assertEquals("releaseDate", validate.getPropertyPath().toString(),
                "дата релиза фильма до дня рождения кино (1895-12-28), property");
    }

    @Test
    public void shouldValidateDuration() {
        Set<Long> users = new HashSet<>();
        users.add(1L);
        film = new Film(1L, "Name", "Description", LocalDate.parse("1990-12-24"), -1, users, 1);
        validates = validator.validate(film);
        validate = validates.iterator().next();
        assertEquals(Positive.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "продолжительность <= 0");
        assertEquals("duration", validate.getPropertyPath().toString(), "продолжительность <= 0, property");

        film = new Film(1L, "Name", "Description", LocalDate.parse("1990-12-24"), 0, users, 1);
        validates = validator.validate(film);
        validate = validates.iterator().next();
        assertEquals(Positive.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "продолжительность <= 0");
        assertEquals("duration", validate.getPropertyPath().toString(), "продолжительность <= 0, property");
    }
}

