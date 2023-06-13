package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

/**
 * Repository template for future sprints from webinar with mentor.
 */
@Component
public class UserRepository {

    private int generatorId = 10;

    public int generateId() {
        return ++generatorId;
    }

    public void save(User user) {
        user.setId(generateId());
        if (user.getName().isBlank()) {
            user.setName(user.getName());
        }
    }
}
