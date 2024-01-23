package ru.kata.spring.boot_security.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

@Component
public class InitDB {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public InitDB(UserService userService, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @PostConstruct
    private void PostDb() {
        userService.createRolesIfNotExist();
        User user = new User("Ruslan", "Kutepov", 29, "rkr.ru@mail.ru", "1234",
                List.of(roleRepository.getById(1L), roleRepository.getById(2L)));
        userService.add(user);
        User user1 = new User("Ivan", "Ivanov", 22, "mail@mail.ru", "1234",
                List.of(roleRepository.getById(1L)));
        userService.add(user1);
    }
}