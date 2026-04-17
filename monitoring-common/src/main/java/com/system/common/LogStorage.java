package com.system.common;

import java.util.List;

public interface LogStorage {
    void save(LogEntry log);
    List<LogEntry> getAll();
}
