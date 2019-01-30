package net.technolords.library.log4j2;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import net.technolords.library.log4j2.elasticsearch.ElasticsearchClient;
import net.technolords.library.log4j2.model.LogEventAsJson;
import net.technolords.library.log4j2.model.ModelManager;

@Plugin(name = ElasticsearchAppender.ELASTIC_SEARCH, category = Node.CATEGORY, elementType = Appender.ELEMENT_TYPE, printObject = true)
public class ElasticsearchAppender extends AbstractAppender {
    public static final String ELASTIC_SEARCH = "Elasticsearch";
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = readWriteLock.readLock();
    private ModelManager modelManager = new ModelManager();
    private ElasticsearchClient elasticsearchClient;

    // TODO: investigate LifeCycle -> stopping -> close connection to es

    /*
        -Dlog4j.configurationFile=path/to/log4j2.xml

        This class has the following responsibilities:
        - Implement the log4j2 Appender
        - Extract 'current' PatternLayout

        Checkout:
        - https://github.com/rfoltyns/log4j2-elasticsearch
    */

    /*
        <Elasticsearch name="es" index="proto" host="localhost" port="9200">
     */

    protected ElasticsearchAppender(
            String name, Filter filter, Layout<? extends Serializable> layout,
            String index, String host, int port
    ) {
        super(name, filter, layout);
        this.elasticsearchClient = new ElasticsearchClient(host, port);
    }

    // See also:
    // https://github.com/apache/logging-log4j2/blob/master/log4j-csv/src/main/java/org/apache/logging/log4j/csv/layout/CsvLogEventLayout.java
    // https://github.com/savantly-net/log4j2-extended-jsonlayout/blob/master/src/main/java/org/apache/logging/log4j/core/layout/ExtendedJsonLayout.java
    @Override
    public void append(LogEvent logEvent) {
        try {
            readLock.lock();
            // Convert to Json
            LogEventAsJson logEventAsJson = this.modelManager.convertLogEvent(logEvent);
            LOGGER.info("Got Json object: {}", logEventAsJson);
            // Get client
            this.elasticsearchClient.createIndex(logEventAsJson);
        } catch (Exception e) {
            if (!ignoreExceptions()) {
                throw new AppenderLoggingException(e);
            }
        } finally {
            readLock.unlock();
        }
    }

    /**
     * <Elasticsearch name="es" index="proto" host="localhost" port="9200">
     *
     * @param name
     *  The name associated with the Appender, used to identify the Appender within the log4j2.xml context
     * @param layout
     *  The Layout associated with the Appender
     * @param filter
     *  The Filter associated with the Appender
     * @param index
     *  The index associated with Elasticsearch, defaults to 'events'
     * @param host
     *  The host associated with the Elasticsearch server
     * @param port
     *  The posrt associated with the Elasticsearch server
     *
     * @return
     *  An instance of the ElasticsearchAppender
     */
    @PluginFactory
    public static ElasticsearchAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") final Filter filter,
            @PluginAttribute("index") String index,
            @PluginAttribute("host") String host,
            @PluginAttribute("port") int port
    ) {
        System.out.println("Factory called...\n\n");
        System.out.flush();
        LOGGER.info("Factory called with name: {}, index: {}, host: {}, port: {}", name, index, host, port);
        // Validate
        if (name == null || name.isEmpty()) {
            LOGGER.error("No name provided for Elasticsearch appender");
            return null;
        }
        if (host == null || host.isEmpty()) {
            LOGGER.error("No host specified...");
            return null;
        }
        // Defaults
        if (index == null || index.isEmpty()) {
            index = ElasticsearchClient.TEMPLATE_LOG_EVENTS;
            LOGGER.info("Index defaulted to: {}", index);
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
            LOGGER.info("Pattern layout defaulted to: {}", layout.toString());
        }
        // Create
        return new ElasticsearchAppender(name, filter, layout, index, host, port);
    }

}
