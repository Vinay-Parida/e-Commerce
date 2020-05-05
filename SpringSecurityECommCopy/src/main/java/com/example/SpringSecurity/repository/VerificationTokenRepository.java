package com.example.SpringSecurity.repository;

import com.example.SpringSecurity.modals.VerificationToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    @Transactional
    @Modifying
    @Query(value = "delete from verification_token where token = :Token", nativeQuery = true)
    public void deleteToken(@Param("Token") String token);

    @Transactional
    @Modifying
    @Query(value = "update verification_token set token = :Token, expiry_date= :NewExpiryDate where user_id = :Id", nativeQuery = true)
    public void updateToken(@Param("Token") String token, @Param("NewExpiryDate") Date date, @Param("Id") Long id);

}
