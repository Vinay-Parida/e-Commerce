package com.example.springsecurity.mongodb.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Document
public class Logs {
    private static Logger logger = null;


    public static void init(Class<?> c){
        logger = LoggerFactory.getLogger(c);
    }

    public Logs() {
    }

    public Logs(Class<?> c){
        init(c);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private Date date;

    private String loggers;
    private String level;
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLogger() {
        return loggers;
    }

    public void setLogger(String logger) {
        this.loggers = logger;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void info(String msg) {
        logger.info(msg);
    }
}
