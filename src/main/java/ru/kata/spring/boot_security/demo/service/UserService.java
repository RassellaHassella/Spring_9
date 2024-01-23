package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    public void add(User user);

    public void createRolesIfNotExist();

    public List<User> usersList();

    public void update(User user, List<Role> roles);

    public void delete(Long id);

    public User getUser(Long id);

    public User findByEmail(String email);


}
