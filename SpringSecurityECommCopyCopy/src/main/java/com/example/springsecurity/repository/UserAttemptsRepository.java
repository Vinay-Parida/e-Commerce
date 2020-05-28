package com.example.springsecurity.repository;

import com.example.springsecurity.modals.UserAttempts;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UserAttemptsRepository extends CrudRepository<UserAttempts, Long> {

    UserAttempts findByEmail(String email);

    @Query(value = "update user_attempts set attempts =:Attempts where email =:Email", nativeQuery = true)
    @Modifying
    @Transactional
    public void updateAttempts(@Param("Attempts") Integer attempts, @Param("Email") String email);
}
