package com.example.SpringSecurity.service;

import com.example.SpringSecurity.repository.UserRepository;
import com.example.SpringSecurity.repository.VerificationTokenRepository;
import com.example.SpringSecurity.entity.users.User;
import com.example.SpringSecurity.exceptions.EmailException;
import com.example.SpringSecurity.exceptions.TokenInvalidException;
import com.example.SpringSecurity.modals.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Component
public class ActivationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    JavaMailSender javaMailSender;

    public String activateUser(String token, WebRequest webRequest){

        Locale locale = webRequest.getLocale();

        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null){
            String messageTokenInvalid = messageSource.getMessage("exception.token.invalid", null, locale);
            throw  new TokenInvalidException(messageTokenInvalid);
        }

        User user = verificationToken.getUser();

        Calendar calendar = Calendar.getInstance();
        if((verificationToken.getExpiry_date().getTime() - calendar.getTime().getTime()) <= 0){
            String messageTokenExpired = messageSource.getMessage("exception.token.expired", null, locale);
            verificationTokenRepository.deleteToken(token);
            throw  new TokenInvalidException(messageTokenExpired + " " + verificationToken.getExpiry_date().getTime() + " "+ calendar.getTime().getTime());
        }

        user.setIsActive(true);
        userRepository.save(user);
        verificationTokenRepository.deleteToken(token);
        String messageActivationUserSuccessful = messageSource.getMessage("activation.user.successful", null, locale);
        return messageActivationUserSuccessful;
    }


    public String reactivationUser(String email, WebRequest webRequest){

        Locale locale = webRequest.getLocale();

        User user = userRepository.findByEmail(email);

        if(user == null){
            String messageEmailDoesNotExists = messageSource.getMessage("exception.email.does.not.exists", null, locale);
            throw new EmailException(messageEmailDoesNotExists);
        }
        else if(user.isActive() == true){
            String messageAccountAlreadyActive = messageSource.getMessage("exception.account.already.active", null, locale);
            throw new EmailException(messageAccountAlreadyActive);
        }

        Long id = user.getId();

        String token = UUID.randomUUID().toString();
        Date newExpiryDate = new VerificationToken().calculateExpiryDate(new VerificationToken().getEXPIRATION());

        verificationTokenRepository.updateToken(token, newExpiryDate, id);

        String receiverEmail = email;
        String subject = "Reactivation User";
        String confirmationUrl = webRequest.getContextPath() + "/registrationConfirm?token=" + token;
        String message = "Reactivation Link\n Click here to re activate user \n ";

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(receiverEmail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message + "\r\n" + "http://localhost:8080" + confirmationUrl);

        javaMailSender.send(simpleMailMessage);

        String messageSuccessful = messageSource.getMessage("reactivation.user.successful", null, locale);

        return messageSuccessful;

    }
}
