package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService/*, UserDetailsService*/ {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userRepository.findAll(Sort.by("id"));
    }

    @Override
    @Transactional
    public void saveUser(User user, String inputRoles) {
        String[] roles = inputRoles.split(",\\s*");
        for (String role : roles) {
            addRoleToUser(role, user);
        }
        /*addRoleToUser("ROLE_USER", user);
        if ("admin".equals(role)) {
            addRoleToUser("ROLE_ADMIN", user);
        }*/
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user, long id, String inputRoles) {
        User userForUpdate = findById(id);
        userForUpdate.setFirstName(user.getFirstName());
        userForUpdate.setLastName(user.getLastName());
        userForUpdate.setAge(user.getAge());
        userForUpdate.setEmail(user.getEmail());
        userForUpdate.setUsername(user.getUsername());
        userForUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        userForUpdate.setRoles(new HashSet<>());
        String[] roles = inputRoles.split(",\\s*");
        for (String role : roles) {
            addRoleToUser(role, userForUpdate);
        }
        userRepository.save(userForUpdate);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addRoleToUser(String roleName, User user) {
        Role role = roleService.findByName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /*@Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        List<GrantedAuthority> authorities = user.getRoles().stream().map(x -> new SimpleGrantedAuthority(x.getName())).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }*/
}
