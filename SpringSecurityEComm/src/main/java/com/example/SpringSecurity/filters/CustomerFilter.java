package com.example.SpringSecurity.filters;

import com.example.SpringSecurity.Repository.UserAttemptsRepository;
import com.example.SpringSecurity.Repository.UserRepository;
import com.example.SpringSecurity.dao.UserAttemptsDao;
import com.example.SpringSecurity.entity.users.User;
import com.example.SpringSecurity.exceptions.UserNotFoundException;
import com.example.SpringSecurity.modals.UserAttempts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;

public class CustomerFilter extends DaoAuthenticationProvider {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserAttemptsDao userAttemptsDao;

    @Autowired
    UserAttemptsRepository userAttemptsRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    JavaMailSender javaMailSender;

    //Internal server Error is showing instead of Bad client credentials if wrong password is entered

    @Override
    public Authentication authenticate(Authentication authentication) {

        try {
            Authentication auth = super.authenticate(authentication);

            String email = authentication.getName();
            User user = userRepository.findByEmail(email);

            if (user != null) {
                UserAttempts userAttempts = userAttemptsDao.getUserAttempts(email);
                if(userAttemptsDao.checkIsActive(email) == false){

//                    String receiverEmail = user.getEmail();
//                    String messageSubject = "Invalid login";
//                    String messageText = "You are tying to login without activating your account";
//
//                    SimpleMailMessage mailMessage = new SimpleMailMessage();
//                    mailMessage.setTo(receiverEmail);
//                    mailMessage.setSubject(messageSubject);
//                    mailMessage.setText(messageText);
//                    javaMailSender.send(mailMessage);

                    throw new UserNotFoundException("Login without activating account");             //Make a custom exception
                }
                else if (userAttemptsDao.checkLock(email) && userAttemptsDao.checkIsActive(email)) {
                    userAttemptsDao.updateAttemptsToNull(email);
                    return auth;
                }
                else {
                    user.setAccountNotLocked(false);
                    userRepository.save(user);

                    String receiverEmail = user.getEmail();
                    String messageSubject = "Account Locked";
                    String messageText = "Account is Locked because of 3 unsuccessful attempts";

                    SimpleMailMessage mailMessage = new SimpleMailMessage();
                    mailMessage.setTo(receiverEmail);
                    mailMessage.setSubject(messageSubject);
                    mailMessage.setText(messageText);
                    javaMailSender.send(mailMessage);

                    throw new UserNotFoundException("User is locked");     //Make a custom exception
                }
            }
        } catch (Exception e) {
            String email = authentication.getName();
            userAttemptsDao.getUserAttempts(email);
            userAttemptsDao.updateAttempts(email);
//            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
//            e.printStackTrace();
            throw new UserNotFoundException("User is Invalid");           //Make a custom exception
        }
        return null;
    }
}
