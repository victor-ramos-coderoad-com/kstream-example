package com.mojix.examples.connectors;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by dbascope on 1/5/17
 */
public class FileStreamSourceTask extends SourceTask {
    private String filename;
    private InputStream stream;
    private String topic;

    @Override
    public String version() {
        return null;
    }

    public void start(Map<String, String> props) {
        filename = props.get(FileStreamSourceConnector.FILE_CONFIG);
        stream = openOrThrowError(filename);
        topic = props.get(FileStreamSourceConnector.TOPIC_CONFIG);
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
//        try {
//            ArrayList<SourceRecord> records = new ArrayList<>();
//            while (streamValid(stream) && records.isEmpty()) {
//                LineAndOffset line = readToNextLine(stream);
//                if (line != null) {
//                    Map sourcePartition = Collections.singletonMap("filename", filename);
//                    Map sourceOffset = Collections.singletonMap("position", streamOffset);
//                    records.add(new SourceRecord(sourcePartition, sourceOffset, topic, Schema.STRING_SCHEMA, line));
//                } else {
//                    Thread.sleep(1);
//                }
//            }
//            return records;
//        } catch (IOException e) {
//            // Underlying stream was killed, probably as a result of calling stop. Allow to return
//            // null, and driving thread will handle any shutdown if necessary.
//        }
        return null;
    }

    @Override
    public synchronized void stop() {
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private InputStream openOrThrowError(String filename) {
        return null;
    }

    private boolean streamValid(InputStream stream){
        return false;
    }
}