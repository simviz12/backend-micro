package com.system.orders.application.services;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class OrderService {
    public Object executeOperation(String operation, Object... params) {
        switch (operation.toLowerCase()) {
            case "createorder":
                simulateDelay(100, 300);
                return "Order created with ID: " + (int)(Math.random() * 10000);
            case "cancelorder":
                return "Order cancelled";
            default:
                throw new IllegalArgumentException("Unknown operation: " + operation);
        }
    }

    private void simulateDelay(int min, int max) {
        try {
            Thread.sleep(new Random().nextInt(max - min) + min);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
