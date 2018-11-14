package net.technolords.library.log4j2.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Map;
import java.util.logging.Level;

@JsonInclude(JsonInclude.Include.NON_NULL)
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

    private Level logLevel;
    private long timestamp;
    private int threadId;
    private String logMessage;
    private String formattedMessage;
    private String marker;
    private String className;
    private Map<String, String> threadContextMap;
    private String threadContextStack;
    private String threadContextStackTrace;
    private String exception;

    @JsonGetter("logLevel")
    public Level getLogLevel() {
        return logLevel;
    }

    @JsonSetter("logLevel")
    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    @JsonGetter("timestamp")
    public long getTimestamp() {
        return timestamp;
    }

    @JsonSetter("timestamp")
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @JsonGetter("threadId")
    public int getThreadId() {
        return threadId;
    }

    @JsonSetter("threadId")
    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    @JsonGetter("logMessage")
    public String getLogMessage() {
        return logMessage;
    }

    @JsonSetter("logMessage")
    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    @JsonGetter("formattedMessage")
    public String getFormattedMessage() {
        return formattedMessage;
    }

    @JsonSetter("formattedMessage")
    public void setFormattedMessage(String formattedMessage) {
        this.formattedMessage = formattedMessage;
    }

    @JsonGetter("marker")
    public String getMarker() {
        return marker;
    }

    @JsonSetter("marker")
    public void setMarker(String marker) {
        this.marker = marker;
    }

    @JsonGetter("className")
    public String getClassName() {
        return className;
    }

    @JsonSetter("className")
    public void setClassName(String className) {
        this.className = className;
    }

    @JsonGetter("threadContextMap")
    public Map<String, String> getThreadContextMap() {
        return threadContextMap;
    }

    @JsonSetter("threadContextMap")
    public void setThreadContextMap(Map<String, String> threadContextMap) {
        this.threadContextMap = threadContextMap;
    }

    @JsonGetter("threadContextStack")
    public String getThreadContextStack() {
        return threadContextStack;
    }

    @JsonSetter("threadContextStack")
    public void setThreadContextStack(String threadContextStack) {
        this.threadContextStack = threadContextStack;
    }

    @JsonGetter("threadContextStackTrace")
    public String getThreadContextStackTrace() {
        return threadContextStackTrace;
    }

    @JsonSetter("threadContextStackTrace")
    public void setThreadContextStackTrace(String threadContextStackTrace) {
        this.threadContextStackTrace = threadContextStackTrace;
    }

    @JsonGetter("exception")
    public String getException() {
        return exception;
    }

    @JsonSetter("exception")
    public void setException(String exception) {
        this.exception = exception;
    }
}
