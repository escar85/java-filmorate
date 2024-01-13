package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.exceptions.ValidationException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();
    private int idCount = 0;

    @GetMapping
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        if (!isFilmValid(film)) {
            log.warn("Ошибка валидации фильма {}", film);
            throw new ValidationException("Ошибка валидации фильма.");
        }

        idCount++;
        film.setId(idCount);
        films.put(film.getId(), film);
        log.info("Создан новый фильм {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        if (!isFilmValid(film)) {
            log.warn("Ошибка валидации фильма {}", film);
            throw new ValidationException("Ошибка валидации фильма.");
        }
        if (!films.containsKey(film.getId())) {
            log.warn("Отсутствует запись о фильме {}", film);
            throw new ValidationException("Фильм с айди " + film.getId() + " отсутствует");
        }

        films.put(film.getId(), film);
        log.info("Обновлен фильм {}", film);
        return film;
    }

    protected boolean isFilmValid(Film film) {
        if (film.getName().isBlank()) return false;
        if (film.getDuration() <= 0) return false;
        if (film.getDescription().length() > 200) return false;
        return !film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28));
    }
}
