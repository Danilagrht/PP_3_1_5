package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminController {
    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/admin")
    public ModelAndView welcome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @GetMapping(value = "/admin/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(value = "/admin/currentUser")
    public List<User> getCurrentUser(Principal principal) {
        return userService.getCurrentUser(principal);
    }

    /*@GetMapping(value = "/admin")
    public String getUsers(Model model, Principal principal) {
        model.addAttribute("currentUser", userService.findByUsername(principal.getName()));
        model.addAttribute("users", userService.getUsers());
        return "admin";
    }*/

    /*@GetMapping(value = "/user")
    public String getCurrentUser(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("currentUser", user);
        return "user";
    }*/

    @PostMapping(value = "/admin/addUser")
    public ResponseEntity<String> addNewUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam int age, @RequestParam String email, @RequestParam String username, @RequestParam String password, @RequestParam String role) {
        User user = new User(firstName, lastName, age, email, username, password);
        userService.saveUser(user, role);
        return ResponseEntity.ok("Everything is fine");
    }

    @PostMapping(value = "/admin/updateUser")
    public ResponseEntity<String> updateUser(@RequestParam Long id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam int age, @RequestParam String email, @RequestParam String username, @RequestParam String password, @RequestParam String role) {
        User user = new User(firstName, lastName, age, email, username, password);
        userService.updateUser(user, id, role);
        return ResponseEntity.ok("Everything is fine");
    }

    @PostMapping(value = "/admin/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Everything is fine");
    }
}
