package ru.kata.spring.boot_security.demo.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @ManyToMany
    @ToString.Exclude
    private Set<Role> roles = new HashSet<>();

    public User(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.isEnabled = true;
    }

    public String getHighRole() {
        List<Role> sortedRoles = new ArrayList<>(roles);
        Collections.sort(sortedRoles, Comparator.comparing(Role::getName));

        if (!sortedRoles.isEmpty()) {
            return sortedRoles.get(0).getName();
        } else {
            return "No roles assigned";
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
