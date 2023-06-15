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
        log.info("Create user: {} - Started", user);
        repository.create(user);
        log.info("Create user: {} - Finished", user);
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) throws ValidationException {
        log.info("Update user: {} - Started", user);
        int id = user.getId();
        if (id < 0) {
            throw new ValidationException("Ошибка в id пользователя");
        }
        User updatedUser = repository.update(user);
        log.info("Update user: {} - Finished", user);
        return updatedUser;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return repository.read();
    }

}
