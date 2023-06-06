package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private List<User> users = new ArrayList<>();

    @PutMapping(path = "/users")
    public User add(@Valid @RequestBody User user) {
        users.add(user);
        return user;
    }

    @PatchMapping(path = "/users/{id}")
    public User update(@Valid @RequestBody User user) {
        return users.get(user.getId());
    }

    @GetMapping(path = "/users")
    public List<User> get() {
        return users;
    }

}
