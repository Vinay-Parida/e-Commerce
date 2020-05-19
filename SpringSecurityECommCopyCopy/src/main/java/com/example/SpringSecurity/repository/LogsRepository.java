package com.example.SpringSecurity.repository;

import com.example.SpringSecurity.mongodb.model.Logs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogsRepository extends MongoRepository<Logs, String> {
}
