package com.example.springsecurity.service;

import com.example.springsecurity.entity.users.User;
import com.example.springsecurity.exceptions.EmailException;
import com.example.springsecurity.exceptions.TokenInvalidException;
import com.example.springsecurity.modals.VerificationToken;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.repository.VerificationTokenRepository;
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
        if((verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0){
            String messageTokenExpired = messageSource.getMessage("exception.token.expired", null, locale);
            verificationTokenRepository.deleteToken(token);
            throw  new TokenInvalidException(messageTokenExpired + " " + verificationToken.getExpiryDate().getTime() + " "+ calendar.getTime().getTime());
        }

        user.setIsActive(true);
        userRepository.save(user);
        verificationTokenRepository.deleteToken(token);
        return messageSource.getMessage("activation.user.successful", null, locale);
    }


    public String reactivationUser(String email, WebRequest webRequest){

        Locale locale = webRequest.getLocale();

        User user = userRepository.findByEmail(email);

        if(user == null){
            String messageEmailDoesNotExists = messageSource.getMessage("exception.email.does.not.exists", null, locale);
            throw new EmailException(messageEmailDoesNotExists);
        }
        else if(user.isActive()){
            String messageAccountAlreadyActive = messageSource.getMessage("exception.account.already.active", null, locale);
            throw new EmailException(messageAccountAlreadyActive);
        }

        Long id = user.getId();

        String token = UUID.randomUUID().toString();
        Date newExpiryDate = new VerificationToken().calculateExpiryDate(VerificationToken.getEXPIRATION());

        verificationTokenRepository.updateToken(token, newExpiryDate, id);

        String subject = "Reactivation User";
        String confirmationUrl = webRequest.getContextPath() + "/registrationConfirm?token=" + token;
        String message = "Reactivation Link\n Click here to re activate user \n ";

        sendMail(email, confirmationUrl, message, subject);

        return messageSource.getMessage("reactivation.user.successful", null, locale);
    }

    private void sendMail(String emailRecipient, String confirmationUrl, String message, String subject) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(emailRecipient);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message + "\r\n" + "http://localhost:8080" + confirmationUrl);
        javaMailSender.send(simpleMailMessage);
    }
}
