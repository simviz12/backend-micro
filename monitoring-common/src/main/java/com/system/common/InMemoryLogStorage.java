package com.system.common;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

@Component
public class InMemoryLogStorage implements LogStorage {
    private final List<LogEntry> logs = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void save(LogEntry log) {
        logs.add(log);
    }

    @Override
    public List<LogEntry> getAll() {
        return new ArrayList<>(logs);
    }
}
