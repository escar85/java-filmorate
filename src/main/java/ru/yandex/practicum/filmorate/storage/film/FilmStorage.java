package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

     List<Film> getAllFilms();
     Film createFilm(Film film);

     Film updateFilm(Film film);

     Film addLike(int filmId, int userId);

     Film deleteLike(int filmId, int userId);

     List<Film> getMostPopular(int count);

     Film getById(int filmId);
}
