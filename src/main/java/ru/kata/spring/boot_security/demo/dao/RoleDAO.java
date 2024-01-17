package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Set;

public interface RoleDAO {
    public List<Role> showAllRolesFromDB();
    public void save(Role role);
    public List<Role> findByRole(List<Role> roles);
}
