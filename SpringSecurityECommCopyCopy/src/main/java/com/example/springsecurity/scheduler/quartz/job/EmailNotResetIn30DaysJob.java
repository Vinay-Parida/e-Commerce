package com.example.springsecurity.scheduler.quartz.job;

import com.example.springsecurity.entity.users.User;
import com.example.springsecurity.exceptions.EmailException;
import com.example.springsecurity.repository.UserRepository;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class EmailNotResetIn30DaysJob extends QuartzJobBean  {
    private static final Logger logger = LoggerFactory.getLogger(EmailNotResetIn30DaysJob.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("Executing Job");

        List<User> userList = userRepository.findAll();
        for (User user: userList) {
            if (user != null) {
                Date passwordLastModified = user.getPasswordLastModified();
                try{
                    logger.info("Before conversion");
                    LocalDateTime passwordLastModifiedInDateTime = passwordLastModified.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    logger.info("After Conversion");
                    LocalDateTime after30Days = passwordLastModifiedInDateTime.plusMinutes(1);   // change it to .plusMonths
                    LocalDateTime today = LocalDateTime.now();
                    if (today.compareTo(after30Days) >= 0) {
                        String subject = "Please Change your Password";
                        String body = "You haven't changed your password in 30 days. Please change it for more security";
                        String sendTo = user.getEmail();
                        sendMail(mailProperties.getUsername(), sendTo, subject, body);
                    }
                }
                catch (Exception e){
                    logger.error("Inside Catch");
                }
            }
            else {
                throw new EmailException("User Null");
            }
        }
    }

    private void sendMail(String fromEmail, String toEmail, String subject, String body) {
        try {
            logger.info("Sending Email to {}", toEmail);
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
            messageHelper.setSubject(subject);
            messageHelper.setText(body, true);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(toEmail);

            logger.info("All Sets");
            mailSender.send(message);
            logger.info("Mail Sent");
        } catch (MessagingException ex) {
            logger.error("Failed to send email to {}", toEmail);
        }
    }
}
