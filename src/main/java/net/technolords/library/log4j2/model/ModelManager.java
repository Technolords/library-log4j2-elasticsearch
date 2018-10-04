package net.technolords.library.log4j2.model;

import java.util.Map;

public class ModelManager {

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
     */

    public Map<String, String> convertLayoutPatternToMap(String layoutPattern) {
        return null;    // TODO
    }
}
