package com.example.springsecurity.repository;

import com.example.springsecurity.entity.users.Seller;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SellerRepository extends CrudRepository<Seller, String> {

    Seller findByGst(String gst);
    Seller findById(Long id);

    @Query(value = "select u.id, u.first_name, u.last_name, u.is_active, s.company_name, s.company_contact, u.image, s.gst, a.address_line, a.city, a.country, a.state, a.zip_code from user u inner join seller s on u.id=s.id inner join address a on a.user_id = s.id", nativeQuery = true)
    public List<Object[]> getSellerProfile(@Param("Id") Long id);

    Seller findByEmail(String email);
}
