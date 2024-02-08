package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        return ResponseEntity.ok(filmService.getAllFilms());
    }

    @PostMapping
    public ResponseEntity<Film> createFilm(@Valid @RequestBody Film film) {
        return ResponseEntity.ok(filmService.createFilm(film));
    }

    @PutMapping
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        return ResponseEntity.ok(filmService.updateFilm(film));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable int id) {
        return ResponseEntity.ok(filmService.getById(id));
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Film> addLike(@PathVariable int id, @PathVariable int userId) {
        return ResponseEntity.ok(filmService.addLike(id, userId));
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Film> deleteLike(@PathVariable int id, @PathVariable int userId) {
        return ResponseEntity.ok(filmService.deleteLike(id, userId));
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Film>> getMostPopular(@RequestParam(defaultValue = "10") String count) {
        return ResponseEntity.ok(filmService.getMostPopular(Integer.parseInt(count)));
    }
}
