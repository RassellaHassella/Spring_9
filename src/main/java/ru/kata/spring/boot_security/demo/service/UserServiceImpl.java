package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleService;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService{
    private UserDAO userDAO;
    private RoleService roleService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDAO = userDAO;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Transactional(readOnly = true)
    @Override
    public List<User> index() {
        List<User> users = userDAO.index();
        for (User user : users) {
            user.checkRole();
        }
        return users;
    }
    @Transactional(readOnly = true)
    @Override
    public User show(int id) {
        User user = userDAO.show(id);
        user.checkRole();
        return user;
    }
    @Transactional
    @Override
    public boolean save(User user) {
        User users = userDAO.findByUsername(user.getUsername());
        List<Role> roles= roleService.findAll();

        if (users != null) {
            return false;
        }

        if (roles.isEmpty()) {
            Role roles1 = new Role(1L, "ROLE_ADMIN");
            Role roles2 = new Role(2L, "ROLE_USER");
            user.setRoles(new HashSet<>(Set.of(roles1, roles2)));
            roleService.save(roles1);
            roleService.save(roles2);
        } else if (!(user.getUser() && user.getAdmin())) {
            user.setRoles(new HashSet<>(Set.of(roles.get(1))));
        } else {
            checkSetRoles(user, roles);
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDAO.save(user);
        return true;
    }

    public void checkSetRoles(User user, List<Role> roles) {
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        } else {
            user.getRoles().clear();
        }

        if (user.getAdmin()){
            user.getRoles().add(roles.get(0));
        }
        if (user.getUser()) {
            user.getRoles().add(roles.get(1));
        }
    }
    @Transactional
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
    @Transactional
    @Override
    public void delete(int id) {
        userDAO.delete(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException("User not found!");

        return user;
    }

    public BCryptPasswordEncoder getbCryptPasswordEncoder() {
        return bCryptPasswordEncoder;
    }

    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
}
