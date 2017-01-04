package com.mojix.examples.serdes;

import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Created by vramos on 1/4/17.
 */
public class BlinkSerializer implements Serializer {
    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, Object data) {
        return new byte[0];
    }

    @Override
    public void close() {

    }
}
