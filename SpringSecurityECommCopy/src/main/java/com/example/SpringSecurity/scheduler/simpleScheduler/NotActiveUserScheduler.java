package com.example.SpringSecurity.scheduler.simpleScheduler;

import com.example.SpringSecurity.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotActiveUserScheduler {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailSender javaMailSender;

    Logger logger = LoggerFactory.getLogger(NotActiveUserScheduler.class);

    @Scheduled(initialDelay = 100000000, fixedDelay = 1000000)
    public void scheduledMailSender(){
        List<String> userEmails = userRepository.getNotActiveUserEmail();

        for (String email: userEmails) {

            logger.info("Sending mail for " + email);

            String subject = "Account not active.";
            String text = "Your Email " + email + " is not active. \n Please active it or contact admin for info";

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(text);
            javaMailSender.send(simpleMailMessage);

            logger.info("Email sent to " + email);
        }
    }
}
