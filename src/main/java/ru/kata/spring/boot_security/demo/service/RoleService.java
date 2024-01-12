package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface RoleService {
    public List<Role> showAllRolesFromDB();
//    public void save(Role role);
    public List<Role> findByRole(String value);
}
