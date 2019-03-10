package net.technolords.library.log4j2.elasticsearch.factory;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.technolords.library.log4j2.model.LogEventAsJson;

public class IndexFactory {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
    private static final String ES_INDEX = "log";
    private static final String ES_TYPE = "";
    private static ObjectMapper objectMapper = new ObjectMapper();

    // Reference:
    // vlog_2018_x/connections
    // Current
    // log_<date>/proto

    public static IndexRequest generateRequest(LogEventAsJson logEventAsJson) throws JsonProcessingException {
        IndexRequest indexRequest = new IndexRequest(generateDynamicIndex(), logEventAsJson.getEsType());
        indexRequest.opType(DocWriteRequest.OpType.INDEX);
        indexRequest.source(objectMapper.writeValueAsString(logEventAsJson), XContentType.JSON);
        return indexRequest;
    }

    private static String generateDynamicIndex() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(ES_INDEX).append("_").append(generateIndexSuffixByCurrentDate());
        return buffer.toString();
    }

    protected static String generateIndexSuffixByCurrentDate() {
        return generateIndexSuffixByDate(null);
    }

    protected static String generateIndexSuffixByDate(Date date) {
        if (date == null) {
            date = new Date();
        }
        return simpleDateFormat.format(date);
    }

}
