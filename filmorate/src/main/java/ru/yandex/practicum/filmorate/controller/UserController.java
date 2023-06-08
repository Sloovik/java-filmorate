package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.ValidateService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Endpoints for users
 */
@RestController
@RequestMapping
@Slf4j
public class UserController {

    private final List<User> users = new ArrayList<>();

    final ValidateService validateService;

    public UserController(ValidateService validateService) {
        this.validateService = validateService;
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) throws ValidationException {
        log.info("Create user: {} - Started", user);
        ValidateService.validateUser(user);
        users.add(user);
        log.info("Create user: {} - Finished", user);
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) throws ValidationException {
        log.info("Update user: {} - Started", user);
        ValidateService.validateUser(user);
        users.add(user);
        log.info("Update user: {} - Finished", user);
        return user;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return users;
    }

}
