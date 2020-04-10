package com.example.SpringSecurity.dao;

import com.example.SpringSecurity.Repository.ForgetPasswordTokenRepository;
import com.example.SpringSecurity.Repository.UserRepository;
import com.example.SpringSecurity.entity.users.User;
import com.example.SpringSecurity.exceptions.EmailException;
import com.example.SpringSecurity.exceptions.PasswordException;
import com.example.SpringSecurity.exceptions.TokenInvalidException;
import com.example.SpringSecurity.modals.ForgetPasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class ForgetPasswordDao {

    @Autowired
    private ForgetPasswordTokenRepository forgetPasswordTokenRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    private String newPassword;


    public String sendForgetPasswordToken(String email, WebRequest webRequest) {
        Locale locale = webRequest.getLocale();
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        Boolean isEmailValid = matcher.matches();

//        @Pattern(regexp="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,})",message="Password must be 8 characters long")
//        String passwordValidate = "((?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,})";
//        Pattern pattern1 = Pattern.compile(passwordValidate);
//        Matcher matcher1 = pattern1.matcher(newPassword);
//        Boolean isValidPassword = matcher1.matches();


        if (isEmailValid) {
            User user = userRepository.findByEmail(email);

            if (user == null) {
                String messageEmailDoesNotExists = messageSource.getMessage("exception.email.does.not.exists", null, locale);
                throw new EmailException(messageEmailDoesNotExists);
            } else if (user.isIs_active() == false) {
                String messageUserNotActivated = messageSource.getMessage("exception.user.not.active", null, locale);
                throw new EmailException(messageUserNotActivated);
            }
//            else if (isValidPassword) {
//                String messageNotAValidPassword = messageSource.getMessage("exception.not.a.valid.password", null, locale);
//                throw new EmailException(messageNotAValidPassword);
//            }
        else {
                String token = UUID.randomUUID().toString();

                ForgetPasswordToken forgetPasswordToken = new ForgetPasswordToken(token, user, new ForgetPasswordToken().calculateExpiryTime(new ForgetPasswordToken().getEXPIRATION()));
                forgetPasswordTokenRepository.save(forgetPasswordToken);

                String receiverEmail = email;
                String subject = "Forget Password";
                String confirmationUrl = webRequest.getContextPath() + "/resetPassword?token=" + token;
                String message = "Link to reset password \n ";

                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setTo(receiverEmail);
                simpleMailMessage.setSubject(subject);
                simpleMailMessage.setText(message + "\r\n" + "http://localhost:8080" + confirmationUrl);

                javaMailSender.send(simpleMailMessage);

                String messageSuccessful = messageSource.getMessage("forget.password.mail.sent.successful", null, locale);

                return messageSuccessful;
            }
        } else {
            String messageInvalidEmail = messageSource.getMessage("exception.invalid.email", null, locale);
            throw new EmailException(messageInvalidEmail);
        }
    }


    public String resetPassword(String token, String password, String confirmPassword, WebRequest webRequest) {

        Locale locale = webRequest.getLocale();

        ForgetPasswordToken forgetPasswordToken = forgetPasswordTokenRepository.findByToken(token);

        if (forgetPasswordToken != null) {
            User user = forgetPasswordToken.getUser();
            Calendar calendar = Calendar.getInstance();

            if ((forgetPasswordToken.getExpiryDate().getTime() - calendar.getTime().getTime()) < 0) {
                String messageTokenExpired = messageSource.getMessage("exception.token.expired", null, locale);
                throw new TokenInvalidException(messageTokenExpired);
            } else if (password.equals(confirmPassword) == false) {
                String messagePasswordNotMatched = messageSource.getMessage("exception.password.confirmpassword.dont.match", null, locale);
                throw new PasswordException(messagePasswordNotMatched);
            } else {
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

                newPassword = passwordEncoder.encode(password);
                userRepository.updatePassword(newPassword, user.getId());
                forgetPasswordTokenRepository.deleteToken(token);

                String messageResetPasswordSuccessful = messageSource.getMessage("reset.password.successful", null, locale);
                return messageResetPasswordSuccessful;
            }
        } else {
            String messageTokenInvalid = messageSource.getMessage("exception.token.invalid", null, locale);
            throw new TokenInvalidException(messageTokenInvalid);
        }
    }
}