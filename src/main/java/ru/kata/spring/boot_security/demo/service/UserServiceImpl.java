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
        List<User> users = userDAO.showAllUsersFromDB();
        return users;
    }

    @Override
    public User show(Long id) {
        User user = userDAO.show(id);
        return user;
    }

    @Override
    public boolean save(User user) {
        User users = userDAO.findByUser(user.getEmail());
        if (users == null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userDAO.save(user);
        } else  {
            throw new RuntimeException("Пользователь с таким email уже существует!");
        }
        return true;

    }

    //    @Override
//    public void checkSetRoles(User user, List<Role> roles) {
//        if (user.getRoles() == null) {
//            user.setRoles(new HashSet<>());
//        } else {
//            user.getRoles().clear();
//        }
//
//        if (user.getAdmin()){
//            user.getRoles().add(roles.get(0));
//        }
//        if (user.getUser()) {
//            user.getRoles().add(roles.get(1));
//        }
//    }
    @Override
    public void update(User updatedUser) {
        checkSetRoles(updatedUser, roleService.findAll());
        if (updatedUser.getPassword().isEmpty()) {
            updatedUser.setPassword(userDAO.show(updatedUser.getId()).getPassword());
        } else {
            updatedUser.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
        }

        userDAO.update(updatedUser);
    }

    @Override
    public void delete(Long id) {
        userDAO.delete(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDAO.findByUser(email);

        if (user == null)
            throw new UsernameNotFoundException("User not found!");

        return user;
    }

    @Override
    public BCryptPasswordEncoder getbCryptPasswordEncoder() {
        return bCryptPasswordEncoder;
    }

    @Override
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
}
