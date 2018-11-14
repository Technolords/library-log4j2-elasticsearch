//package net.technolords.library.log4j2;
//
//import net.technolords.library.log4j2.model.ModelManager;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//
//public class ModelManagerTest {
//    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
//    private static final String LAYOUT_PATTERNS = "layoutPatterns";
//
//    @DataProvider(name = LAYOUT_PATTERNS) //
//    public Object[][] dataSetForLayoutPatterns() {
//        return new Object[][] {
//                {"%d{UNIX_MILLIS} %X{httpUri} %X{httpStatus} %message%n"},
//                {"%date{DEFAULT} [%level] [%thread] [%class{36}] [%thread] %message%n"}
//        };
//    }
//
//    @Test(dataProvider = LAYOUT_PATTERNS)
//    public void testConversionPatternLayput(final String pattern) {
//        ModelManager modelManager = new ModelManager();
////        Map<String, String> hmap =  modelManager(pattern);
//
//        Set keys = hmap.keySet();
//        Iterator iterator = keys.iterator();
//
//        while (iterator.hasNext()) {
//            String key = (String) iterator.next();
//            String value = hmap.get(key);
//
//           LOGGER.info("Extracted Key and Value: {} -> {}", key , value);
//        }
//
//
//    }
//}
