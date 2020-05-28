package com.example.springsecurity.service;

import com.example.springsecurity.mongodb.model.Logs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CustomLoggerService {

    @Autowired
    MongoTemplate mongoTemplate;

    public void info(String msg, Class<?> c) {
        Logs logs = new Logs(c);
        logs.setLogger("Logger Demo");
        logs.setDate(new Date());
        logs.setLevel("INFO");
        logs.setMessage(msg);

        logs.info(msg);
        mongoTemplate.insert(logs);
    }


}
