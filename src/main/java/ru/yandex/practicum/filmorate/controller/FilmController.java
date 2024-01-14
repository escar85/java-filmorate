package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.exceptions.ValidationException;

import javax.validation.Valid;
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
    public ResponseEntity<List<Film>> getAllFilms() {
        return ResponseEntity.ok(new ArrayList<>(films.values()));
    }

    @PostMapping
    public ResponseEntity<Film> createFilm(@Valid @RequestBody Film film) {
        if (!isFilmValid(film)) {
            log.warn("Ошибка валидации фильма {}", film);
            throw new ValidationException("Ошибка валидации фильма.");
        }

        idCount++;
        film.setId(idCount);
        films.put(film.getId(), film);
        log.info("Создан новый фильм {}", film);
        return ResponseEntity.ok(film);
    }

    @PutMapping
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
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
        return ResponseEntity.ok(film);
    }

    protected boolean isFilmValid(Film film) {
        if (StringUtils.isBlank(film.getName())) return false;
        if (film.getDuration() <= 0) return false;
        if (film.getDescription().length() > 200) return false;
        return !film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28));
    }
}
