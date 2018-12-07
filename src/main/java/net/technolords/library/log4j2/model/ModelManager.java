package net.technolords.library.log4j2.model;

import org.apache.logging.log4j.core.LogEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelManager {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Convert the generated LogEvent data into a presentable Json format so it can be stored.
     *
     * @param logEvent
     *  The generated LogEvent containing all the data.
     *
     * @return
     *  An instance of a LogEventAsJson containing formatted data.
     */
    public LogEventAsJson convertLogEvent(LogEvent logEvent) {
        LogEventAsJson logEventAsJson = new LogEventAsJson();
        logEventAsJson.setLogLevel(logEvent.getLevel().toString());
        logEventAsJson.setTimestamp(logEvent.getTimeMillis());
        logEventAsJson.setThreadName(logEvent.getThreadName());
        logEventAsJson.setLogMessage(logEvent.getMessage() == null ? "" : logEvent.getMessage().getFormattedMessage());
        logEventAsJson.setMarker(logEvent.getMarker() == null ? "" : logEvent.getMarker().getName());
        logEventAsJson.setClassName(logEvent.getSource().getClassName());
        logEventAsJson.setThreadContextMap(logEvent.getContextData().toMap());
        logEventAsJson.setException(this.getRootCause(logEvent.getThrown()).getMessage());
        return logEventAsJson;
    }

    protected Throwable getRootCause(Throwable cause) {
        LOGGER.debug("Got cause: {} -> with root cause: {}", cause.getMessage(), (cause.getCause() == null ? "null": cause.getCause().getMessage()));
        if (cause.getCause() != null) {
            return this.getRootCause(cause.getCause());
        } else {
            return cause;
        }
    }

}