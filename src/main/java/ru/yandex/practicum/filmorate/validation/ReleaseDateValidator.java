package ru.yandex.practicum.filmorate.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.time.LocalDate;

/**
 * Tip from webinar with mentor
 */
public class ReleaseDateValidator implements
        ConstraintValidator<ReleaseDateConstraint, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext cxt) {
        if (value == null) {
            return true;
        }
        return value.isAfter(LocalDate.of(1895, 12, 28));
    }

}