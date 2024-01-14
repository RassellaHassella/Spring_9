package ru.kata.spring.boot_security.demo.dao;

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
    public Set<Role> showAllRolesFromDB() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class)
                .getResultStream().collect(Collectors.toSet());
    }

    @Transactional
    @Override
    public void save(Role role) {
        entityManager.persist(role);
    }

    @Transactional
    @Override
    public Set<Role> findByRole(String value) {
        TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r where r=:value1", Role.class);
        query.setParameter("value1", value);
        return query.getResultStream().collect(Collectors.toSet());

    }

}
