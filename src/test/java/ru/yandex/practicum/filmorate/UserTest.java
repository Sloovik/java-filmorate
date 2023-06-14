package ru.yandex.practicum.filmorate;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

//public class UserTest {
//
////    private static Validator validator;
////    static {
////        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
////        validator = validatorFactory.usingContext().getValidator();
////    }
//
////    @Test
////    void validateName() {
////        User user = new User();
////        user.setName("");
////
////        Set<ConstraintViolation<User>> violations = validator.validate(user);
////        Assertions.assertEquals(0, violations.size(), "Name is empty");
////    }
//
//    @Test
//    void create() {
//        User user = new User(1, "name@email.ru", "login", "name", LocalDate.of(1950, 10, 27));
//        Assertions.assertEquals(user.getId(), 1);
//        Assertions.assertEquals(user.getName(), "name");
//        Assertions.assertEquals(user.getEmail(), "name@email.ru");
//        Assertions.assertEquals(user.getLogin(), "login");
//        Assertions.assertEquals(user.getBirthday(), LocalDate.of(1950, 10, 27));
//    }
//
//    @Test
//    void incorrectEmailTest() {
//        User user = new User(0, null, "login", "name", LocalDate.of(1950, 10, 27));
//        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> ValidateService.validateUser(user));
//        Assertions.assertEquals("Ошибка в электронной почте", exception.getMessage());
//    }
//
//    @Test
//    void incorrectLoginTest() {
//        User user = new User(0, "name@email.ru", null, "name", LocalDate.of(1950, 10, 27));
//        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> ValidateService.validateUser(user));
//        Assertions.assertEquals("Ошибка в логине", exception.getMessage());
//    }
//
//    @Test
//    void incorrectBirthdayTest() {
//        User user = new User(0, "name@email.ru", "login", "name", LocalDate.of(2050, 10, 27));
//        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> ValidateService.validateUser(user));
//        Assertions.assertEquals("Ошибка в дате рождения", exception.getMessage());
//    }
//
//}
@DisplayName("Пользователь должен:")
public class UserTest {
    private Set<ConstraintViolation<User>> validates;
    private ConstraintViolation<User> validate;
    private User user;
    private static final Validator validator;

    static {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            validator = validatorFactory.usingContext().getValidator();
        }
    }

    @DisplayName("валидировать email")
    @Test
    public void shouldValidateEmail() {
        user = new User(1, null, "Login", "Name", LocalDate.parse("1990-12-24"));

        validates = validator.validate(user);
        validate = validates.iterator().next();
        assertEquals(NotEmpty.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "валидация на null");
        assertEquals("email", validate.getPropertyPath().toString(), "валидация на null, property");

        user = new User(1, "", "Login", "Name", LocalDate.parse("1990-12-24"));
        validates = validator.validate(user);
        validate = validates.iterator().next();
        assertEquals(NotEmpty.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "валидация на empty");
        assertEquals("email", validate.getPropertyPath().toString(), "валидация на empty, property");

        user = new User(1, "test.ru", "Login", "Name", LocalDate.parse("1990-12-24"));
        validates = validator.validate(user);
        validate = validates.iterator().next();
        assertEquals(Email.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "не совпадает формат адреса электронной почты");
        assertEquals("email", validate.getPropertyPath().toString(), "вне совпадает формат адреса электронной почты, property");
    }

    @DisplayName("валидировать логин")
    @Test
    public void shouldValidateLogin() {
        user = new User(1, "test@test.ru", null, "Name", LocalDate.parse("1990-12-24"));
        validates = validator.validate(user);
        validate = validates.iterator().next();
        assertEquals(NotNull.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "валидация на null");
        assertEquals("login", validate.getPropertyPath().toString(), "валидация на null, property");

        user = new User(1, "test@test.ru", "", "Name", LocalDate.parse("1990-12-24"));
        validates = validator.validate(user);
        validate = validates.iterator().next();
        assertEquals(Pattern.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "валидация на empty");
        assertEquals("login", validate.getPropertyPath().toString(), "валидация на empty, property");

        user = new User(1, "test@test.ru", " ", "Name", LocalDate.parse("1990-12-24"));
        validates = validator.validate(user);
        validate = validates.iterator().next();
        assertEquals(Pattern.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "валидация на blank");
        assertEquals("login", validate.getPropertyPath().toString(), "валидация на blank, property");

        user = new User(1, "test@test.ru", "Login with spaces", "Name", LocalDate.parse("1990-12-24"));
        validates = validator.validate(user);
        validate = validates.iterator().next();
        assertEquals(Pattern.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "содержит пробелы");
        assertEquals("login", validate.getPropertyPath().toString(), "содержит пробелы, property");
    }

    @DisplayName("валидировать дату рождения")
    @Test
    public void shouldValidateBirthday() {
        user = new User(1, "test@test.ru", "Login", "Name", null);

        validates = validator.validate(user);
        validate = validates.iterator().next();
        assertEquals(NotNull.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "валидация на null");
        assertEquals("birthday", validate.getPropertyPath().toString(), "валидация на null, property");

        user = new User(1, "test@test.ru", "Login", "Name", LocalDate.parse("2100-12-24"));
        validates = validator.validate(user);
        validate = validates.iterator().next();
        assertEquals(PastOrPresent.class, validate.getConstraintDescriptor().getAnnotation().annotationType(), "будущая дата");
        assertEquals("birthday", validate.getPropertyPath().toString(), "будущая дата, property");
    }
}

