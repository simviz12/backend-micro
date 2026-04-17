package com.system.payments.application.services;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class PaymentService {
    private final Random random = new Random();

    public Object executeOperation(String operation, Object... params) {
        // Random failure simulation: 10%
        if (random.nextInt(100) < 10) {
            throw new RuntimeException("Payment gateway connection timeout (Simulated Failure)");
        }

        switch (operation.toLowerCase()) {
            case "processpayment":
                simulateDelay(200, 500);
                return "Payment success for amount: " + (params.length > 0 ? params[0] : "0.0");
            case "refund":
                return "Refund processed";
            default:
                throw new IllegalArgumentException("Unknown operation: " + operation);
        }
    }

    private void simulateDelay(int min, int max) {
        try {
            Thread.sleep(random.nextInt(max - min) + min);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
