package ru.yandex.practicum.filmorate.model;


import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void userIsValid() {
        user = new User(1, "test@test.ru", "Login", "Name", LocalDate.parse("1990-12-24"));
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertEquals(0, constraintViolations.size());
    }

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

