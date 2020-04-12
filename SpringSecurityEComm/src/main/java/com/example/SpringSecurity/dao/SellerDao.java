package com.example.SpringSecurity.dao;

import com.example.SpringSecurity.Repository.SellerRepository;
import com.example.SpringSecurity.Repository.UserRepository;
import com.example.SpringSecurity.Repository.VerificationTokenRepository;
import com.example.SpringSecurity.dto.SellerDto;
import com.example.SpringSecurity.entity.users.Name;
import com.example.SpringSecurity.entity.users.Role;
import com.example.SpringSecurity.entity.users.Seller;
import com.example.SpringSecurity.exceptions.EmailException;
import com.example.SpringSecurity.exceptions.GstException;
import com.example.SpringSecurity.modals.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
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

@Component
public class SellerDao {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MessageSource messageSource;

    public String registerSeller(SellerDto sellerDto, WebRequest webRequest){
        Locale locale = webRequest.getLocale();
        if(userRepository.findByEmail(sellerDto.getEmail()) != null){
            String messageEmailAlreadyExists = messageSource.getMessage("exception.email.already.exists", null, locale);
            throw new EmailException(messageEmailAlreadyExists);
        }
        else if(sellerRepository.findByGst(sellerDto.getGst()) != null){
            String messageGstAlreadyExists = messageSource.getMessage("exception.gst.already.exists", null, locale);
            throw new GstException(messageGstAlreadyExists);
        }
        else {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Seller user1 = new Seller();
            user1.setEmail(sellerDto.getEmail());
            Name name = new Name();
            name.setFirst_name(sellerDto.getFirst_name());
            name.setMiddle_name(sellerDto.getMiddle_name());
            name.setLast_name(sellerDto.getLast_name());
            user1.setName(name);
            user1.setIs_active(false);
            user1.setPassword(passwordEncoder.encode(sellerDto.getPassword()));
            user1.setRoles(Arrays.asList(new Role("ROLE_SELLER")));
            user1.setCompany_contact(sellerDto.getCompany_contact());
            user1.setCompany_name(sellerDto.getCompany_name());
            user1.setGst(sellerDto.getGst());

            sellerRepository.save(user1);

            String token = UUID.randomUUID().toString();
            VerificationToken verificationToken = new VerificationToken(token,user1, new VerificationToken().calculateExpiryDate(new VerificationToken().getEXPIRATION()));
            verificationTokenRepository.save(verificationToken);

            String receiverEmail = user1.getEmail();
            String subject = "Registration Confirmation for Seller";
            String confirmationUrl = webRequest.getContextPath() + "/registrationConfirm?token=" + token;
            String message = "Registration Successful \n Click the link to activate the user ";

            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(receiverEmail);
            email.setSubject(subject);
            email.setText(message + "http://localhost:8080" + confirmationUrl);
            javaMailSender.send(email);

            String messageSuccessful = messageSource.getMessage("seller.registration.successful", null, locale);
            return messageSuccessful;


        }
    }

}
