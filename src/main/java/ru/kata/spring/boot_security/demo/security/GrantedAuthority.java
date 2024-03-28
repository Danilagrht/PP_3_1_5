package ru.kata.spring.boot_security.demo.security;

import ru.kata.spring.boot_security.demo.model.Role;

public class GrantedAuthority extends Role implements org.springframework.security.core.GrantedAuthority {

    public GrantedAuthority(String name) {
        setName(name);
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}