package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoleDAOImpl implements RoleDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public List<Role> showAllRolesFromDB() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class)
                .getResultStream().collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void save(Role role) {

        entityManager.persist(role);
    }

    @Transactional
    @Override
    public List<Role> findByRole(List<Role> roles) {
        return entityManager.createQuery("SELECT r FROM Role r where r IN :roles", Role.class)
        .setParameter("roles", roles)
        .getResultList();
    }

}
