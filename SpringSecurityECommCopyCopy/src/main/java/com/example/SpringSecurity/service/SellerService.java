package com.example.SpringSecurity.service;

import com.example.SpringSecurity.dto.SellerProfileDTO;
import com.example.SpringSecurity.entity.users.*;
import com.example.SpringSecurity.exceptions.UserNotFoundException;
import com.example.SpringSecurity.repository.AddressRepository;
import com.example.SpringSecurity.repository.SellerRepository;
import com.example.SpringSecurity.repository.UserRepository;
import com.example.SpringSecurity.repository.VerificationTokenRepository;
import com.example.SpringSecurity.dto.SellerRegisterDTO;
import com.example.SpringSecurity.exceptions.EmailException;
import com.example.SpringSecurity.exceptions.GstException;
import com.example.SpringSecurity.modals.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AddressRepository addressRepository;

    public String registerSeller(SellerRegisterDTO sellerDto, WebRequest webRequest){
        Locale locale = webRequest.getLocale();
        if(userRepository.findByEmail(sellerDto.getEmail()) != null){
            String messageEmailAlreadyExists = messageSource.getMessage("exception.email.already.exists", null, locale);
            throw new EmailException(messageEmailAlreadyExists);
        }
        else if(sellerRepository.findByGst(sellerDto.getGst()) != null){
            String messageGstAlreadyExists = messageSource.getMessage("exception.gst.already.exists", null, locale);
            throw new GstException(messageGstAlreadyExists);
        }
        else if(!sellerDto.getPassword().equals(sellerDto.getConfirmPassword())){
            return messageSource.getMessage("exception.password.confirmpassword.dont.match", null, locale);
        }
        else {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Seller user1 = new Seller();
            user1.setEmail(sellerDto.getEmail());
            Name name = new Name();
            name.setFirstName(sellerDto.getFirstName());
            name.setMiddleName(sellerDto.getMiddleName());
            name.setLastName(sellerDto.getLastName());
            user1.setName(name);

            Address address = new Address();
            address.setAddressLine(sellerDto.getAddressLine());
            address.setCity(sellerDto.getCity());
            address.setState(sellerDto.getState());
            address.setCountry(sellerDto.getCountry());
            address.setZipCode(sellerDto.getZipCode());
            address.setLabel(sellerDto.getLabel());

            user1.addAddresses(address);

            user1.setIsActive(false);
            user1.setPassword(passwordEncoder.encode(sellerDto.getPassword()));
            user1.setRoles(Arrays.asList(new Role("ROLE_SELLER")));
            user1.setCompanyContact(sellerDto.getCompanyContact());
            user1.setCompanyName(sellerDto.getCompanyName());
            user1.setGst(sellerDto.getGst());

            user1.setPasswordLastModified(new Date());

            sellerRepository.save(user1);

            String token = UUID.randomUUID().toString();
            VerificationToken verificationToken = new VerificationToken(token,user1, new VerificationToken().calculateExpiryDate(VerificationToken.getEXPIRATION()));
            verificationTokenRepository.save(verificationToken);

            String receiverEmail = user1.getEmail();
            String subject = "Registration for Seller";
            String message = "Registration Successful \n Wait for admin to approve your account ";

            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(receiverEmail);
            email.setSubject(subject);
            email.setText(message);
            javaMailSender.send(email);

            return messageSource.getMessage("seller.registration.successful", null, locale);
        }
    }

    public SellerProfileDTO getSellerProfile(HttpServletRequest httpServletRequest){
        String email = httpServletRequest.getUserPrincipal().getName();
        Long id = userRepository.findByEmail(email).getId();

        SellerProfileDTO sellerProfileDto = null;

        List<Object[]> sellerDetails = sellerRepository.getSellerProfile(id);
        for (Object[] seller: sellerDetails) {
            sellerProfileDto = new SellerProfileDTO((BigInteger) seller[0],(String) seller[1],(String) seller[2],(Boolean) seller[3],
                    (String) seller[4],(String) seller[5],(String) seller[6],(String) seller[7],
                    (String) seller[8],(String) seller[9],(String) seller[10],(String) seller[11],(String) seller[12]);
        }
        return sellerProfileDto;
    }

    public String updateSellerAddress(HashMap<String, Object> addressDetails, HttpServletRequest httpServletRequest) throws Exception {
        String email = httpServletRequest.getUserPrincipal().getName();
        Long id = userRepository.findByEmail(email).getId();

        Address address = addressRepository.getAddressByUserId(id);

        if (address != null) {
            String addressLine = (String) addressDetails.get("address_Line");
            String city = (String) addressDetails.get("city");
            String state = (String) addressDetails.get("state");
            String country = (String) addressDetails.get("country");
            String zipCode = (String) addressDetails.get("zip_code");

            if (checkNotNull(addressLine)) {
                address.setAddressLine(addressLine);
            }
            if (checkNotNull(city)) {
                address.setCity(city);
            }
            if (checkNotNull(state)) {
                address.setState(state);
            }
            if (checkNotNull(country)) {
                address.setCountry(country);
            }
            if (zipCode != null) {
                address.setZipCode(zipCode);
            }

            addressRepository.save(address);
            return "Address Updated Successfully";
        } else {
            throw new UserNotFoundException("Address Not found");
        }
    }
    private Boolean checkNotNull(String value) {
        return (value != null && !value.equals(""));
    }

    public String updateProfile(HashMap<String, Object> customerDetails, HttpServletRequest httpServletRequest){
        String email = httpServletRequest.getUserPrincipal().getName();

        Seller seller = sellerRepository.findByEmail(email);

        Name name = seller.getName();

        String firstName = (String) customerDetails.get("first_name");
        String middleName = (String) customerDetails.get("middle_name");
        String lastName = (String) customerDetails.get("last_name");
        String companyContact = (String) customerDetails.get("company_contact");

        if (checkNotNull(firstName)){
            name.setFirstName(firstName);
        }
        if (checkNotNull(middleName)){
            name.setMiddleName(middleName);
        }
        if (checkNotNull(lastName)){
            name.setLastName(lastName);
        }
        if (checkNotNull(companyContact)){
            if (!isContactValid(companyContact)) {
                return "Not a Valid Phone number";
            }
            else {
                seller.setCompanyContact(companyContact);
            }
        }
        else {
            return "Phone number Can't be empty";
        }
        seller.setName(name);
        sellerRepository.save(seller);

        return "Update Successful";
    }

    private Boolean isContactValid(String contact){
        String regex = "(^$|[0-9]{10})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(contact);
        return matcher.matches();
    }
}
