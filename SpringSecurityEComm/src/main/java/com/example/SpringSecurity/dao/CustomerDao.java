package com.example.SpringSecurity.dao;

import com.example.SpringSecurity.repository.CustomerRepository;
import com.example.SpringSecurity.repository.UserRepository;
import com.example.SpringSecurity.repository.VerificationTokenRepository;
import com.example.SpringSecurity.dto.CustomerRegisterDto;
import com.example.SpringSecurity.entity.users.Customer;
import com.example.SpringSecurity.entity.users.Name;
import com.example.SpringSecurity.entity.users.Role;
import com.example.SpringSecurity.exceptions.EmailException;
import com.example.SpringSecurity.modals.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

@EnableAutoConfiguration
@Component
public class CustomerDao {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MessageSource messageSource;

    public String registerCustomer(CustomerRegisterDto customerDto, WebRequest webRequest){
        Locale locale = webRequest.getLocale();
        if(userRepository.findByEmail(customerDto.getEmail()) != null){
            String messageEmailAlreadyExists = messageSource.getMessage("exception.email.already.exists", null, locale);
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

            String token = UUID.randomUUID().toString();
            VerificationToken verificationToken = new VerificationToken(token,user1, new VerificationToken().calculateExpiryDate(new VerificationToken().getEXPIRATION()));
            verificationTokenRepository.save(verificationToken);

            String receiverEmail = user1.getEmail();
            String subject = "Registration Confirmation for Customer";
            String confirmationUrl = webRequest.getContextPath() + "/registrationConfirm?token=" + token;
            String message = "Registration Successful \n Click the link to activate the user ";

            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(receiverEmail);
            email.setSubject(subject);
            email.setText(message + "http://localhost:8080" + confirmationUrl);
            javaMailSender.send(email);

            String messageSuccessful = messageSource.getMessage("customer.registration.successful", null, locale);
            return messageSuccessful;
        }
    }



}
