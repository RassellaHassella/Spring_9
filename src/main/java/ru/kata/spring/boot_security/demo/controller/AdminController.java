package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService carService) {
        this.userService = carService;
    }

    @GetMapping({"/", "/index"})
    public String showFirstPage(Principal principal, ModelMap model) {
//        userService.save(new User("admin", 26, "admin"));
        if (principal != null) {
            Long id = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            model.addAttribute("user", id);
        }
        return "/index";
    }
    @GetMapping("/user")
    public String showUser(@RequestParam(value = "id", required = false) Long id, ModelMap model) {
        model.addAttribute("users", userService.show(id));
        return "id";
    }

    @GetMapping("/admin")
    public String showAdmin(@RequestParam(value = "id", required = false) Long id, ModelMap model) {
        if (id != null) {
            model.addAttribute("users", userService.show(id));
            return "id";
        }
        model.addAttribute("users", userService.index());
        return "/user";
    }

    @GetMapping({"/admin/new", "/admin/edit"})
    public String newPersonByAdmin(@ModelAttribute("users") User user) {
        return "/new";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("users") User user) {
        return "/new";
    }

    @PostMapping()
    public String createUpdate(@RequestParam(value = "id", required = false) Integer id,
                               @ModelAttribute("users") User user) {
        if (id == null) {
            userService.save(user);
        } else {
            userService.update(user);
        }
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("users") User user) {
        userService.delete(user.getId());
        return "redirect:/admin";
    }
}