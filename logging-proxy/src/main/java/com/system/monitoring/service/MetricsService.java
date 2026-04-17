package com.system.monitoring.service;

import com.system.common.LogEntry;
import com.system.common.LogStorage;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MetricsService {
    private final LogStorage logStorage;

    public MetricsService(LogStorage logStorage) {
        this.logStorage = logStorage;
    }

    public Map<String, Object> getSummary() {
        List<LogEntry> logs = logStorage.getAll();
        Map<String, Object> summary = new HashMap<>();
        
        long totalCalls = logs.size();
        summary.put("totalCalls", totalCalls);
        summary.put("errorRate", 0.0);
        summary.put("services", new HashMap<>());
        
        if (totalCalls == 0) return summary;

        long totalErrors = logs.stream().filter(l -> "ERROR".equals(l.getStatus())).count();
        summary.put("errorRate", (double) totalErrors / totalCalls);

        Map<String, Map<String, Object>> serviceStats = new HashMap<>();
        
        logs.stream().collect(Collectors.groupingBy(LogEntry::getServiceId)).forEach((serviceId, serviceLogs) -> {
            Map<String, Object> stats = new HashMap<>();
            long count = serviceLogs.size();
            long errors = serviceLogs.stream().filter(l -> "ERROR".equals(l.getStatus())).count();
            double avgDuration = serviceLogs.stream().mapToLong(LogEntry::getDurationMs).average().orElse(0.0);
            
            stats.put("totalCalls", count);
            stats.put("errorRate", (double) errors / count);
            stats.put("avgResponseTimeMs", avgDuration);
            
            serviceStats.put(serviceId, stats);
        });

        summary.put("services", serviceStats);
        return summary;
    }

    public List<LogEntry> getFilteredLogs(String service, String status, Long from, Long to) {
        return logStorage.getAll().stream()
                .filter(l -> service == null || (l.getServiceId() != null && l.getServiceId().equalsIgnoreCase(service)))
                .filter(l -> status == null || (l.getStatus() != null && l.getStatus().equalsIgnoreCase(status)))
                .collect(Collectors.toList());
    }
}
