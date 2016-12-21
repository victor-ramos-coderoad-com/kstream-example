package com.mojix.examples;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.ValueMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Created by vramos on 12/20/16.
 */
public class KCoreBridge {
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
        streamsConfiguration.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        streamsConfiguration.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, "4");

        final Serde<String> stringSerde = Serdes.String();
        final Serde<Long> longSerde = Serdes.Long();
        final Pattern pattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);

        // INPUT
        final KStreamBuilder builder = new KStreamBuilder();
        final KStream<String, String> blinks = builder.stream(stringSerde, stringSerde, "___v1___data___ALEB");

        // TRANSFORMATIONS
        final KStream<String, String> dataout = blinks
                .flatMapValues(new ValueMapper<String, Iterable<String>>() {
                    @Override
                    public Iterable<String> apply(String value) {
                        System.out.println("1. Creating thing message "+Thread.currentThread().getId());
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return Arrays.asList(pattern.split(value.toLowerCase()));
                    }
                })
                .map(new KeyValueMapper<String, String, KeyValue<String, String>>() {
                    @Override
                    public KeyValue<String, String> apply(String key, String value) {
                        System.out.println("2. Instantiating thing "+Thread.currentThread().getId());
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return new KeyValue<>(key, value);
                    }
                })
                .map(new KeyValueMapper<String, String, KeyValue<String, String>>() {
                    @Override
                    public KeyValue<String, String> apply(String key, String value) {
                        System.out.println("3. Processing thing message "+Thread.currentThread().getId());
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return new KeyValue<>(key, value);
                    }
                })
                .map(new KeyValueMapper<String, String, KeyValue<String, String>>() {
                    @Override
                    public KeyValue<String, String> apply(String key, String value) {
                        System.out.println("4. Creating json data out "+Thread.currentThread().getId());
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return new KeyValue<>(key, value);
                    }
                });

//        final KStream<String, Long> dataout = blinks
//                .flatMapValues(value -> Arrays.asList(pattern.split(value.toLowerCase())))
//                .map((key, word) -> new KeyValue<>(word, word))
//                .through("RekeyedIntermediateTopic")
//                .countByKey("Counts")
//                .toStream();

        // OUTPUT
        dataout.to(stringSerde, stringSerde, "___v1___dataout");

        // Starts the application
        final KafkaStreams streams = new KafkaStreams(builder, streamsConfiguration);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
