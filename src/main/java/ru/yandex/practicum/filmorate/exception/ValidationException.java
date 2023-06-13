package ru.yandex.practicum.filmorate.exception;

/**
 * Basic exception for invalid user or film data
 */
public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
    }

}
