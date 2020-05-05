//package com.example.SpringSecurity.scheduler;
//
//import com.example.SpringSecurity.repository.ProductVariationRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class SendMailProductQuantityZero {
//
//    @Autowired
//    ProductVariationRepository productVariationRepository;
//
//    @Autowired
//    JavaMailSender javaMailSender;
//
//    Logger logger = LoggerFactory.getLogger(SendMailProductQuantityZero.class);
//
//    @Scheduled(cron = "*/5 * * * * ?")
//    public void scheduledEmail(){
//        List<Object[]> sellerDetails = productVariationRepository.getDetailedProductAndEmailWithProductVariationNone();
//
//        for (Object[] objects: sellerDetails){
//            String subject = "Product Quantity is 0";
//            String text = "Your product quantity is 0 for variation: " + objects[1] + objects[2] + objects[3] + objects[4];
//            String sentTo = (String) objects[0];
//            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//            simpleMailMessage.setTo(sentTo);
//            simpleMailMessage.setSubject(subject);
//            simpleMailMessage.setText(text);
//            javaMailSender.send(simpleMailMessage);
//
//            logger.info("Mail sent to: " + sentTo);
//
//        }
//    }
//
//}
