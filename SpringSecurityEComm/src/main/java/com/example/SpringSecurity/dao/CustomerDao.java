package com.example.SpringSecurity.dao;

import com.example.SpringSecurity.Repository.CustomerRepository;

import com.example.SpringSecurity.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerDao {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserRepository userRepository;

//    public void registerCustomer(CustomerRepository)

}
