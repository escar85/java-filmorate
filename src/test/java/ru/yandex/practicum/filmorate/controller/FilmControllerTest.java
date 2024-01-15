package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {
    FilmController controller = new FilmController();

    @Test
    void check_valid_object_validation() {
        Film film = new Film(
                1,
                "film",
                "description",
                LocalDate.of(1980, 2, 2),
                100
        );
        boolean isValid = controller.isFilmValid(film);
        assertTrue(isValid, "Валидный объект не прошел валидацию");
    }

    @Test
    void check_empty_name_validation() {
        Film film = new Film(
                1,
                "",
                "description",
                LocalDate.of(1980, 2, 2),
                100
        );
        boolean isValid = controller.isFilmValid(film);
        assertFalse(isValid, "Неверный результат валидации");
    }

    @Test
    void check_zero_and_negative_duration_validation() {
        Film film = new Film(
                1,
                "film",
                "description",
                LocalDate.of(1980, 2, 2),
                0
        );
        Film film1 = new Film(
                1,
                "film",
                "description",
                LocalDate.of(1980, 2, 2),
                0
        );
        boolean isValid = controller.isFilmValid(film);
        boolean isValid1 = controller.isFilmValid(film1);
        assertFalse(isValid, "Неверный результат валидации");
        assertFalse(isValid1, "Неверный результат валидации");
    }

    @Test
    void check_release_date_validation() {
        Film validFilm = new Film(
                1,
                "film",
                "description",
                LocalDate.of(1895, 12, 28),
                100
        );
        Film invalidFilm = new Film(
                1, "film",
                "description",
                LocalDate.of(1895, 12, 27),
                100
        );
        boolean validResult = controller.isFilmValid(validFilm);
        boolean invalidResult = controller.isFilmValid(invalidFilm);

        assertTrue(validResult, "Неверный результат валидации");
        assertFalse(invalidResult, "Неверный результат валидации");
    }

    @Test
    void check_description_validation() {
        Film validFilm = new Film(
                1,
                "film",
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod " +
                        "tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, " +
                        "quis nostrud exerci tatio",
                LocalDate.of(1980, 2, 2),
                100
        );
        Film invalidFilm = new Film(
                1,
                "film",
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod " +
                        "tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, " +
                        "quis nostrud exerci tatio1",
                LocalDate.of(1980, 2, 2),
                100
        );
        boolean validResult = controller.isFilmValid(validFilm);
        boolean invalidResult = controller.isFilmValid(invalidFilm);

        assertTrue(validResult, "Неверный результат валидации");
        assertFalse(invalidResult, "Неверный результат валидации");
    }
}
