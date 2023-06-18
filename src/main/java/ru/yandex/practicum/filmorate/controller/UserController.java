package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;

/**
 * Endpoints for users
 */
@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserRepository repository;

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) throws ValidationException {
        log.info("Post request to create user, {}", user);
        return repository.create(user);
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) throws ValidationException {
        log.info("Put request to update user, {}", user);
        int id = user.getId();
        if (id == 0) {
            throw new ValidationException("Ошибка в id пользователя");
        }
        return repository.update(user);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return repository.read();
    }

}
