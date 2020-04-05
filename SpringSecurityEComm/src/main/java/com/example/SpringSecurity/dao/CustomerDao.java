package com.example.SpringSecurity.dao;

import com.example.SpringSecurity.Repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerDao {

    @Autowired
    CustomerRepository customerRepository;


}
