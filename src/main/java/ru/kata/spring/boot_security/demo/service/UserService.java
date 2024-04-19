package ru.kata.spring.boot_security.demo.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;

import java.security.Principal;
import java.util.List;

public interface UserService {
    List<User> getUsers();

    List<User> getCurrentUser(Principal principal);

    void saveUser(User user, String role);

    void updateUser(User user, long id, String role);

    void deleteUser(long id);

    void addRoleToUser(String roleName, User user);

    User findByUsername(String username);

    User findById(Long id);
}