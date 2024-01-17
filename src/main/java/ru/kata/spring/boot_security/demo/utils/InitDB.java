package ru.kata.spring.boot_security.demo.utils;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;


import javax.annotation.PostConstruct;

@Component
public class InitDB {

    private final RoleDAO roleDAO;
    private final UserService userService;


    public InitDB(RoleDAO roleDAO, UserService userService) {
        this.roleDAO = roleDAO;
        this.userService = userService;
    }
    @PostConstruct
    private void PostDb(){
        Role roleAdmin = new Role( "ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        roleDAO.save(roleAdmin);
        roleDAO.save(roleUser);

        User user = new User("rkr.ru@mail.ru", "Ruslan", "Kutepov", 29, "1234");
        user.addRoles(roleAdmin);
        user.addRoles(roleUser);
        userService.save(user);
        User user1 = new User("mail@mail.ru", "Ivan", "Ivanov", 22, "1234");
        user1.addRoles(roleUser);
        userService.save(user1);

    }
}