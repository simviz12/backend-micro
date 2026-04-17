# Stage 1: Build all modules
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copy poms first for caching
COPY pom.xml .
COPY monitoring-common/pom.xml monitoring-common/
COPY inventory-service/pom.xml inventory-service/
COPY order-service/pom.xml order-service/
COPY payment-service/pom.xml payment-service/
COPY logging-proxy/pom.xml logging-proxy/

# Copy source code
COPY monitoring-common/src monitoring-common/src
COPY inventory-service/src inventory-service/src
COPY order-service/src order-service/src
COPY payment-service/src payment-service/src
COPY logging-proxy/src logging-proxy/src

# Build the project
RUN mvn clean install -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the final jar from logging-proxy module
COPY --from=build /app/logging-proxy/target/logging-proxy-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8888

# Use optimal JVM settings for memory limited environments
ENTRYPOINT ["java", "-Xmx512m", "-Dserver.port=8888", "-jar", "app.jar"]
