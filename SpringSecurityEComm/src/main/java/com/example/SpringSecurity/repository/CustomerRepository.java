package com.example.SpringSecurity.repository;

import com.example.SpringSecurity.entity.users.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    Customer findById(Long Id);
}
