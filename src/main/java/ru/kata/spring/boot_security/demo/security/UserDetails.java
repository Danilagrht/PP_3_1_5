package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*List<ru.kata.spring.boot_security.demo.security.GrantedAuthority> autorities = getRoles().stream().map(x -> new ru.kata.spring.boot_security.demo.security.GrantedAuthority(x.getName())).collect(Collectors.toList());
        return autorities;*/
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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
        return false;
    }
}
