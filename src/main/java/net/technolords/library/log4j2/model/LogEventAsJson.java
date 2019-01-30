package net.technolords.library.log4j2.model;

import com.fasterxml.jackson.annotation.*;

import java.util.Map;
import java.util.logging.Level;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "logLevel",
        "timeStamp",
        "threadId",
        "logMessage",
        "marker",
        "environment",
        "className",
        "threadContextMap",
        "threadContextStackTrace",
        "exception"
})
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

    2018-11-22 20:42:23,197 main INFO Level: ERROR
    2018-11-22 20:42:23,198 main INFO Time (ms): 1542915743196
    2018-11-22 20:42:23,198 main INFO Thread id: 1 -> name: main
    2018-11-22 20:42:23,198 main INFO Message: Some error occurred: hah -> parameters: 2
    2018-11-22 20:42:23,198 main INFO Formatted message: Some error occurred: hah
    2018-11-22 20:42:23,198 main INFO Marker: ADMIN
    2018-11-22 20:42:23,198 main INFO Class: org.apache.logging.slf4j.Log4jLogger
    2018-11-22 20:42:23,199 main INFO ContextData: {my_key=my_value} -> size: 1
    2018-11-22 20:42:23,199 main INFO ContextStack: [] -> size: 0
    2018-11-22 20:42:23,199 main INFO StackTraceElement: net.technolords.proto.log4j2.Sample.initAndRun(Sample.java:17)
    2018-11-22 20:42:23,199 main INFO Thowable: {} java.lang.IllegalArgumentException: hah
        at net.technolords.proto.log4j2.Sample.initAndRun(Sample.java:16)
        at net.technolords.proto.log4j2.Sample.main(Sample.java:24)

     */

    private String logLevel;
    private long timestamp;
    private String threadName;
    private String logMessage;
    private String marker;
    private String environment;
    private String className;
    private Map<String, String> threadContextMap;
    private String exception;

    @JsonProperty(value = "logLevel")
    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    @JsonProperty(value = "timestamp")
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty(value = "threadName")
    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    @JsonProperty(value = "logMessage")
    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    @JsonProperty(value = "marker")
    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    @JsonProperty(value = "environment")
    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    @JsonProperty(value = "stackTraceElement")
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @JsonProperty
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

    public void setThreadContextMap(Map<String, String> threadContextMap) {
        this.threadContextMap = threadContextMap;
    }
}
