package com.mojix.examples.serdes;

import com.mojix.examples.commons.wrappers.ThingWrapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Created by dbascope on 1/4/17
 */
class ThingWrapperDeserializer implements Deserializer {
    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public ThingWrapper deserialize(String topic, byte[] data) {
        String body = new String(data, StandardCharsets.UTF_8);
        try {
            return ThingWrapper.parse(body);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() {

    }
}
