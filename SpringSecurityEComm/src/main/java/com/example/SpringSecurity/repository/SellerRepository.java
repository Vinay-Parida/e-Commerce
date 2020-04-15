package com.example.SpringSecurity.repository;

import com.example.SpringSecurity.entity.users.Seller;
import org.springframework.data.repository.CrudRepository;

public interface SellerRepository extends CrudRepository<Seller, String> {

    Seller findByGst(String gst);
    Seller findById(Long Id);
}
