package com.system.inventory.infrastructure.adapters.in.web;

import com.system.common.LoggingProxy;
import com.system.common.MicroserviceProxy;
import com.system.common.LogStorage;
import com.system.inventory.application.services.InventoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services/inventory")
public class InventoryController {

    private final MicroserviceProxy<Object> proxy;

    public InventoryController(InventoryService service, LogStorage logStorage) {
        // Wrapping the service with LoggingProxy
        this.proxy = new LoggingProxy<>("InventoryService", service::executeOperation, logStorage);
    }

    @PostMapping("/{operation}")
    public Object callOperation(@PathVariable String operation, @RequestBody(required = false) Object[] params) {
        return proxy.execute(operation, params != null ? params : new Object[0]);
    }
}
