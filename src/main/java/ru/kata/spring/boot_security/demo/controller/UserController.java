package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.models.User;

@Controller
public class UserController {
    private final RoleDAO roleDAO;

    public UserController(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }
    @GetMapping("/user")
    public String showUser(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleDAO.showAllRolesFromDB());
        return "user";
    }
}