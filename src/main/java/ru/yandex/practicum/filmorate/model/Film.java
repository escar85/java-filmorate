package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * Film
 */
@Data
@AllArgsConstructor
public class Film {
    private int id;
    @NotBlank
    private String name;
    @Max(200)
    private String description;
    private LocalDate releaseDate;
    @Min(1)
    private int duration;
}
