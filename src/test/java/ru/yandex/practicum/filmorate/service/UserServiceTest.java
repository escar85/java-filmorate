package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserServiceTest {

    private final UserService service = new UserService(new InMemoryUserStorage());

    @Test
    void check_valid_object_validation() {
        User user = new User(
                1,
                "user",
                "qwerty@ya.ru",
                "login",
                LocalDate.of(1980, 2, 2),
                new HashSet<>()
        );
        boolean isValid = service.isUserValid(user);
        assertTrue(isValid, "Валидный объект не прошел валидацию");
    }

    @Test
    void check_login_validation() {
        User userEmpty = new User(
                1,
                "user",
                "qwerty@ya.ru",
                "",
                LocalDate.of(1980, 2, 2),
                new HashSet<>()
        );
        User userWhitespace = new User(
                1,
                "user",
                "qwerty@ya.ru",
                "asd asd",
                LocalDate.of(1980, 2, 2),
                new HashSet<>()
        );

        boolean isValidEmpty = service.isUserValid(userEmpty);
        boolean isValidWhitespace = service.isUserValid(userWhitespace);

        assertFalse(isValidEmpty, "Неверный результат валидации");
        assertFalse(isValidWhitespace, "Неверный результат валидации");
    }

    @Test
    void check_email_validation() {
        User userEmpty = new User(
                1,
                "user",
                "",
                "login",
                LocalDate.of(1980, 2, 2),
                new HashSet<>()
        );
        User userWrong = new User(
                1,
                "user",
                "qwertyya.ru",
                "asdasd",
                LocalDate.of(1980, 2, 2),
                new HashSet<>()
        );

        boolean isValidEmpty = service.isUserValid(userEmpty);
        boolean isValidWrong = service.isUserValid(userWrong);

        assertFalse(isValidEmpty, "Неверный результат валидации");
        assertFalse(isValidWrong, "Неверный результат валидации");
    }

    @Test
    void check_birth_date_validation() {
        LocalDate date = LocalDate.now();
        User user = new User(
                1,
                "user",
                "qwerty@ya.ru",
                "login",
                date.plusDays(1),
                new HashSet<>()
        );

        boolean isValid = service.isUserValid(user);

        assertFalse(isValid, "Неверный результат валидации");
    }
}
