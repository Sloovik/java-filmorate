package ru.yandex.practicum.filmorate.exception;

/**
 * Exception for not found objects
 */
public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(final String message) {
        super(message);
    }
}