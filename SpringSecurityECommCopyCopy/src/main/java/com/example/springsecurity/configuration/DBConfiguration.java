package com.example.springsecurity.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties("spring.datasource")
@SuppressWarnings("unused")
public class DBConfiguration {

    @Profile("dev")
    @Bean
    public String devDatabaseConnection(){
        return "DB Connection for dev";
    }

    @Profile("qa")
    @Bean
    public String qaDatabaseConnection(){
        return "DB Connection for QA";
    }
}
