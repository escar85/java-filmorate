package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * User
 */
@Data
@AllArgsConstructor
public class User {
    private int id;
    private String name;
    private String email;
    private String login;
    private LocalDate birthday;
}
