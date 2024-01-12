package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> showAllUsersFromDB() {
        return userDAO.showAllUsersFromDB();
    }

    @Override
    public User show(Long id) {
        return userDAO.show(id);
    }
    @Transactional
    @Override
    public boolean save(User user) {
        User users = userDAO.findByUser(user.getEmail());
        if (users == null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userDAO.save(user);
        } else {
            throw new RuntimeException("Пользователь с таким email уже существует!");
        }
        return true;

    }
    @Transactional
    @Override
    public void update(User updatedUser) {
        if (updatedUser.getPassword().isEmpty()) {
            updatedUser.setPassword(userDAO.show(updatedUser.getId()).getPassword());
        } else {
            updatedUser.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
        }
        userDAO.update(updatedUser);
    }
    @Transactional
    @Override
    public void delete(Long id) {
        userDAO.delete(id);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDAO.findByUser(email);

        if (user == null)
            throw new UsernameNotFoundException("User not found!");

        return user;
    }


}
