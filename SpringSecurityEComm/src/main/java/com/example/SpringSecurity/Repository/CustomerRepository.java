package com.example.SpringSecurity.Repository;

import com.example.SpringSecurity.entity.users.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

}
