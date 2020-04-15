package com.example.SpringSecurity.dao;

import com.example.SpringSecurity.repository.CustomerRepository;
import com.example.SpringSecurity.repository.SellerRepository;
import com.example.SpringSecurity.repository.UserRepository;
import com.example.SpringSecurity.dto.FindAllCustomerDto;
import com.example.SpringSecurity.dto.FindAllSellerDto;
import com.example.SpringSecurity.entity.users.Customer;
import com.example.SpringSecurity.entity.users.Seller;
import com.example.SpringSecurity.entity.users.User;
import com.example.SpringSecurity.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class AdminDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MessageSource messageSource;

    public List<FindAllCustomerDto> getCustomersList(int size, int offset, String field) {

        PageRequest pageRequest = PageRequest.of(offset, size, Sort.Direction.ASC, field);

        List<FindAllCustomerDto> findAllCustomerDtos = new ArrayList<>();
        List<Object[]> customerDetails = userRepository.getCustomerDetails(pageRequest);

        for (Object[] customer : customerDetails) {
            findAllCustomerDtos.add(new FindAllCustomerDto((BigInteger) customer[0],
                    ((String) customer[1] + " " + (String) customer[2] + " " + (String) customer[3]),
                    (String) customer[4], (Boolean) customer[5]));
        }
        return findAllCustomerDtos;
    }


    public List<FindAllSellerDto> getSellersList(int size, int offset, String field) {

        PageRequest pageRequest = PageRequest.of(offset, size, Sort.Direction.ASC, field);

        List<FindAllSellerDto> findAllSellerDtos = new ArrayList<>();
        List<Object[]> sellerDetails = userRepository.getSellerDetails(pageRequest);

        for (Object[] seller : sellerDetails) {
            findAllSellerDtos.add(new FindAllSellerDto((BigInteger) seller[0],
                    ((String) seller[1] + " " + (String) seller[2] + " " + (String) seller[3]),
                    (String) seller[4], (Boolean) seller[5],
                    (String) seller[6], (String) seller[7]));
        }
        return findAllSellerDtos;
    }

    //Customer

    public String activateCustomer(Long customerId, WebRequest webRequest) {

        Locale locale = webRequest.getLocale();

        Customer customer = customerRepository.findById(customerId);

        if (customer == null) {
            String messageCustomerNotFound = messageSource.getMessage("exception.customer.not.found", null, locale);
            throw new UserNotFoundException(messageCustomerNotFound);
        }

        User user = userRepository.getUserById(customerId);

        if (user.isIs_active() == true) {
            return messageSource.getMessage("customer.already.active", null, locale);
        } else {
            user.setIs_active(true);

            String receiverEmail = user.getEmail();
            String messageSubject = messageSource.getMessage("customer.activated", null, locale);
            String messageText = messageSource.getMessage("customer.activated.by.admin", null, locale);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(receiverEmail);
            mailMessage.setSubject(messageSubject);
            mailMessage.setText(messageText);
            javaMailSender.send(mailMessage);

            userRepository.save(user);
            String messageUserCustomerActivated = messageSource.getMessage("customer.activated.successfully", null, locale);
            return messageUserCustomerActivated;
        }
    }


    public String deactivateCustomer(Long customerId, WebRequest webRequest) {

        Locale locale = webRequest.getLocale();

        Customer customer = customerRepository.findById(customerId);

        if (customer == null) {
            String messageCustomerNotFound = messageSource.getMessage("exception.customer.not.found", null, locale);
            throw new UserNotFoundException(messageCustomerNotFound);
        }

        User user = userRepository.getUserById(customerId);

        if (user.isIs_active() == false) {
            return messageSource.getMessage("customer.already.deactivate", null, locale);
        } else {
            user.setIs_active(false);

            String receiverEmail = user.getEmail();
            String messageSubject = messageSource.getMessage("customer.deactivated", null, locale);
            String messageText = messageSource.getMessage("customer.deactivated.by.admin", null, locale);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(receiverEmail);
            mailMessage.setSubject(messageSubject);
            mailMessage.setText(messageText);
            javaMailSender.send(mailMessage);

            userRepository.save(user);
            String messageUserCustomerActivated = messageSource.getMessage("customer.deactivated.successfully", null, locale);
            return messageUserCustomerActivated;
        }
    }

    //Seller

    public String activateSeller(Long id, WebRequest webRequest) {

        Locale locale = webRequest.getLocale();

        Seller seller = sellerRepository.findById(id);

        if (seller == null) {
            String messageSellerNotFound = messageSource.getMessage("exception.seller.not.found", null, locale);
            throw new UserNotFoundException(messageSellerNotFound);
        }

        User user = userRepository.getUserById(id);

        if (user.isIs_active() == true) {
            return messageSource.getMessage("seller.already.active", null, locale);
        } else {
            user.setIs_active(true);

            String receiverEmail = user.getEmail();
            String messageSubject = messageSource.getMessage("seller.activated", null, locale);
            String messageText = messageSource.getMessage("seller.activated.by.admin", null, locale);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(receiverEmail);
            mailMessage.setSubject(messageSubject);
            mailMessage.setText(messageText);
            javaMailSender.send(mailMessage);

            userRepository.save(user);
            String messageUserSellerActivated = messageSource.getMessage("seller.activated.successfully", null, locale);
            return messageUserSellerActivated;
        }
    }


    public String deactivateSeller(Long id, WebRequest webRequest) {

        Locale locale = webRequest.getLocale();

        Seller seller = sellerRepository.findById(id);

        if (seller == null) {
            String messageSellerNotFound = messageSource.getMessage("exception.seller.not.found", null, locale);
            throw new UserNotFoundException(messageSellerNotFound);
        }

        User user = userRepository.getUserById(id);

        if (user.isIs_active() == false) {
            return messageSource.getMessage("seller.already.deactivate", null, locale);
        } else {
            user.setIs_active(false);

            String receiverEmail = user.getEmail();
            String messageSubject = messageSource.getMessage("seller.deactivated", null, locale);
            String messageText = messageSource.getMessage("seller.deactivated.by.admin", null, locale);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(receiverEmail);
            mailMessage.setSubject(messageSubject);
            mailMessage.setText(messageText);
            javaMailSender.send(mailMessage);

            userRepository.save(user);
            String messageUserSellerActivated = messageSource.getMessage("seller.deactivated.successfully", null, locale);
            return messageUserSellerActivated;
        }
    }

}
