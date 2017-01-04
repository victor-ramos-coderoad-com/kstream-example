package com.mojix.examples;

import com.mojix.examples.commons.wrappers.ThingWrapper;
import com.mojix.examples.serdes.BlinkSerde;
import com.mojix.examples.serdes.ThingWrapperSerde;
import com.tierconnect.riot.bridges.ThingMessage;
import com.tierconnect.riot.bridges.ThingsMessage;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;

import java.util.Iterator;
import java.util.Properties;

/**
 * Created by dbascope on 1/4/17
 */
public class KThingSerde {
    public static void main(String[] args) throws Exception{
        final Properties streamsConfiguration = new Properties();
        // Application unique name in the Kafka cluster
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "kcorebridge");
        // Kafka broker(s).
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // ZooKeeper ensemble.
        streamsConfiguration.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "localhost:2181");
        // Specify default (de)serializers for record keys and for record values.
        streamsConfiguration.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, ThingWrapperSerde.class);

        streamsConfiguration.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, "4");

        final Serde<String> stringSerde = Serdes.String();
        final Serde<ThingWrapper> twSerde = new ThingWrapperSerde<>();

        // INPUT
        final KStreamBuilder builder = new KStreamBuilder();
        final KStream<String, ThingWrapper> blinks = builder.stream("___v1___dataout");


        final KStream<String, ThingWrapper> dataout = blinks
                .map((key, tw) -> new KeyValue<>(tw.getSerialNumber(), tw));

        // OUTPUT
        dataout.to(stringSerde, twSerde, "___v1___dataoutout");

        // Starts the application
        final KafkaStreams streams = new KafkaStreams(builder, streamsConfiguration);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}

