package ru.yandex.practicum.filmorate.exception;

/**
 * Exception for invalid data
 */
public class InvalidValueException extends RuntimeException {
    public InvalidValueException(final String message) {
        super(message);
    }
}
