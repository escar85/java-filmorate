package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int idCount = 0;

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film createFilm(Film film) {
        idCount++;
        film.setId(idCount);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException(String.format("Фильм с id %s не найден", film.getId()));
        }
        films.put(film.getId(), film);
        log.info("Обновлен фильм {}", film);
        return film;
    }

    @Override
    public Film addLike(int filmId, int userId) {
        Film film = films.get(filmId);
        Set<Integer> likes = getLikesHelper(film);
        likes.add(userId);
        film.setLikes(likes);
        return film;
    }

    @Override
    public Film deleteLike(int filmId, int userId) {
        Film film = films.get(filmId);
        Set<Integer> likes = getLikesHelper(film);
        if (!likes.contains(userId)) {
            throw new NotFoundException(String.format("Лайк пользователя с id %s не найден", userId));
        } else {
            likes.remove(userId);
            film.setLikes(likes);
        }
        return film;
    }

    @Override
    public List<Film> getMostPopular(int count) {
        List<Film> filmsList = new ArrayList<>(films.values());
        filmsList.sort(Comparator.comparingInt(film -> getLikesHelper((Film) film).size()).reversed());
        if (filmsList.size() <= count) return filmsList;
        return filmsList.subList(0, count);
    }

    @Override
    public Film getById(int filmId) {
        if (!films.containsKey(filmId)) {
            throw new NotFoundException(String.format("Фильм с id %s не найден", filmId));
        }
        return films.get(filmId);
    }

    private Set<Integer> getLikesHelper(Film film) {
        Set<Integer> likes = film.getLikes();
        if (likes == null) return new HashSet<>();
        return likes;
    }
}
