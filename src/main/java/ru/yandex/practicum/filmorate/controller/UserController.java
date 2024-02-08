package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<User> addFriend(@PathVariable int userId, @PathVariable int friendId) {
        return ResponseEntity.ok(userService.addFriend(userId, friendId));
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<User> deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        return ResponseEntity.ok(userService.deleteFriend(id, friendId));
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<List<User>> getUserFriends(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUserFriends(id));
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<List<User>> getUserCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        return ResponseEntity.ok(userService.getUserCommonFriends(id, otherId));
    }
}
