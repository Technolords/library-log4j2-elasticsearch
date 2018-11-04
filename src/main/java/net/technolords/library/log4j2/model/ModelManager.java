package net.technolords.library.log4j2.model;

import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModelManager {
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(getClass());

    /*
        This class has the following responsobilies:
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

        %d{HH:mm:ss,SSS} ok %Xfdgd %Xfdgd %message -> filter -> %d{HH:mm:ss,SSS} %Xfdgd %message

        {
            "date" : "v1",
            "loglevel" : "INFO",
            "message" : "v3"
        }
     */

    public Map<String, String> convertLayoutPatternToMap(String layoutPattern) {
        String[] logEvents = layoutPattern.split("\\s+");
        HashMap<String, String> hmap = new HashMap<>();
        String eventName = null;


        for (String event : logEvents) {
            String conversionString, key = null;
            Pattern p1 = Pattern.compile("\\{\\w*\\}");
            Matcher m1 = p1.matcher(event);
            if (m1.find()) {
                eventName = m1.group();
            }

            event = event.replaceAll("\\{.*\\}", "");

            Pattern p2 = Pattern.compile("(\\[%.*]|%.*)");
            Matcher m2 = p2.matcher(event);

            if (m2.find()) {
                conversionString = m2.group(1);
                conversionString = conversionString.replaceAll("%n","");
                conversionString = conversionString.replaceAll("\\[", "");
                conversionString = conversionString.replaceAll("\\]", "");
                event = conversionString;

                conversionString = conversionString.replaceAll("%","");
                key = conversionString+eventName;
            }
            hmap.put(key,event);
        }
        return hmap;
    }

}