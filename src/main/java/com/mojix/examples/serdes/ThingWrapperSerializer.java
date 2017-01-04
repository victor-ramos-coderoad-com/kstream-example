package com.mojix.examples.serdes;

import com.mojix.examples.commons.wrappers.ThingWrapper;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

/**
 * Created by dbascope on 1/4/17
 */
public class ThingWrapperSerializer<ThingWrapper> implements Serializer<ThingWrapper> {
    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, ThingWrapper data) {
        try {
            return com.mojix.examples.commons.wrappers.ThingWrapper.toJsonString((com.mojix.examples.commons.wrappers
                    .ThingWrapper) data).getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() {

    }
}
