package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.*;
import java.util.List;


@Repository
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> showAllUsersFromDB() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User findByUser(String email) {
        return entityManager.createQuery("SELECT u FROM User u where u=:email", User.class)
               .getSingleResult();
    }

    @Override
    public User show(Long id) {
        return entityManager.find(User.class, id);
    }
    @Transactional
    @Override
    public void save(User user) {
        entityManager.persist(user);
    }
    @Transactional
    @Override
    public void update(User updatedUser) {
        entityManager.merge(updatedUser);
    }
    @Transactional
    @Override
    public void delete(Long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

}

