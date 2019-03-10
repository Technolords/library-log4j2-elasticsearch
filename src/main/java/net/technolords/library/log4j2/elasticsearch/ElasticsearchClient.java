package net.technolords.library.log4j2.elasticsearch;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.status.StatusLogger;
import org.elasticsearch.action.admin.indices.template.get.GetIndexTemplatesResponse;
import org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.IndexTemplateMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import net.technolords.library.log4j2.elasticsearch.factory.TemplateFactory;
import net.technolords.library.log4j2.model.LogEventAsJson;
import net.technolords.library.log4j2.util.RootCauseHelper;

public class ElasticsearchClient {
    private static final org.apache.logging.log4j.Logger LOGGER = StatusLogger.getLogger();
    private static final String PROP_ES_IGNORE_CLUSTER_NAME = "client.transport.ignore_cluster_name";
    private static final String PROP_ES_TRANSPORT_SNIFF = "client.transport.sniff";
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
//        try {
//            LOGGER.debug("About to create index...");
//            IndexResponse indexResponse = this.transportClient.index(this.generateRequest(nettyConnectionContext)).get();
//            LOGGER.debug("Got response: {}", indexResponse.toString());
//        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
//            LOGGER.error("Failed to create index", e);
//        }
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
            GetIndexTemplatesResponse getIndexTemplatesResponse = this.transportClient
                    .admin()
                    .indices()
                    .getTemplates(TemplateFactory.generateGetIndexTemplatesRequest())
                    .get();
            LOGGER.info("Found total templates: {}", getIndexTemplatesResponse.getIndexTemplates().size());
            if (this.isTemplateForLogEventsPresent(getIndexTemplatesResponse)) {
                LOGGER.info("... good, template for `{}` is already present...", TemplateFactory.TEMPLATE_LOG_EVENTS);
            } else {
                LOGGER.info("... bad, expected template does not exist, creating one...");
                PutIndexTemplateResponse putIndexTemplateResponse = this.transportClient
                        .admin()
                        .indices()
                        .putTemplate(TemplateFactory.generatePutIndexTemplateRequest())
                        .get();
                LOGGER.info("... done: {}", putIndexTemplateResponse.isAcknowledged());
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            LOGGER.error("Unable to fetch templates...", RootCauseHelper.getRootCause(e));
        }
    }

    private boolean isTemplateForLogEventsPresent(GetIndexTemplatesResponse response) {
        for (IndexTemplateMetaData metaData : response.getIndexTemplates()) {
            LOGGER.info("Checking template: {}", metaData.getName());
            if (TemplateFactory.TEMPLATE_LOG_EVENTS.equals(metaData.getName())) {
                return true;
            }
        }
        return false;
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
