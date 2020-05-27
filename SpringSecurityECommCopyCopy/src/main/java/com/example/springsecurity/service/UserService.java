package com.example.springsecurity.service;

import com.example.springsecurity.entity.users.AppUser;
import com.example.springsecurity.entity.users.Role;
import com.example.springsecurity.entity.users.User;
import com.example.springsecurity.exceptions.UserNotFoundException;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.security.GrantAuthorityImpl;
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
            throw new UserNotFoundException("User not found");
        }
    }
}
