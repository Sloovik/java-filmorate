package ru.yandex.practicum.filmorate;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.ValidateService;

import java.time.LocalDate;

public class UserTest {

//    private static Validator validator;
//    static {
//        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
//        validator = validatorFactory.usingContext().getValidator();
//    }

//    @Test
//    void validateName() {
//        User user = new User();
//        user.setName("");
//
//        Set<ConstraintViolation<User>> violations = validator.validate(user);
//        Assertions.assertEquals(0, violations.size(), "Name is empty");
//    }

    @Test
    void create() {
        User user = new User(1L, "name@email.ru", "login", "name", LocalDate.of(1950, 10, 27));
        Assertions.assertEquals(user.getId(), 1L);
        Assertions.assertEquals(user.getName(), "name");
        Assertions.assertEquals(user.getEmail(), "name@email.ru");
        Assertions.assertEquals(user.getLogin(), "login");
        Assertions.assertEquals(user.getBirthday(), LocalDate.of(1950, 10, 27));
    }

    @Test
    void incorrectIdTest() {
        User user = new User(null, "name@email.ru", "login", "name", LocalDate.of(1950, 10, 27));
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> ValidateService.validateUser(user));
        Assertions.assertEquals("Ошибка в id", exception.getMessage());
    }

    @Test
    void incorrectEmailTest() {
        User user = new User(0L, null, "login", "name", LocalDate.of(1950, 10, 27));
        ValidationException exception = Assertions.assertThrows(ValidationException.class, ()-> ValidateService.validateUser(user));
        Assertions.assertEquals("Ошибка в электронной почте", exception.getMessage());
    }

    @Test
    void incorrectLoginTest() {
        User user = new User(0L, "name@email.ru", null, "name", LocalDate.of(1950, 10, 27));
        ValidationException exception = Assertions.assertThrows(ValidationException.class, ()-> ValidateService.validateUser(user));
        Assertions.assertEquals("Ошибка в логине", exception.getMessage());
    }

    @Test
    void incorrectBirthdayTest() {
        User user = new User(0L, "name@email.ru", "login", "name", LocalDate.of(2050, 10, 27));
        ValidationException exception = Assertions.assertThrows(ValidationException.class, ()-> ValidateService.validateUser(user));
        Assertions.assertEquals("Ошибка в дате рождения", exception.getMessage());
    }

}
