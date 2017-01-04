package com.mojix.examples.serdes;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Created by dbascope on 1/4/17
 */
public class ThingWrapperSerde<ThingWrapper> implements Serde<ThingWrapper> {

    private final Serde<ThingWrapper> inner;

    public ThingWrapperSerde(){
        inner = Serdes.serdeFrom(new ThingWrapperSerializer(), new ThingWrapperDeserializer());
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        inner.serializer().configure(configs, isKey);
        inner.deserializer().configure(configs, isKey);
    }

    @Override
    public void close() {
        inner.serializer().close();
        inner.deserializer().close();
    }

    @Override
    public Serializer<ThingWrapper> serializer() {
        return inner.serializer();
    }

    @Override
    public Deserializer<ThingWrapper> deserializer() {
        return inner.deserializer();
    }
}
