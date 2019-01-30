package net.technolords.library.log4j2.model;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.logging.log4j.core.LogEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.technolords.library.log4j2.util.RootCauseHelper;

public class ModelManager {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private String knownEnvironment;

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
        logEventAsJson.setEnvironment(this.getEnvironment());
        logEventAsJson.setMarker(logEvent.getMarker() == null ? "" : logEvent.getMarker().getName());
        logEventAsJson.setClassName(logEvent.getSource().getClassName());
        logEventAsJson.setThreadContextMap(logEvent.getContextData().toMap());
        logEventAsJson.setException(RootCauseHelper.getRootCause(logEvent.getThrown()).getMessage());
        return logEventAsJson;
    }

    protected String getEnvironment() {
        if (this.knownEnvironment != null) {
            return this.knownEnvironment;
        }
        // Derive from environment variable
        String environmentAsVariable = System.getenv("environment");
        if (environmentAsVariable != null && !environmentAsVariable.isEmpty()) {
            this.knownEnvironment = environmentAsVariable;
            return this.knownEnvironment;
        }
        try {
            // Derive from calculation (i.e. from local machine)
            this.knownEnvironment = InetAddress.getLocalHost().getCanonicalHostName();
            return this.knownEnvironment;
        } catch (UnknownHostException e) {
            this.knownEnvironment = "localhost";
            return this.knownEnvironment;
        }
    }



}