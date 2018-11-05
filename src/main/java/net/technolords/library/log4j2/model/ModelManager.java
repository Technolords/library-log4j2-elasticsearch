package net.technolords.library.log4j2.model;

import org.apache.logging.log4j.core.LogEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelManager {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public LogEventAsJson convertLogEvent(LogEvent logEvent) {
        // Detect actual stack trace -> reduce to root cause
        return null;
    }

}