package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Component
@Slf4j
public class ValidateService {
    public void validateUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            log.warn("user name is blank: {}", user);
            throw new RuntimeException("Ошибка в имени:....");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new RuntimeException("invalid birthday");
        }

    }
}