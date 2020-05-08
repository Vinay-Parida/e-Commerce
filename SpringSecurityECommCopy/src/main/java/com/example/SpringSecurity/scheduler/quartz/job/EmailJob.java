package com.example.SpringSecurity.scheduler.quartz.job;

import com.example.SpringSecurity.repository.ProductVariationRepository;
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
import java.util.List;

@Component
public class EmailJob extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(EmailJob.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private ProductVariationRepository variationRepository;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("Executing Job");

//        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
//        String subject = jobDataMap.getString("subject");
//        String body = jobDataMap.getString("body");
//        String recipientEmail = jobDataMap.getString("email");

        List<Object[]> sellerDetails = variationRepository.getDetailedProductAndEmailWithProductVariationNone();

        for (Object[] objects: sellerDetails) {
            String subject = "Product Quantity is 0";
            String text = "Your product quantity is 0 for variation: " + objects[1] + objects[2] + objects[3] + objects[4];
            String sentTo = (String) objects[0];

            sendMail(mailProperties.getUsername(), sentTo, subject, text);
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

            logger.info("All Set");
            mailSender.send(message);
            logger.info("Mail Sent");
        } catch (MessagingException ex) {
            logger.error("Failed to send email to {}", toEmail);
        }
    }
}
