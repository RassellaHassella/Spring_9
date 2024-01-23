package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @GetMapping()
    public String showFirstPage(Model model) {
        List<User> users = userService.usersList();
        model.addAttribute("usersList", users);
        return "admin";
    }


    @PostMapping({"/addUser"})
    public String createPerson(@ModelAttribute("user") @Valid User user){
        userService.add(user);
        return "redirect:/admin";
    }
    @GetMapping("/addUser")
    public String newUserForm(Model model) {
        List<Role> roles = (List<Role>) roleService.findAll();
        model.addAttribute("allRoles", roles);
        model.addAttribute("user", new User());
        return "addUser";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model) {
        User user = userService.getUser(id);
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("allRoles", roleService.findAll());
            return "adminEditUser";
        } else {
            return "redirect:/admin";
        }

    }
    @PostMapping("/edit")
    public String edit(@ModelAttribute("user") @Valid User user,
                       BindingResult bindingResult, Model model) {
        userService.update(user, user.getRoles());
        return "redirect:/admin";
    }
    @GetMapping("/delete/{id}")
    public String deletUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}

