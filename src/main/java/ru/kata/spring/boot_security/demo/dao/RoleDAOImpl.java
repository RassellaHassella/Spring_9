package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RoleDAOImpl implements RoleDAO {
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
    public List<Role> findByRole(String value) {
        TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r where r=:value1", Role.class);
        query.setParameter("value1", value);
        return query.getResultStream().collect(Collectors.toList());

    }

}
