package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * Basic exception for invalid user or film data
 */
@Slf4j
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
        log.error(message);
    }

}
