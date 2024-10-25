package org.user.userservice.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "userLog")
public class LogRequest {

    private String id;

    private String methodName;
    private String serviceName;
    private LocalDateTime timeStamp;
    private String data;
    private String message;

}
