package com.mojix.examples.connectors;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dbascope on 1/5/17
 */
public class FileStreamSourceConnector extends SourceConnector {
    public static final String FILE_CONFIG = "";
    public static final String TOPIC_CONFIG = "";
    private String filename;
    private String topic;

    @Override
    public String version() {
        return null;
    }

    @Override
    public void start(Map<String, String> props) {
        // The complete version includes error handling as well.
        filename = props.get(FILE_CONFIG);
        topic = props.get(TOPIC_CONFIG);
    }

    @Override
    public Class<? extends Task> taskClass() {
        return FileStreamSourceTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        ArrayList<Map<String, String>> configs = new ArrayList<>();
        // Only one input partition makes sense.
        Map<String, String> config = new HashMap<>();
        if (filename != null)
            config.put(FILE_CONFIG, filename);
        config.put(TOPIC_CONFIG, topic);
        configs.add(config);
        return configs;
    }

    @Override
    public void stop() {
        // Nothing to do since no background monitoring is required
    }

    @Override
    public ConfigDef config() {
        return null;
    }
}
