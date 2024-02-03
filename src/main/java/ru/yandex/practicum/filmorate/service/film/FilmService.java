package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film createFilm(Film film) {
        if (!isFilmValid(film)) {
            log.warn("Ошибка валидации фильма {}", film);
            throw new ValidationException("Ошибка валидации фильма.");
        }

        Film newFilm = filmStorage.createFilm(film);
        log.info("Создан новый фильм {}", film);
        return newFilm;
    }

    public Film updateFilm(Film film) {
        if (!isFilmValid(film)) {
            log.warn("Ошибка валидации фильма {}", film);
            throw new ValidationException("Ошибка валидации фильма.");
        }

        Film updatedFilm = filmStorage.updateFilm(film);
        log.info("Обновлен фильм {}", film);
        return updatedFilm;
    }

    public Film addLike(int filmId, int userId) {
        return filmStorage.addLike(filmId, userId);
    }

    public Film deleteLike(int filmId, int userId) {
       return filmStorage.deleteLike(filmId, userId);
    }

    public List<Film> getMostPopular(int count) {
        return filmStorage.getMostPopular(count);
    }

    public Film getById(int filmId) {
        return filmStorage.getById(filmId);
    }

    public boolean isFilmValid(Film film) {
        if (StringUtils.isBlank(film.getName())) return false;
        if (film.getDuration() <= 0) return false;
        if (film.getDescription().length() > 200) return false;
        return !film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28));
    }
}
