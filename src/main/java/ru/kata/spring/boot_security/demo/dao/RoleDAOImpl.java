package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Role> showAllRolesFromDB() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    @Override
    public void save(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Role findByRole(String value) {
        return entityManager.createQuery("SELECT r FROM Role r where r=:value", Role.class)
                .getSingleResult();
    }

}
