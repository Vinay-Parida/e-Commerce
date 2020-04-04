package com.example.SpringSecurity;

import com.example.SpringSecurity.entity.users.AppUser;
import com.example.SpringSecurity.entity.users.Role;
import com.example.SpringSecurity.entity.users.User;
import com.example.SpringSecurity.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    UserRepository userRepository;

    public AppUser loadByUsername(String username){
        User user = userRepository.findByUsername(username);
        List<GrantAuthorityImpl> grantAuthorities = new ArrayList<>();
        for(Role role: user.getRoles()){
            grantAuthorities.add(new GrantAuthorityImpl(role.getAuthority()));
        }

        System.out.println(user);

        if(username!= null){
            return new AppUser(user.getUsername(), user.getPassword(), grantAuthorities);
        }
        else {
            throw new RuntimeException("User not found");
        }
    }
}
