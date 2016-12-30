package com.mojix.examples.avro;

import com.mojix.examples.commons.wrappers.ThingWrapper;
import com.mojix.examples.commons.wrappers.ZonePropertyWrapper;
import com.mojix.examples.commons.wrappers.ZoneWrapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by dbascope on 12/28/16
 */
public class CurrentEncodingConsumer {
    public static void main(String args[]) {
        final Properties streamsConfiguration = new Properties();
        // Application unique name in the Kafka cluster
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "kcorebridge");
        // Kafka broker(s).
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // ZooKeeper ensemble.
        streamsConfiguration.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "localhost:2181");
        // Specify default (de)serializers for record keys and for record values.
        streamsConfiguration.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        streamsConfiguration.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, "4");

        final Serde<String> stringSerde = Serdes.String();
        final Serde<byte[]> byteSerde = Serdes.ByteArray();

        // INPUT
        final KStreamBuilder builder = new KStreamBuilder();
        final KStream<String, byte[]> input = builder.stream(stringSerde, byteSerde, "___v1___test");
        input.foreach((key, value) -> {
            try {
                ThingWrapper tw = ThingWrapper.parse(new String(value));
                System.out.println("-------------------------------");
                System.out.println("id:" + tw.getId());
                System.out.println("serial:" + tw.getSerialNumber());
                System.out.println("time:" + tw.getTime());
                System.out.println("eNode:" + tw.getProperties().get(0).get("eNode").getValue());
                System.out.println("zone:" +
                        ((ZoneWrapper) tw.getProperties().get(0).get("zone").getValue()).getCode());
                System.out.println("facility:" +
                        ((ZoneWrapper) tw.getProperties().get(0).get("zone").getValue()).facilityMap.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        // Starts the application
        final KafkaStreams streams = new KafkaStreams(builder, streamsConfiguration);
        streams.start();
    }
}
