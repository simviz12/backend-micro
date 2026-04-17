package com.system.inventory.application.services;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class InventoryService {
    public Object executeOperation(String operation, Object... params) {
        // Simulate business logic
        switch (operation.toLowerCase()) {
            case "checkstock":
                return "Stock is available for product " + (params.length > 0 ? params[0] : "unknown");
            case "updateinventory":
                simulateDelay(50, 150);
                return "Inventory updated successfully";
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
