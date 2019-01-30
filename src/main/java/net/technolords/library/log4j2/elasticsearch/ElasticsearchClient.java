package net.technolords.library.log4j2.elasticsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.logging.log4j.status.StatusLogger;
import org.elasticsearch.action.admin.indices.template.get.GetIndexTemplatesRequest;
import org.elasticsearch.action.admin.indices.template.get.GetIndexTemplatesResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.IndexTemplateMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import net.technolords.library.log4j2.model.LogEventAsJson;
import net.technolords.library.log4j2.util.RootCauseHelper;

public class ElasticsearchClient {
    private static final org.apache.logging.log4j.Logger LOGGER = StatusLogger.getLogger();
    private static final String PATH_TO_LOG_TEMPLATE_FILE = "elasticsearch/log-events-template.json";
    private static final String PROP_ES_IGNORE_CLUSTER_NAME = "client.transport.ignore_cluster_name";
    private static final String PROP_ES_TRANSPORT_SNIFF = "client.transport.sniff";
    public static final String TEMPLATE_LOG_EVENTS = "log_events";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
    private TransportClient transportClient;
    private String host;
    private int port;

    public ElasticsearchClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.initTransportClient();
        this.provisionTemplateWhenNeeded();
    }

    public void createIndex(LogEventAsJson logEventAsJson) {
        LOGGER.info("About to write log event to Elasticsearch");
    }

    // TODO: call this from lifecycle management
    public void closeConnection() {
        if (this.transportClient != null) {
            this.transportClient.close();
        }
    }

    private void provisionTemplateWhenNeeded() {
        try {
            LOGGER.info("About to provision template, checking for presence...");
            GetIndexTemplatesRequest getIndexTemplatesRequest = new GetIndexTemplatesRequest();
            getIndexTemplatesRequest.names("*");
            GetIndexTemplatesResponse getIndexTemplatesResponse = this.transportClient
                    .admin()
                    .indices()
                    .getTemplates(getIndexTemplatesRequest)
                    .get();
            LOGGER.info("Found total templates: {}", getIndexTemplatesResponse.getIndexTemplates().size());
            if (this.isTemplateForLogEventsPresent(getIndexTemplatesResponse)) {
                LOGGER.info("... good, template for `{}` is already present...", TEMPLATE_LOG_EVENTS);
            } else {
                LOGGER.info("... bad, expected template does not exist, creating one...");
                String content = this.readTemplateFromFile();
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            LOGGER.error("Unable to fetch templates...", RootCauseHelper.getRootCause(e));
        }
    }

    private boolean isTemplateForLogEventsPresent(GetIndexTemplatesResponse response) {
        for (IndexTemplateMetaData metaData : response.getIndexTemplates()) {
            LOGGER.info("Checking template: {}", metaData.getName());
            if (TEMPLATE_LOG_EVENTS.equals(metaData.getName())) {
                return true;
            }
        }
        return false;
    }

    private String readTemplateFromFile() throws IOException {
        InputStream fileStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PATH_TO_LOG_TEMPLATE_FILE);
        LOGGER.info("Path to file exists: {}", fileStream.available());
        return new BufferedReader(new InputStreamReader(fileStream)).lines().collect(Collectors.joining("\n"));
    }

    private void initTransportClient() {
        LOGGER.info("About to configure the Elasticsearch client using host: {} -> port: {}", this.host, this.port);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(this.host, this.port);
        Settings settings = Settings.builder()
                .put(PROP_ES_IGNORE_CLUSTER_NAME, true)
                .put(PROP_ES_TRANSPORT_SNIFF, true)
                .build();
        this.transportClient = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(inetSocketAddress));
    }
}
