package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    public Role save(Role role);

    public List<Role> findAll();

    public Optional<Role> findById(Long id);

    public void delete(Long id);

    public Optional<Role> findByRole(String role);

}
