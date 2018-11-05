package net.technolords.library.log4j2.model;

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
}
