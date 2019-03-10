package net.technolords.library.log4j2.elasticsearch.factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.apache.logging.log4j.status.StatusLogger;
import org.elasticsearch.action.admin.indices.template.get.GetIndexTemplatesRequest;
import org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateRequest;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * The responsibility of this class is to generate queries for Elasticsearch related to templates.
 */
public class TemplateFactory {
    private static final org.apache.logging.log4j.Logger LOGGER = StatusLogger.getLogger();
    private static final String PATH_TO_LOG_TEMPLATE_FILE = "elasticsearch/log-events-template.json";
    public static final String TEMPLATE_LOG_EVENTS = "log_events";

    public static GetIndexTemplatesRequest generateGetIndexTemplatesRequest() {
        GetIndexTemplatesRequest getIndexTemplatesRequest = new GetIndexTemplatesRequest();
        getIndexTemplatesRequest.names("*");
        return getIndexTemplatesRequest;
    }

    public static PutIndexTemplateRequest generatePutIndexTemplateRequest() throws IOException {
        PutIndexTemplateRequest putIndexTemplateRequest = new PutIndexTemplateRequest();
        putIndexTemplateRequest.name(TEMPLATE_LOG_EVENTS);
        putIndexTemplateRequest.source(readTemplateFromFile().getBytes(), XContentType.JSON);
        return putIndexTemplateRequest;
    }

    private static String readTemplateFromFile() throws IOException {
        InputStream fileStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PATH_TO_LOG_TEMPLATE_FILE);
        LOGGER.info("Path to file exists: {}", fileStream.available());
        return new BufferedReader(new InputStreamReader(fileStream)).lines().collect(Collectors.joining("\n"));
    }

}
