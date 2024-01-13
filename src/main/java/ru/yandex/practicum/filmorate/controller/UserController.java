package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.exceptions.ValidationException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    private int idCount = 0;

    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        if (!isUserValid(user)) {
            log.warn("Ошибка валидации пользователя {}", user);
            throw new ValidationException("Ошибка валидации пользователя.");
        }

        idCount++;
        user.setId(idCount);
        users.put(user.getId(), user);
        log.info("Создан новый пользователь {}", user);
    }

    @PutMapping
    public void updateUser(@RequestBody User user) {
        if (!isUserValid(user)) {
            log.warn("Ошибка валидации пользователя {}", user);
            throw new ValidationException("Ошибка валидации пользователя.");
        }
        if (!users.containsKey(user.getId())) {
            log.warn("Отсутствует запись о пользователе {}", user);
            throw new ValidationException("Пользователь с айди " + user.getId() + " отсутствует");
        }

        users.put(user.getId(), user);
        log.info("Обновлен пользователь {}", user);
    }

    protected boolean isUserValid(User user) {
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) return false;
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) return false;
        return !user.getBirthday().isAfter(LocalDate.now());
    }
}
