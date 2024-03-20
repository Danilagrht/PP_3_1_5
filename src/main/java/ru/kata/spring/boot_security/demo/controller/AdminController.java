package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class AdminController {
    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "admin";
    }

    @PostMapping(value = "/admin/addUser")
    public String addNewUser(@RequestParam String name, @RequestParam String email, @RequestParam String username, @RequestParam String password) {
        User user = new User(name, email, username, password);
        userService.saveUser(user);
        userService.addRoleToUser("ROLE_USER", user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/updateUser")
    public String updateUser(@RequestParam Long id, @RequestParam String name, @RequestParam String email, @RequestParam String username, @RequestParam String password) {
        User user = userService.findById(id);
        user.setName(name);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/deleteUser")
    public String deleteUser(@RequestParam long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
