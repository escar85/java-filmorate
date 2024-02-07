package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserInterface {
    List<User> getAllUsers();

    User createUser(User user);

    User updateUser(User user);

    User addFriend(int userId, int friendId);

    User deleteFriend(int userId, int friendId);

    List<User> getUserFriends(int userId);

    List<User> getUserCommonFriends(int userId, int otherId);

    User getById(int userId);
}
