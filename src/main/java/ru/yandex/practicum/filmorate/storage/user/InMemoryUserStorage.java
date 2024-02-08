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
        addFriendHelper(user, friendId);
        addFriendHelper(users.get(friendId), userId);
        return user;
    }

    @Override
    public User deleteFriend(int userId, int friendId) {
        User user = users.get(userId);
        deleteFriendHelper(user, friendId);
        deleteFriendHelper(users.get(friendId), userId);
        return user;
    }

    @Override
    public List<User> getUserFriends(int userId) {
        Set<Integer> ids = getFriendsHelper(users.get(userId));
        List<User> friends = new ArrayList<>();
        for (int id : ids) {
            friends.add(users.get(id));
        }
        return friends;
    }

    @Override
    public List<User> getUserCommonFriends(int userId, int otherId) {
        Set<Integer> userIds = getFriendsHelper(users.get(userId));
        Set<Integer> otherUserIds = getFriendsHelper(users.get(otherId));

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

    private void addFriendHelper(User user, int friendId) {
        Set<Integer> friends = getFriendsHelper(user);
            friends.add(friendId);
            user.setFriends(friends);
    }

    private void deleteFriendHelper(User user, int friendId) {
        Set<Integer> friends = getFriendsHelper(user);
            friends.remove(friendId);
            user.setFriends(friends);
    }

    private Set<Integer> getFriendsHelper(User user) {
        Set<Integer> friends = user.getFriends();
        if (friends == null) return new HashSet<>();
        return friends;
    }

}
