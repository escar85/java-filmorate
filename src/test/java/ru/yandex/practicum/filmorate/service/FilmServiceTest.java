package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilmServiceTest {

    private final FilmService service = new FilmService(new InMemoryFilmStorage());

    @Test
    void check_valid_object_validation() {
        Film film = new Film(
                1,
                "film",
                "description",
                LocalDate.of(1980, 2, 2),
                100,
                new HashSet<>()
        );
        boolean isValid = service.isFilmValid(film);
        assertTrue(isValid, "Валидный объект не прошел валидацию");
    }

    @Test
    void check_empty_name_validation() {
        Film film = new Film(
                1,
                "",
                "description",
                LocalDate.of(1980, 2, 2),
                100,
                new HashSet<>()
        );
        boolean isValid = service.isFilmValid(film);
        assertFalse(isValid, "Неверный результат валидации");
    }

    @Test
    void check_zero_and_negative_duration_validation() {
        Film film = new Film(
                1,
                "film",
                "description",
                LocalDate.of(1980, 2, 2),
                0,
                new HashSet<>()
        );
        Film film1 = new Film(
                1,
                "film",
                "description",
                LocalDate.of(1980, 2, 2),
                0,
                new HashSet<>()
        );
        boolean isValid = service.isFilmValid(film);
        boolean isValid1 = service.isFilmValid(film1);
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
                100,
                new HashSet<>()
        );
        Film invalidFilm = new Film(
                1, "film",
                "description",
                LocalDate.of(1895, 12, 27),
                100,
                new HashSet<>()
        );
        boolean validResult = service.isFilmValid(validFilm);
        boolean invalidResult = service.isFilmValid(invalidFilm);

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
                100,
                new HashSet<>()
        );
        Film invalidFilm = new Film(
                1,
                "film",
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod " +
                        "tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, " +
                        "quis nostrud exerci tatio1",
                LocalDate.of(1980, 2, 2),
                100,
                new HashSet<>()
        );
        boolean validResult = service.isFilmValid(validFilm);
        boolean invalidResult = service.isFilmValid(invalidFilm);

        assertTrue(validResult, "Неверный результат валидации");
        assertFalse(invalidResult, "Неверный результат валидации");
    }
}
