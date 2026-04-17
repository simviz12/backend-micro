package com.system.common;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class LogEntry {
    private String serviceId;
    private String operation;
    private Long durationMs;
    private String status; // SUCCESS / ERROR
    private String requestId;
    private LocalDateTime timestamp;
    private String errorMessage;
    private String parameters; // JSON string
    private String result;     // JSON string
}
