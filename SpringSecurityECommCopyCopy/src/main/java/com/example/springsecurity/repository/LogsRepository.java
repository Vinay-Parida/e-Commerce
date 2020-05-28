package com.example.springsecurity.repository;

import com.example.springsecurity.mongodb.model.Logs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogsRepository extends MongoRepository<Logs, String> {
}
