package com.example.SpringSecurity.mongodb;

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



    }
}
