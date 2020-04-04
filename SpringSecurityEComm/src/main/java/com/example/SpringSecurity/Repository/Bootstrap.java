package com.example.SpringSecurity.Repository;

import com.example.SpringSecurity.entity.users.Name;
import com.example.SpringSecurity.entity.users.Role;
import com.example.SpringSecurity.entity.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(userRepository.count()<1){
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            User user1 = new User();
            user1.setName(new Name("Vinay", "", "Parida"));
            user1.setPassword(passwordEncoder.encode("pass"));
            user1.setRoles(Arrays.asList(new Role("ROLE_USER")));
            user1.setEmail("user1email");


            
            userRepository.save(user1);

        }

    }
}
