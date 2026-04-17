# Sentinel Monitoring Backend

This is a multi-module Spring Boot application following Clean Architecture.

## Repository Structure
- `monitoring-common`: Shared entities, interfaces, and the Generic Proxy.
- `inventory-service`, `order-service`, `payment-service`: Business microservices.
- `logging-proxy`: The monitoring hub and data aggregator.

## How to Run
Since this is a multi-module project with dependencies between modules, the best way to run it is:

1. **Via IDE (Recommended)**:
   - Open the source folder (`backend-micro`) in **IntelliJ IDEA**, **Eclipse**, or **VS Code**.
   - Let the IDE import the Maven dependencies.
   - Run the class `com.system.monitoring.MonitoringApplication` found in the `logging-proxy` module.

2. **Via Command Line**:
   - Make sure you have Maven installed (`mvn -version`).
   - Run: `mvn clean install` from the root folder.
   - Run: `java -jar logging-proxy/target/logging-proxy-0.0.1-SNAPSHOT.jar`

## Ports
- Centralized API & Monitoring: `http://localhost:8080` (The frontend is configured to use this).
- Individual services: 8081, 8082, 8083.
