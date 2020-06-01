package com.example.springsecurity.repository;

import com.example.springsecurity.entity.users.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    Customer findById(Long id);

    @Query(value = "select u.id, u.first_name, u.last_name, u.is_active, c.contact, u.image from user u inner join customer c on u.id = c.id where email =:Email",nativeQuery = true)
    public List<Object[]> getCustomerDetails(@Param("Email") String email);

    Customer findByEmail(String email);
}
