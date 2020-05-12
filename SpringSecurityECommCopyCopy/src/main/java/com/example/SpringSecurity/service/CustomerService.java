package com.example.SpringSecurity.service;

import com.example.SpringSecurity.dto.AddressDTO;
import com.example.SpringSecurity.dto.CustomerRegisterDTO;
import com.example.SpringSecurity.dto.SellerProfileDTO;
import com.example.SpringSecurity.entity.users.*;
import com.example.SpringSecurity.exceptions.EmailException;
import com.example.SpringSecurity.exceptions.UserNotFoundException;
import com.example.SpringSecurity.exceptions.ValueNotFoundException;
import com.example.SpringSecurity.modals.VerificationToken;
import com.example.SpringSecurity.repository.AddressRepository;
import com.example.SpringSecurity.repository.CustomerRepository;
import com.example.SpringSecurity.repository.UserRepository;
import com.example.SpringSecurity.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.Principal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@EnableAutoConfiguration
@Component
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MessageSource messageSource;

    public String registerCustomer(CustomerRegisterDTO customerDto, WebRequest webRequest) {
        Locale locale = webRequest.getLocale();
        if (userRepository.findByEmail(customerDto.getEmail()) != null) {
            String messageEmailAlreadyExists = messageSource.getMessage("exception.email.already.exists", null, locale);
            throw new EmailException(messageEmailAlreadyExists);
        } else {
            if (customerDto.getPassword().equals(customerDto.getConfirmPassword())) {
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                Customer user1 = new Customer();
                user1.setEmail(customerDto.getEmail());
                Name name = new Name();
                name.setFirstName(customerDto.getFirst_name());
                name.setMiddleName(customerDto.getMiddle_name());
                name.setLastName(customerDto.getLast_name());
                user1.setName(name);
                user1.setIsActive(false);
                user1.setPassword(passwordEncoder.encode(customerDto.getPassword()));
                user1.setRoles(Arrays.asList(new Role("ROLE_CUSTOMER")));
                user1.setContact(customerDto.getContact());

                user1.setPasswordLastModified(new Date());

                customerRepository.save(user1);

                String token = UUID.randomUUID().toString();
                VerificationToken verificationToken = new VerificationToken(token, user1, new VerificationToken().calculateExpiryDate(new VerificationToken().getEXPIRATION()));
                verificationTokenRepository.save(verificationToken);

                String receiverEmail = user1.getEmail();
                String subject = "Registration Confirmation for Customer";
                String confirmationUrl = webRequest.getContextPath() + "/registrationConfirm?token=" + token;
                String message = "Registration Successful \n Click the link to activate the user ";

                SimpleMailMessage email = new SimpleMailMessage();
                email.setTo(receiverEmail);
                email.setSubject(subject);
                email.setText(message + "http://localhost:8080" + confirmationUrl);
                javaMailSender.send(email);

                String messageSuccessful = messageSource.getMessage("customer.registration.successful", null, locale);
                return messageSuccessful;
            }
            else {
                return messageSource.getMessage("exception.password.confirmpassword.dont.match", null, locale);
            }
        }
    }



    public SellerProfileDTO getProfile(HttpServletRequest httpServletRequest) {

//        SimpleFilterProvider filterProvider = new SimpleFilterProvider().addFilter("customerFilter",
//                SimpleBeanPropertyFilter.filterOutAllExcept("id", "firstName", "lastName", "isActive", "image"));
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setFilterProvider(filterProvider);

        Principal principal = httpServletRequest.getUserPrincipal();
        String email = principal.getName();

        SellerProfileDTO customerProfileDto = new SellerProfileDTO();

        List<Object[]> customerDetails = customerRepository.getCustomerDetails(email);
        for (Object[] customer : customerDetails) {
//            customerProfileDto = new CustomerProfileDTO((BigInteger) customer[0], (String) customer[1], (String) customer[2], (Boolean) customer[3], (String) customer[4], (String) customer[5]);

            customerProfileDto.setId((BigInteger) customer[0]);
            customerProfileDto.setFirstName((String) customer[1]);
            customerProfileDto.setLastName((String) customer[2]);
            customerProfileDto.setIsActive((Boolean) customer[3]);
            customerProfileDto.setImage((String) customer[5]);
            customerProfileDto.setContact((String) customer[4]);
        }
        return customerProfileDto;
    }


    public String addAddress(AddressDTO addressDto, HttpServletRequest httpServletRequest) {
        String email = httpServletRequest.getUserPrincipal().getName();
        Long id = userRepository.findByEmail(email).getId();

        Customer customer = customerRepository.findById(id);

        Address address = new Address();
        address.setAddressLine(addressDto.getAddress_line());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setCountry(addressDto.getCountry());
        address.setZipCode(addressDto.getZip_code());
        address.setLabel(addressDto.getLabel());

        address.setUser(customer);
        addressRepository.save(address);

        return "Address Saved Successfully";
    }

    public List<AddressDTO> getAddressList(HttpServletRequest httpServletRequest) {
        List<AddressDTO> addressDtoList = new ArrayList<>();

        String email = httpServletRequest.getUserPrincipal().getName();

        Long id = userRepository.findByEmail(email).getId();
        List<Address> addresses = addressRepository.getAddress(id);

        addresses.forEach(address -> {
            AddressDTO addressDto = new AddressDTO(address.getCity(), address.getState(), address.getCountry(), address.getAddressLine(), address.getLabel(), address.getZipCode());
            addressDtoList.add(addressDto);
        });
        return addressDtoList;
    }


    public String updateProfile(HashMap<String, Object> customerDetails, HttpServletRequest httpServletRequest) {
        String email = httpServletRequest.getUserPrincipal().getName();
        Customer customer = customerRepository.findByEmail(email);

        Name name = customer.getName();

        String first_name = (String) customerDetails.get("first_name");
        String middle_name = (String) customerDetails.get("middle_name");
        String last_name = (String) customerDetails.get("last_name");
        String contact = (String) customerDetails.get("contact");


        if (checkNotNull(first_name)) {
            name.setFirstName(first_name);
        }
        if (checkNotNull(middle_name)) {
            name.setMiddleName(middle_name);
        }
        if (checkNotNull(last_name)) {
            name.setLastName(last_name);
        }
        if (checkNotNull(contact)) {
            if (!isContactValid(contact)) {
                return "Not a Valid Phone number";
            } else {
                customer.setContact(contact);
            }
        }

        customer.setName(name);
        customerRepository.save(customer);

        return "Update Successful";
    }

    private Boolean isContactValid(String contact) {
        String regex = "(^$|[0-9]{10})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(contact);
        return matcher.matches();
    }

    private Boolean checkNotNull(String value) {
        if (value != null && !value.equals(""))
            return true;
        else
            return false;
    }


    public String updatePassword(String password, String confirmPassword, HttpServletRequest httpServletRequest) {
        String regex = "((?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,25})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        Boolean isValidPassword = matcher.matches();

        String email = httpServletRequest.getUserPrincipal().getName();
        User user = userRepository.findByEmail(email);

        if (isValidPassword == false) {
            return "Password is not valid";
        } else if (password.equals(confirmPassword) == false) {
            return "Password and Confirm Password Does not matches";
        } else {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String newPassword = passwordEncoder.encode(password);
            userRepository.updatePassword(newPassword, user.getId());
            user.setPasswordLastModified(new Date());
            userRepository.save(user);
            return "Password Updated Successfully";
        }
    }

    public String deleteAddress(Long addressId, HttpServletRequest httpServletRequest) {
        String email = httpServletRequest.getUserPrincipal().getName();
        Long id = userRepository.findByEmail(email).getId();
        Address address = addressRepository.getAddressByUserAndAddressId(id, addressId);
        if (address != null) {
            addressRepository.deleteById(addressId);
            return "Address Deleted Successfully";
        } else {
            throw new ValueNotFoundException("Address not found");
        }
    }

    public String updateAddress(HashMap<String, Object> addressDetails, Long addressId, HttpServletRequest httpServletRequest) throws Exception {
        String email = httpServletRequest.getUserPrincipal().getName();
        Long id = userRepository.findByEmail(email).getId();

        Address address = addressRepository.getAddressByUserAndAddressId(id, addressId);

        try {
            if (address != null) {
                String address_line = (String) addressDetails.get("address_line");
                String city = (String) addressDetails.get("city");
                String state = (String) addressDetails.get("state");
                String country = (String) addressDetails.get("country");
                String zip_code = (String) addressDetails.get("zip_code");

                if (checkNotNull(address_line)) {
                    address.setAddressLine(address_line);
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
                if (zip_code != null) {
                    address.setZipCode(zip_code);
                }

                addressRepository.save(address);
                return "Address Updated Successfully";
            } else {
                throw new UserNotFoundException("Address Not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }


    }


}
