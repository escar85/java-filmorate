package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

/**
 * User
 */
@Data
@AllArgsConstructor
public class User {
    private int id;
    private String name;
    @Email
    private String email;
    @NotNull
    @NotBlank
    private String login;
    private LocalDate birthday;
    private Set<Integer> friends;

}
