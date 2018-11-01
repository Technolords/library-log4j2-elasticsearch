package net.technolords.library.log4j2.model;

import org.apache.logging.log4j.core.LogEvent;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class ModelManager {
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(getClass());

    static Map<String, String> keyMap = new HashMap<>();
    static Map<String, String > logEventMap = new HashMap<>();

    /*
        This class has the following responsibilities:
        - Pass the extracted pattern as input for some method to a Map with key/values
        - Normalize the values so it will not interfere with Json structure itself

        ----------
        %date{DEFAULT} [%level] [%thread] [%class{36}] %message%n
        %d{UNIX_MILLIS} %X{httpUri} %X{httpStatus} %message%n

        ----------
        Challenges:
        - how to identify the key (as name)
        - how to deal with duplicates
        - use the standard substitutions as well (see: https://logging.apache.org/log4j/2.x/manual/layouts.html)
     */

    public Map<String, String> convertLayoutPatternToMap(String layoutPattern) {
        String[] logEvents = layoutPattern.split("\\s+");

        for (Map.Entry<String,String> entry : getKeyMap(logEvents).entrySet()) {
            int i = 0;

            logEventMap.put(entry.getValue(), logEvents[i]);

            i++;
        }

        return logEventMap;
    }
//TODO : this method is to create a static map of logEvents
    private Map<String, LogEvent> getEventMap(LogEvent[] logEvents) {
        for (LogEvent event : logEvents) {
        }


        return null;
    }

// identification of keys from the layout pattern
    private Map<String, String> getKeyMap(String[] logEvents) {
        for (String event : logEvents) {
            if (Pattern.matches("(%date|%d)(\\{.*})?",event)) {
                keyMap.put(replaceBrackets(event),"date");
            } else if (Pattern.matches("(%level)(\\{.*})?",event)) {
                keyMap.put(replaceBrackets(event),"level");
            } else if (Pattern.matches("%t|\\[%t]]",event)) {
                keyMap.put(replaceBrackets(event),"thread");
            } else if (Pattern.matches("\\[%c\\{.*}]|%c",event)) {
                keyMap.put(replaceBrackets(event),"loggerCategoryName");
            } else if (Pattern.matches("\\[%C\\{.*}]|%C",event)) {
                keyMap.put(replaceBrackets(event),"fullyQualifiedClassName");
            }
        //TODO : Need to cover other patterns from logEvent
        }
        return keyMap;
    }

    private String replaceBrackets(String event) {
        event = event.replaceAll("^\\[","");
        event = event.replaceAll("]$","");
        return event;
    }

}