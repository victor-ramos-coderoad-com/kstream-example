package com.mojix.examples.serdes;

import com.tierconnect.riot.bridges.ThingsMessageParser;
import com.tierconnect.riot.bridges.ThingsMessage;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Created by vramos on 1/4/17.
 */
public class BlinkDeserializer implements Deserializer {
    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public ThingsMessage deserialize(String topic, byte[] data) {
        String body = new String(data, StandardCharsets.UTF_8);
        ThingsMessage tm = new ThingsMessage();
        ThingsMessageParser.parse(topic,body,tm);
        return tm;
    }

    @Override
    public void close() {

    }
}
