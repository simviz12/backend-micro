package com.system.monitoring.web;

import com.system.common.LogEntry;
import com.system.common.LogStorage;
import com.system.monitoring.service.MetricsService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/metrics")
public class MetricsController {

    private final MetricsService metricsService;
    private final LogStorage logStorage;

    public MetricsController(MetricsService metricsService, LogStorage logStorage) {
        this.metricsService = metricsService;
        this.logStorage = logStorage;
    }

    @GetMapping("/summary")
    public Map<String, Object> getSummary() {
        return metricsService.getSummary();
    }

    @GetMapping("/logs")
    public List<LogEntry> getLogs(
            @RequestParam(name = "service", required = false) String service,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "from", required = false) Long from,
            @RequestParam(name = "to", required = false) Long to) {
        return metricsService.getFilteredLogs(service, status, from, to);
    }

    @PostMapping("/simulate-load")
    public Map<String, String> simulateLoad() {
        Random random = new Random();
        String[] services = {"InventoryService", "OrderService", "PaymentService"};
        String[] operations = {"checkStock", "createOrder", "processPayment", "updateInventory", "refund"};

        for (int i = 0; i < 50; i++) {
            String serviceId = services[random.nextInt(services.length)];
            String operation = operations[random.nextInt(operations.length)];
            
            // Randomly determine status (Payment fails more often according to requirement)
            String status = "SUCCESS";
            String error = null;
            
            if (serviceId.equals("PaymentService") && random.nextInt(100) < 10) {
                status = "ERROR";
                error = "Payment gateway connection timeout (Simulated)";
            } else if (random.nextInt(100) < 2) { // 2% random error for others
                status = "ERROR";
                error = "Unexpected system error (Simulated)";
            }

            LogEntry log = LogEntry.builder()
                    .serviceId(serviceId)
                    .operation(operation)
                    .requestId(UUID.randomUUID().toString())
                    .durationMs((long) (random.nextInt(500) + 50))
                    .status(status)
                    .timestamp(LocalDateTime.now().minusMinutes(random.nextInt(60)))
                    .errorMessage(error)
                    .build();
            
            logStorage.save(log);
        }

        return Collections.singletonMap("message", "Simulated 50 calls successfully");
    }
}
