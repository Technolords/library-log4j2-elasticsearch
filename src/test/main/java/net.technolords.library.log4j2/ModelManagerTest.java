package net.technolords.library.log4j2;

import net.technolords.library.log4j2.model.ModelManager;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ModelManagerTest {

    @Test
    public void testConversionPatternLayput() {

        ModelManager modelManager = new ModelManager();
        Map<String, String> hmap =  modelManager.convertLayoutPatternToMap("%date{DEFAULT} [%level] [%thread] [%class{36}] [%thread] %message%n");

        Set keys = hmap.keySet();
        Iterator iterator = keys.iterator();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String value = hmap.get(key);

            System.out.println("LogEvent Key : " + key);
            System.out.println("LogEvent Value : " + value);
        }


    }
}
