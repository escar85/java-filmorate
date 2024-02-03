package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int idCount = 0;

    @Override
    public List<User> getAllUsers() {
       return new ArrayList<>(users.values());
    }

    @Override
    public User createUser(User user) {
        idCount++;
        user.setId(idCount);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException(String.format("Пользователь с id %s не найден", user.getId()));
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User addFriend(int userId, int friendId) {
        if (!users.containsKey(friendId)) {
            throw new NotFoundException(String.format("Пользователь с id %s не найден", userId));
        }
        User user = users.get(userId);
        user.addFriend(friendId);
        users.get(friendId).addFriend(userId);
        return user;
    }

    @Override
    public User deleteFriend(int userId, int friendId) {
        User user = users.get(userId);
        user.deleteFriend(friendId);
        users.get(friendId).deleteFriend(userId);
        return user;
    }

    @Override
    public List<User> getUserFriends(int userId) {
        Set<Integer> ids = users.get(userId).getFriends();
        List<User> friends = new ArrayList<>();
        for (int id : ids) {
            friends.add(users.get(id));
        }
        return friends;
    }

    @Override
    public List<User> getUserCommonFriends(int userId, int otherId) {
        Set<Integer> userIds = users.get(userId).getFriends();
        Set<Integer> otherUserIds = users.get(otherId).getFriends();

        List<User> commonFriends = new ArrayList<>();
        for (int id : userIds) {
            if (otherUserIds.contains(id)) {
                commonFriends.add(users.get(id));
            }
        }

        return commonFriends;
    }

    @Override
    public User getById(int userId) {
        if (!users.containsKey(userId)) {
            throw new NotFoundException(String.format("Пользователь с id %s не найден", userId));
        }
        return users.get(userId);
    }
}
