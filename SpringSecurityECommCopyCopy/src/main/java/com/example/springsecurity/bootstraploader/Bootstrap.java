package com.example.springsecurity.bootstraploader;

import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.entity.users.*;
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
        if (userRepository.count() < 1) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            User user1 = new User();
            Name name = new Name();
            name.setFirstName("Vinay");
            name.setLastName("Parida");
            user1.setName(name);
            user1.setPassword(passwordEncoder.encode("pass"));
            user1.setRoles(Arrays.asList(new Role("ROLE_ADMIN")));
            user1.setEmail("vincdprime@gmail.com");

            user1.setIsActive(true);

            userRepository.save(user1);
        }
    }
}
