package com.example.SpringSecurity.Repository;

import com.example.SpringSecurity.entity.users.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String username);

    @Modifying
    @Transactional
    @Query(value = "update user set password = :Password where id = :Id", nativeQuery = true)
    public void updatePassword(@Param("Password") String password, @Param("Id") Long id);



}
