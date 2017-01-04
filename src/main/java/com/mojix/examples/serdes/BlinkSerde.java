package com.mojix.examples.serdes;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Created by vramos on 1/4/17.
 */
public class BlinkSerde<ThingsMessage> implements Serde<ThingsMessage> {

    private final Serde<ThingsMessage> inner;

    public BlinkSerde(){
        inner = Serdes.serdeFrom(new BlinkSerializer(), new BlinkDeserializer());
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
    public Serializer<ThingsMessage> serializer() {
        return inner.serializer();
    }

    @Override
    public Deserializer<ThingsMessage> deserializer() {
        return inner.deserializer();
    }
}
