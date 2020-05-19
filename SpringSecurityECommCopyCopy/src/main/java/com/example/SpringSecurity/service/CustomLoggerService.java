package com.example.SpringSecurity.service;

import com.example.SpringSecurity.mongodb.model.Logs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
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
        try{
            mongoTemplate.insert(logs);
        }
        catch (Exception e){
            System.out.println("This exception: " + e);
        }
    }


}
