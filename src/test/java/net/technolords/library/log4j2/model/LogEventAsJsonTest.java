package net.technolords.library.log4j2.model;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LogEventAsJsonTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogEventAsJsonTest.class);
    private static final String DATA_SET_FOR_CONVERSION = "dataSetForConversion";
    private ObjectMapper objectMapper = new ObjectMapper();

    @DataProvider(name = DATA_SET_FOR_CONVERSION)
    public Object[][] dataSetConfigs() {
        return new Object[][]{
                {"src/test/resources/data/json/json-without-exception.json"},
                {"src/test/resources/data/json/json-with-exception.json"}
        };
    }

    @Test(dataProvider = DATA_SET_FOR_CONVERSION)
    public void testConversionFromJson(String pathToFile) throws IOException {
        Path pathToData = FileSystems.getDefault().getPath(pathToFile);
        LOGGER.info("About to check whether file exists: {}", Files.exists(pathToData));
        LogEventAsJson logEventAsJson = this.objectMapper.readValue(Files.newInputStream(pathToData), LogEventAsJson.class);
        Assert.assertNotNull(logEventAsJson);
        LOGGER.info("Got Pojo: {}", logEventAsJson);
    }
}