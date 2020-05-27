package com.example.springsecurity.filters;

import com.example.springsecurity.entity.users.User;
import com.example.springsecurity.exceptions.UserNotFoundException;
import com.example.springsecurity.repository.UserAttemptsRepository;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.service.UserAttemptsService;
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
    UserAttemptsService userAttemptsService;

    @Autowired
    UserAttemptsRepository userAttemptsRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public Authentication authenticate(Authentication authentication) {

        try {

            Authentication auth = super.authenticate(authentication);

            String email = authentication.getName();
            User user = userRepository.findByEmail(email);

            if (user != null) {
                if(!userAttemptsService.checkIsActive(email)){
                    throw new UserNotFoundException("Login without activating account");
                }
                else if (userAttemptsService.checkLock(email) && userAttemptsService.checkIsActive(email)) {
                    userAttemptsService.updateAttemptsToNull(email);
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

                    throw new UserNotFoundException("User is locked");
                }
            }
        } catch (Exception e) {
            String email = authentication.getName();
            userAttemptsService.getUserAttempts(email);
            userAttemptsService.updateAttempts(email);
            throw e;
        }
        return null;
    }
}
