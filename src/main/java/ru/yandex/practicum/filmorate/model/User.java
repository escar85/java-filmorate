package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
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

    public void addFriend(int friendId) {
        if (friends == null) friends = new HashSet<>();
        friends.add(friendId);
    }

    public void deleteFriend(int friendId) {
        friends.remove(friendId);
    }

    public Set<Integer> getFriends() {
        if (friends == null) return new HashSet<>();
        return new HashSet<>(friends);
    }
}
