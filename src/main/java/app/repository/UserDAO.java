package app.repository;

import app.model.User;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    User getUser(int id);
    void saveUser(User user);
    void updateUser(int id, User user);
    void removeUser(int id);
}
