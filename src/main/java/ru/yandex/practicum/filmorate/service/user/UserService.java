package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User createUser(User user) {
        if (!isUserValid(user)) {
            log.warn("Ошибка валидации пользователя {}", user);
            throw new ValidationException("Ошибка валидации пользователя.");
        }

        if (StringUtils.isBlank(user.getName())) {
            user.setName(user.getLogin());
        }
        User newUser = userStorage.createUser(user);
        log.info("Создан новый пользователь {}", user);
        return newUser;
    }

    public User updateUser(User user) {
        if (!isUserValid(user)) {
            log.warn("Ошибка валидации пользователя {}", user);
            throw new ValidationException("Ошибка валидации пользователя.");
        }

        User updatedUser = userStorage.updateUser(user);
        log.info("Обновлен пользователь {}", user);
        return updatedUser;
    }

    public User addFriend(int userId, int friendId) {
       return userStorage.addFriend(userId, friendId);
    }

    public User deleteFriend(int userId, int friendId) {
        return userStorage.deleteFriend(userId, friendId);
    }

    public List<User> getUserFriends(int userId) {
        return userStorage.getUserFriends(userId);
    }

    public List<User> getUserCommonFriends(int userId, int otherId) {
        return userStorage.getUserCommonFriends(userId, otherId);
    }

    public User getById(int userId) {
        return userStorage.getById(userId);
    }

    public boolean isUserValid(User user) {
        if (StringUtils.isBlank(user.getLogin()) || user.getLogin().contains(" ")) return false;
        if (StringUtils.isBlank(user.getEmail()) || !user.getEmail().contains("@")) return false;
        return !user.getBirthday().isAfter(LocalDate.now());
    }
}
