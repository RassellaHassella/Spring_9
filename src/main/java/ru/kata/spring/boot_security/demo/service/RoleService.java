package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.Set;

public interface RoleService {
    public Set<Role> showAllRolesFromDB();
//    public void save(Role role);
    public Set<Role> findByRole(String value);
}
