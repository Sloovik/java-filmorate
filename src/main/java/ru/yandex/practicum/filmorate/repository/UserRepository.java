package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository for user controller
 */
@Repository
public class UserRepository {
    private final Map<Integer, User> users = new HashMap<>();
    private int nextUserId = 1;

    public User create(User user) {
        checkUserName(user);
        User newUser = user
                .toBuilder()
                .id(nextUserId++)
                .build();

        users.put(newUser.getId(), newUser);
        return newUser;
    }

    public List<User> read() {
        return List.copyOf(users.values());
    }

    public User update(User user) {
        User existingUser = users.get(user.getId());
        if (existingUser == null) {
            throw new ValidationException("Несуществующий id пользователя");
        }
        checkUserName(user);

        existingUser.setEmail(user.getEmail());
        existingUser.setLogin(user.getLogin());
        existingUser.setName(user.getName());
        existingUser.setBirthday(user.getBirthday());
        return existingUser;
    }

    private void checkUserName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
