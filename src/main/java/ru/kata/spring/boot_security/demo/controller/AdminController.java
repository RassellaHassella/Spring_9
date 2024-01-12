package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping({"/", "/index"})
    public String showFirstPage(@AuthenticationPrincipal UserDetails userDetails, Model model, Principal principal) {
        String email = userDetails.getUsername();
        model.addAttribute("users", userService.showAllUsersFromDB());
        model.addAttribute("user", userService.loadUserByUsername(email));
        model.addAttribute("roles", roleService.showAllRolesFromDB());
        return "/index";
    }


    @PostMapping({"/new"})
    public String createPerson(@ModelAttribute("user") User user,
                               @RequestParam(value = "nameRoles", required = false) String roles) {
        user.setRoles(roleService.findByRole(roles));
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/update/{id}")
    public String updatePerson(@ModelAttribute("users") User user,
            @RequestParam(value = "roleName", required = false) String roles){
        user.setRoles(roleService.findByRole(roles));
        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String delete(@ModelAttribute("users") User user) {
        userService.delete(user.getId());
        return "redirect:/admin";
    }
}
