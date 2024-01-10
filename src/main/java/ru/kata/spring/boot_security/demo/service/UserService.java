package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    public List<User> index();
    public User show(Long id);
    public boolean save(User user);
    public void update(User updatedUser);
    public void delete(Long id);
}
