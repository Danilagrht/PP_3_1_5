package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class AdminController {
    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin")
    public String getUsers(Model model, Principal principal) {
        model.addAttribute("currentUser", userService.findByUsername(principal.getName()));
        model.addAttribute("users", userService.getUsers());
        return "admin";
    }

    /*@GetMapping(value = "/user")
    public String getCurrentUser(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("currentUser", user);
        return "user";
    }*/

    @PostMapping(value = "/admin/addUser")
    public String addNewUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam int age, @RequestParam String email, @RequestParam String username, @RequestParam String password, @RequestParam String role) {
        User user = new User(firstName, lastName, age, email, username, password);
        userService.saveUser(user, role);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/updateUser")
    public String updateUser(@RequestParam Long id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam int age, @RequestParam String email, @RequestParam String username, @RequestParam String password, @RequestParam String role) {
        User user = new User(firstName, lastName, age, email, username, password);
        userService.updateUser(user, id, role);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/deleteUser")
    public String deleteUser(@RequestParam long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
