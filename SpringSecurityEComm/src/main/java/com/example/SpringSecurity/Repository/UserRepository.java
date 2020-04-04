package com.example.SpringSecurity.Repository;

import com.example.SpringSecurity.entity.users.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

}
