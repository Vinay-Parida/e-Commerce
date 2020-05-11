package com.example.SpringSecurity.service;

import com.example.SpringSecurity.entity.users.AppUser;
import com.example.SpringSecurity.entity.users.Role;
import com.example.SpringSecurity.entity.users.User;
import com.example.SpringSecurity.repository.UserRepository;
import com.example.SpringSecurity.security.GrantAuthorityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserService {

    @Autowired
    UserRepository userRepository;

    public AppUser loadByUsername(String username){
        User user = userRepository.findByEmail(username);
        List<GrantAuthorityImpl> grantAuthorities = new ArrayList<>();
        //Caused by: java.lang.NullPointerException: null
        for(Role role: user.getRoles()){
            grantAuthorities.add(new GrantAuthorityImpl(role.getAuthority()));
        }

        if(username!= null){
            return new AppUser(user.getEmail(), user.getPassword(), user.isAccountNotLocked(), grantAuthorities);
        }
        else {
            throw new RuntimeException("User not found");
        }
    }
}
