package ru.yandex.practicum.filmorate.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.util.Date;

public class ReleaseDateValidator implements
        ConstraintValidator<ReleaseDateConstraint, LocalDate> {

//    @Override
//    public void initialize(ReleaseDateConstraint contactNumber) {
//    }

    @Override
    public boolean isValid(LocalDate value,
                           ConstraintValidatorContext cxt) {
        return value.isAfter(LocalDate.of(1895, 12, 28));
    }

}