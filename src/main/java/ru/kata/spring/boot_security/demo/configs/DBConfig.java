package ru.kata.spring.boot_security.demo.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;

@Component
public class DBConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DBConfig(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... strings) {
        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");

        this.roleRepository.save(adminRole);
        this.roleRepository.save(userRole);

        User admin = new User("admin", "example@gmail.com", "admin", "admin");
        admin.setRoles(new HashSet<>(List.of(adminRole, userRole)));
        admin.setPassword(passwordEncoder.encode("admin"));

        User user = new User("user", "example@gmail.com", "user", "user");
        user.setRoles(new HashSet<>(List.of(userRole)));
        user.setPassword(passwordEncoder.encode("user"));

        this.userRepository.save(admin);
        this.userRepository.save(user);
    }
}