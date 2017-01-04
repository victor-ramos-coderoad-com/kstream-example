package com.mojix.examples;

import com.mojix.examples.serdes.BlinkSerde;
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
 * Created by vramos on 1/4/17.
 */
public class KCustomSerde {
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
        streamsConfiguration.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, BlinkSerde.class);

        streamsConfiguration.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, "4");

        final Serde<String> stringSerde = Serdes.String();
        final Serde<Long> longSerde = Serdes.Long();

        // INPUT
        final KStreamBuilder builder = new KStreamBuilder();
        final KStream<String, ThingsMessage> blinks = builder.stream("___v1___data___ALEBDerby0");


        final KStream<String, String> dataout = blinks
                .flatMap((key, blink) -> {
                    final Iterator<ThingMessage> i1 = blink.entries.iterator();

                    return new Iterable<KeyValue<String, ThingMessage>>()
                    {
                        @Override
                        public Iterator<KeyValue<String, ThingMessage>> iterator() {
                            return new Iterator<KeyValue<String, ThingMessage>>() {
                                @Override
                                public boolean hasNext()
                                {
                                    return i1.hasNext();
                                }

                                @Override
                                public KeyValue<String, ThingMessage> next()
                                {
                                    ThingMessage tm = i1.next();
                                    return new KeyValue<String, ThingMessage>( tm.getSerialNumber(), tm );
                                }
                            };
                        }
                    };

                })
                .map((key, tm) -> new KeyValue<String, String>(tm.getSerialNumber(),tm.getThingTypeCode()));

        // OUTPUT
        dataout.to(stringSerde, stringSerde, "___v1___dataout");

        // Starts the application
        final KafkaStreams streams = new KafkaStreams(builder, streamsConfiguration);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
