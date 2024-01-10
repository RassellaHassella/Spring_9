package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDAO {
    public List<User> showAllUsersFromDB();
    public User show(Long id);
    public void save(User user);
    public void update(User updatedUser);
    public void delete(Long id);
    public User findByUser(String email);
}
