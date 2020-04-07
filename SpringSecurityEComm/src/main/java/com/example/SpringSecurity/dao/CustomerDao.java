package com.example.SpringSecurity.dao;

import com.example.SpringSecurity.Repository.CustomerRepository;

import com.example.SpringSecurity.Repository.UserRepository;
import com.example.SpringSecurity.dto.CustomerDto;
import com.example.SpringSecurity.entity.users.Customer;
import com.example.SpringSecurity.entity.users.Name;
import com.example.SpringSecurity.entity.users.Role;
import com.example.SpringSecurity.entity.users.User;
import com.example.SpringSecurity.exceptions.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import sun.security.provider.VerificationProvider;

import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

@Component
public class CustomerDao {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    public void registerCustomer(CustomerDto customerDto, WebRequest webRequest){
        Locale locale = webRequest.getLocale();
        if(userRepository.findByEmail(customerDto.getEmail()) != null){
            String messageEmailAlreadyExists = messageSource.getMessage("exception.emailAlreadyExists", null, locale);
            throw new EmailException(messageEmailAlreadyExists);
        }
        else {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Customer user1 = new Customer();
            user1.setEmail(customerDto.getEmail());
            Name name = new Name();
            name.setFirst_name(customerDto.getFirst_name());
            name.setMiddle_name(customerDto.getMiddle_name());
            name.setLast_name(customerDto.getLast_name());
            user1.setName(name);
            user1.setIs_active(false);
            user1.setPassword(passwordEncoder.encode(customerDto.getPassword()));
            user1.setRoles(Arrays.asList(new Role("ROLE_CUSTOMER")));
            user1.setContact(customerDto.getContact());

            customerRepository.save(user1);

//            String token = UUID.randomUUID().toString();
////            VerificationToken




        }
    }

}
