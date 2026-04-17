package com.system.payments.infrastructure.adapters.in.web;

import com.system.common.LoggingProxy;
import com.system.common.MicroserviceProxy;
import com.system.common.LogStorage;
import com.system.payments.application.services.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services/payments")
public class PaymentController {

    private final MicroserviceProxy<Object> proxy;

    public PaymentController(PaymentService service, LogStorage logStorage) {
        this.proxy = new LoggingProxy<>("PaymentService", service::executeOperation, logStorage);
    }

    @PostMapping("/{operation}")
    public Object callOperation(@PathVariable String operation, @RequestBody(required = false) Object[] params) {
        return proxy.execute(operation, params != null ? params : new Object[0]);
    }
}
