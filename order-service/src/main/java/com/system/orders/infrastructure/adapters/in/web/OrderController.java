package com.system.orders.infrastructure.adapters.in.web;

import com.system.common.LoggingProxy;
import com.system.common.MicroserviceProxy;
import com.system.common.LogStorage;
import com.system.orders.application.services.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services/orders")
public class OrderController {

    private final MicroserviceProxy<Object> proxy;

    public OrderController(OrderService service, LogStorage logStorage) {
        this.proxy = new LoggingProxy<>("OrderService", service::executeOperation, logStorage);
    }

    @PostMapping("/{operation}")
    public Object callOperation(@PathVariable String operation, @RequestBody(required = false) Object[] params) {
        return proxy.execute(operation, params != null ? params : new Object[0]);
    }
}
