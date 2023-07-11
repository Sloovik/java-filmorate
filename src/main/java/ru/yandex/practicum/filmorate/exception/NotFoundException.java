package ru.yandex.practicum.filmorate.exception;

/**
 * Exception for not found objects
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(final String message) {
        super(message);
    }
}