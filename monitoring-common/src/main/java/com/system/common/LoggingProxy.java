package com.system.common;

import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.BiFunction;

@Slf4j
public class LoggingProxy<T> implements MicroserviceProxy<T> {

    private final String serviceId;
    private final BiFunction<String, Object[], T> serviceOperation;
    private final LogStorage logStorage;

    public LoggingProxy(String serviceId, BiFunction<String, Object[], T> serviceOperation, LogStorage logStorage) {
        this.serviceId = serviceId;
        this.serviceOperation = serviceOperation;
        this.logStorage = logStorage;
    }

    @Override
    public T execute(String operation, Object... params) {
        String requestId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();
        LocalDateTime timestamp = LocalDateTime.now();
        
        System.out.println("[LoggingProxy] Starting operation " + operation + " for service " + serviceId + " (RequestID: " + requestId + ")");

        try {
            T result = serviceOperation.apply(operation, params);
            long duration = System.currentTimeMillis() - startTime;
            
            saveLog(serviceId, operation, duration, "SUCCESS", requestId, timestamp, null, java.util.Arrays.toString(params), String.valueOf(result));
            return result;
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            String summarizedStackTrace = e.getMessage() != null ? e.getMessage() : e.getClass().getName();
            
            saveLog(serviceId, operation, duration, "ERROR", requestId, timestamp, summarizedStackTrace, java.util.Arrays.toString(params), null);
            throw e;
        }
    }

    private void saveLog(String serviceId, String operation, long duration, String status, String requestId, LocalDateTime timestamp, String error, String params, String result) {
        LogEntry logEntry = LogEntry.builder()
                .serviceId(serviceId)
                .operation(operation)
                .durationMs(duration)
                .status(status)
                .requestId(requestId)
                .timestamp(timestamp)
                .errorMessage(error)
                .parameters(params)
                .result(result)
                .build();
        
        logStorage.save(logEntry);
    }
}
