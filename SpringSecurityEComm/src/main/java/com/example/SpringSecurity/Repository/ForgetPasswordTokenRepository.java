package com.example.SpringSecurity.Repository;

import com.example.SpringSecurity.modals.ForgetPasswordToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface ForgetPasswordTokenRepository extends CrudRepository<ForgetPasswordToken, Long> {

    ForgetPasswordToken findByToken(String token);

    @Modifying
    @Transactional
    @Query(value = "delete from forget_password_token where token = :Token", nativeQuery = true)
    public void deleteToken(@Param("Token") String token);
}
