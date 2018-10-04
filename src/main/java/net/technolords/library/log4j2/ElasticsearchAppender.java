package net.technolords.library.log4j2;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

@Plugin(name = "Elasticsearch", category = "core", elementType = "appender", printObject = true)
public class ElasticsearchAppender extends AbstractAppender {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = readWriteLock.readLock();

    /*
        -Dlog4j.configurationFile=path/to/log4j2.xml

        This class has the following responsibilities:
        - Implement the log4j2 Appender
        - Extract 'current' PatternLayout
    */

    protected ElasticsearchAppender(
            String name, Filter filter, Layout<? extends Serializable> layout,
            String index, String host, int port
    ) {
        super(name, filter, layout);
    }

    @Override
    public void append(LogEvent logEvent) {
        try {
            readLock.lock();
            final byte[] bytes = getLayout().toByteArray(logEvent);
            // Get map
            // Convert to Json
            // Get client
            // Write Json
            LOGGER.info("... done");
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
     * @param layout
     * @param filter
     * @param index
     * @param host
     * @param port
     *
     * @return
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
            index = "TODO"; // TODO: fetch constant from client
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
