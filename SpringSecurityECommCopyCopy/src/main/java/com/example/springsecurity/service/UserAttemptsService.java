package com.example.springsecurity.service;

import com.example.springsecurity.repository.UserAttemptsRepository;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.entity.users.User;
import com.example.springsecurity.modals.UserAttempts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class UserAttemptsService {

    @Autowired
    private UserAttemptsRepository userAttemptsRepository;

    @Autowired
    private UserRepository userRepository;

    private Integer attemptCounts = 0;

    private void doSave(String email){
        UserAttempts userAttempts = new UserAttempts();
        userAttempts.setEmail(email);
        userAttempts.setAttempts(attemptCounts);

        Calendar calendar = Calendar.getInstance();
        userAttempts.setLastModified(new Date(calendar.getTime().getTime()));
        userAttemptsRepository.save(userAttempts);
    }


    public UserAttempts getUserAttempts(String email){
        UserAttempts userAttempts = userAttemptsRepository.findByEmail(email);
        if(userAttempts != null){
            return userAttempts;
        }
        else {
            doSave(email);
            return userAttemptsRepository.findByEmail(email);
        }
    }

    public void updateAttempts(String email){
        UserAttempts userAttempts = userAttemptsRepository.findByEmail(email);
        Integer attempts = userAttempts.getAttempts() + 1;
        userAttemptsRepository.updateAttempts(attempts, email);
    }

    public void updateAttemptsToNull(String email){
        userAttemptsRepository.updateAttempts(0, email);
    }

    private Integer getAttempt(String email){
        return userAttemptsRepository.findByEmail(email).getAttempts();
    }

    public Boolean checkLock(String email){
        Integer attempts = getAttempt(email);
        return attempts <= 3;
    }

    public Boolean checkIsActive(String email){
        User user = userRepository.findByEmail(email);
        return user.isActive();
    }

}
