package net.technolords.library.log4j2.model;

import com.fasterxml.jackson.annotation.*;

import java.util.Map;
import java.util.logging.Level;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"logLevel", "timeStamp", "threadId", "logMessage", "marker", "className", "threadContextMap", "threadContextStackTrace", "exception"})
public class LogEventAsJson {
    // 'basics'
    // context-data aka mdc (key, value pairs)
    // stack trace element
    // actual stack trace -> find root cause

    /*
    2018-11-05 20:26:32,801 main INFO Level: INFO
    2018-11-05 20:26:32,801 main INFO Time (ms): 1541445992800
    2018-11-05 20:26:32,801 main INFO Thread id: 1 -> name: main
    2018-11-05 20:26:32,801 main INFO Message: About to sample the logging, with param: test -> parameters: 1
    2018-11-05 20:26:32,801 main INFO Formatted message: About to sample the logging, with param: test
    2018-11-05 20:26:32,801 main INFO Marker: ADMIN
    2018-11-05 20:26:32,802 main INFO Class: org.apache.logging.slf4j.Log4jLogger
    2018-11-05 20:26:32,802 main INFO ContextData: {my_key=my_value} -> size: 1
    2018-11-05 20:26:32,802 main INFO ContextStack: [] -> size: 0
    2018-11-05 20:26:32,802 main INFO StackTraceElement: net.technolords.proto.log4j2.Sample.initAndRun(Sample.java:15)
    2018-11-05 20:26:32,802 main INFO Thowable: {}
     */

    public Level logLevel;
    public long timestamp;
    public int threadId;
    public String logMessage;
    public String marker;
    public String className;
    public Map<String, String> threadContextMap;
    public String threadContextStackTrace;
    public String exception;

    public LogEventAsJson(Level logLevel, long timestamp, String logMessage, String Marker, String className, String threadContextStackTrace, String exception) {
    }

    @JsonProperty
    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getThreadContextStackTrace() {
        return threadContextStackTrace;
    }

    public void setThreadContextStackTrace(String threadContextStackTrace) {
        this.threadContextStackTrace = threadContextStackTrace;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    @JsonAnyGetter
    public Map<String, String> getThreadContextMap() {
        return threadContextMap;
    }

    @JsonAnySetter
    public void setThreadContextMap(Map<String, String> threadContextMap) {
        this.threadContextMap = threadContextMap;
    }
}
