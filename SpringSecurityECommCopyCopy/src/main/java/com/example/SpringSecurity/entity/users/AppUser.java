package com.example.SpringSecurity.entity.users;

import com.example.SpringSecurity.security.GrantAuthorityImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AppUser implements UserDetails {

    private String username;
    private String password;
    private Boolean isAccountLocked;
    private List<GrantAuthorityImpl> grantAuthorities;

    public AppUser(String username, String password, Boolean isAccountLocked, List<GrantAuthorityImpl> grantAuthorities) {
        this.username = username;
        this.password = password;
        this.isAccountLocked = isAccountLocked;
        this.grantAuthorities = grantAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
