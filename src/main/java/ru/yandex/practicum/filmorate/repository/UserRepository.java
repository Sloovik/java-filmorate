package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Repository for user controller
 */
@Repository
public class UserRepository implements CrudRepository<User> {
    private final Map<Long, User> users = new HashMap<>();
    private Long nextUserId = 1L;

    @Override
    public User create(User user) {
        User newUser = user
                .toBuilder()
                .id(nextUserId++)
                .friends(new HashSet<>())
                .build();

        users.put(newUser.getId(), newUser);
        return newUser;
    }

    @Override
    public List<User> getAll() {
        return List.copyOf(users.values());
    }

    @Override
    public User update(User user) {
        User existingUser = users.get(user.getId());
        if (existingUser == null) {
            throw new NotFoundException("Несуществующий id пользователя");
        }
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getLogin() != null) {
            existingUser.setLogin(user.getLogin());
        }
        if (user.getName() != null) {
            existingUser.setName(user.getName());
        }
        if (user.getBirthday() != null) {
            existingUser.setBirthday(user.getBirthday());
        }

        return existingUser;
    }

    @Override
    public User getById(Long id) {
        User user = users.get(id);
        if (user == null) {
            throw new NotFoundException("Несуществующий id пользователя: " + id);
        }
        return user;
    }

}
