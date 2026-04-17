package com.system.common;

/**
 * Generic interface for Microservice Proxies.
 * @param <T> The return type of the operation.
 */
public interface MicroserviceProxy<T> {
    T execute(String operation, Object... params);
}
