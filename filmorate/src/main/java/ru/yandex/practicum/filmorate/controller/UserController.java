package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import ru.yandex.practicum.filmorate.service.ValidateService;
import ru.yandex.practicum.filmorate.validation.ReleaseDateValidator;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
@Slf4j
public class UserController {

    private final List<User> users = new ArrayList<>(); // исходя из условия тз, данные о пользователях храним пока тут?

    final ValidateService validateService;
    
    public UserController(ValidateService validateService) {
        this.validateService = validateService;
    }
    @PostMapping()
    public User createUser(@Valid @RequestBody User user) {
        log.info("Create user: {} - Started", user);
        validateService.validateUser(user);
        users.add(user);
        log.info("Create user: {} - Finished", user);
        return user;
    }

    @PutMapping(path = "/{id}")
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Update user: {} - Started", user);
        users.add(user);
        log.info("Update user: {} - Finished", user);
        return user;
    }

    @GetMapping()
    public List<User> getUsers() {
        return users;
    }

}
