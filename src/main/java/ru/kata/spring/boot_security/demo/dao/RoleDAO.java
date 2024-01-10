package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface RoleDAO {
    public List<Role> showAllRolesFromDB();
    public void save(Role role);
    public Role findByRole(String value);
}
