package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {
    UserController controller = new UserController();

    @Test
    void check_valid_object_validation() {
        User user = new User(
                1,
                "user",
                "qwerty@ya.ru",
                "login",
                LocalDate.of(1980, 2, 2)
        );
        boolean isValid = controller.isUserValid(user);
        assertTrue(isValid, "Валидный объект не прошел валидацию");
    }

    @Test
    void check_login_validation() {
        User userEmpty = new User(
                1,
                "user",
                "qwerty@ya.ru",
                "",
                LocalDate.of(1980, 2, 2)
        );
        User userWhitespace = new User(
                1,
                "user",
                "qwerty@ya.ru",
                "asd asd",
                LocalDate.of(1980, 2, 2)
        );

        boolean isValidEmpty = controller.isUserValid(userEmpty);
        boolean isValidWhitespace = controller.isUserValid(userWhitespace);

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
                LocalDate.of(1980, 2, 2)
        );
        User userWrong = new User(
                1,
                "user",
                "qwertyya.ru",
                "asdasd",
                LocalDate.of(1980, 2, 2)
        );

        boolean isValidEmpty = controller.isUserValid(userEmpty);
        boolean isValidWrong = controller.isUserValid(userWrong);

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
                date.plusDays(1)
        );

        boolean isValid = controller.isUserValid(user);

        assertFalse(isValid, "Неверный результат валидации");
    }
}
