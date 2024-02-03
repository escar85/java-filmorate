package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Film
 */
@Data
@AllArgsConstructor
public class Film {
    private int id;
    @NotNull
    private String name;
    private String description;
    private LocalDate releaseDate;
    @Min(1)
    private int duration;
    private Set<Integer> likes;

    public void addLike(int userId) {
        if (likes == null) likes = new HashSet<>();
        likes.add(userId);
    }

    public void deleteLike(int userId) {
        likes.remove(userId);
    }

    public Set<Integer> getLikes() {
        if (likes == null) return new HashSet<>();
        return new HashSet<>(likes);
    }
}
