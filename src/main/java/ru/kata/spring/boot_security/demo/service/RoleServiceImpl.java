package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService{
    private final RoleDAO roleDAO;
    private final UserDAO userDAO;

    public RoleServiceImpl(RoleDAO roleDAO, UserDAO userDAO) {
        this.roleDAO = roleDAO;
        this.userDAO = userDAO;
    }

    @Override
    public List<Role> showAllRolesFromDB() {
        return roleDAO.showAllRolesFromDB();
    }

//    @Override
//    public void save(Role role) {
//        Role roles = roleDAO.findByRole(role.getValue());
//        if (roles == null){
//            roleDAO.save(role);
//        }
//    }

    @Override
    public List<Role> findByRole(String value) {
        return roleDAO.findByRole(value);
    }
}
