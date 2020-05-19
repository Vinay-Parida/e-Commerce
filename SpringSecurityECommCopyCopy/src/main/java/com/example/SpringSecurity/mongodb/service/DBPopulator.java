package com.example.SpringSecurity.mongodb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class DBPopulator implements ApplicationRunner {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        //This is done to populate the database because without it the collection will not show up in database

//        Logs logs = new Logs();
//        logs.setLogger("Logger");
//        logs.setDate(new Date());
//        logs.setMessage("Test message");
//        logs.setLevel("INFO");

        //        mongoTemplate.save(logs);


    }
}
