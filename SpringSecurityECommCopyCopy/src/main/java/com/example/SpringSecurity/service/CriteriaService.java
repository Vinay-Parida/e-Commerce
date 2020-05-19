package com.example.SpringSecurity.service;

import com.example.SpringSecurity.entity.users.User;
import com.example.SpringSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Component
public class CriteriaService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    public List<User> userList(){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        return userRepository.findAll();
    }
}
