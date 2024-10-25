package org.user.userservice.repository;

import org.user.userservice.model.LogRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<LogRequest, String> {
}
