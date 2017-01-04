package com.mojix.examples.avro;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;

import com.mojix.examples.commons.wrappers.*;
import org.apache.avro.Schema;
import org.apache.avro.io.*;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * Created by dbascope on 12/22/16
 * Use Reflection to encode and decode the data.
 */
public class AvroReflectThingWrapper {
    private final static ReflectData reflectData = ReflectData.get();
    private final static Schema schema = reflectData.getSchema(ThingWrapper.class);

    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        Encoder e = EncoderFactory.get().binaryEncoder(os, null);
        Encoder e = EncoderFactory.get().jsonEncoder(schema, os);

//        String dataout = "{\"meta\": {\"bridgeCode\": \"ALEB\",\"sqn\": 169637,\"specName\": \"MYSPECNAME\",\"origin\": [],\"units\": \"ft\",\"partition\": 57,\"numPartitions\": 32,\"reblinked\": false,\"outOfOrder\": false,\"newBlink\": false},\"id\": 300004,\"serialNumber\": \"TESTKAFKA004\",\"name\": \"TESTKAFKA004\",\"createdTime\": \"2016-10-24T17:37:40.188-04:00\",\"modifiedTime\": \"2016-10-25T08:53:41.060-04:00\",\"time\": \"2016-10-25T16:11:20.031-04:00\",\"group\": {\"id\": 3,\"name\": \"Santa Monica\",\"code\": \"SM\",\"groupType\": {\"id\": 3,\"name\": \"Store\",\"code\": \"Store\"}},\"thingType\": {\"id\": 1,\"name\": \"Default RFID Thing Type\",\"code\": \"default_rfid_thingtype\"},\"properties\": [{\"eNode\": {\"id\": 5,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-24T17:37:40.161-04:00\",\"value\": \"x3ed9371\",\"dataTypeId\": 1},\"lastDetectTime\": {\"id\": 7,\"blinked\": true,\"modified\": true,\"timeSeries\": false,\"time\": \"2016-10-25T16:11:20.031-04:00\",\"value\": \"2016-10-25T16:11:20.031-04:00\",\"dataTypeId\": 24},\"lastLocateTime\": {\"id\": 6,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": \"2016-10-25T08:53:38.565-04:00\",\"dataTypeId\": 24},\"location\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": true,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": [-118.44380581724973,34.04821254121192,0],\"dataTypeId\": 2},\"locationXYZ\": {\"id\": 2,\"blinked\": false,\"modified\": false,\"timeSeries\": true,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": [61.46,24.48,0],\"dataTypeId\": 3},\"tsCoreIn\": {\"id\": 1,\"blinked\": true,\"modified\": false,\"timeSeries\": true,\"time\": \"2016-10-25T16:11:20.031-04:00\",\"value\": \"2016-10-25T16:11:31.575-04:00\",\"dataTypeId\": 11},\"zone\": {\"id\": 4,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"value\": {\"id\": 8,\"code\": \"Enance\",\"name\": \"Entrance\",\"facilityMap\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Map Store Santa Monica\",\"code\": \"SM\",\"blinked\": true,\"modified\": false},\"zoneType\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Off-Site\",\"code\": \"Off-Site\",\"blinked\": false,\"modified\": false},\"zoneGroup\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Default-Zone-Group\",\"code\": \"Default-Zone-Group\",\"blinked\": false,\"modified\": true}},\"dataTypeId\": 9},\"logicalreader\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"zoneInId\": 1,\"zoneOutId\": 4,\"x\": \"\",\"y\": \"\",\"z\": \"\"},\"dataTypeId\": 23},\"group\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 5,\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"description\": \"Description of the group\",\"hierarchyName\": \">mojix>SM\",\"archived\": true,\"treeLevel\": 3},\"dataTypeId\": 22},\"shift\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": true,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 3,\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"active\": true,\"startTimeOfDay\": 1700,\"endTimeOfDay\": 1700,\"daysOfWeek\": \"23456\"},\"dataTypeId\": 7},\"thingTypeUDF\": {\"id\": 40,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 300003,\"serialNumber\": \"TESTKAFKA003\",\"name\": \"TESTKAFKA003\",\"createdTime\": \"2016-10-24T17:37:40.188-04:00\",\"modifiedTime\": \"2016-10-25T08:53:41.060-04:00\",\"time\": \"2016-10-25T16:11:20.031-04:00\",\"group\": {\"id\": 3,\"name\": \"Santa Monica\",\"code\": \"SM\",\"groupType\": {\"id\": 3,\"name\": \"Store\",\"code\": \"Store\"}},\"thingType\": {\"id\": 1,\"name\": \"Default RFID Thing Type\",\"code\": \"default_rfid_thingtype\"},\"properties\": [{\"eNode\": {\"id\": 5,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-24T17:37:40.161-04:00\",\"value\": \"x3ed9371\",\"dataTypeId\": 1},\"lastDetectTime\": {\"id\": 7,\"blinked\": true,\"modified\": true,\"timeSeries\": false,\"time\": \"2016-10-25T16:11:20.031-04:00\",\"value\": \"2016-10-25T16:11:20.031-04:00\",\"dataTypeId\": 24},\"lastLocateTime\": {\"id\": 6,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": \"2016-10-25T08:53:38.565-04:00\",\"dataTypeId\": 24},\"location\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": true,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": [-118.44380581724973,34.04821254121192,0],\"dataTypeId\": 2},\"locationXYZ\": {\"id\": 2,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": [61.46,24.48,0],\"dataTypeId\": 3},\"tsCoreIn\": {\"id\": 1,\"blinked\": true,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T16:11:20.031-04:00\",\"value\": \"2016-10-25T16:11:31.575-04:00\",\"dataTypeId\": 11},\"zone\": {\"id\": 4,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"value\": {\"id\": 8,\"code\": \"Enance\",\"name\": \"Entrance\",\"facilityMap\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Map Store Santa Monica\",\"code\": \"SM\"},\"zoneType\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Off-Site\",\"code\": \"Off-Site\"},\"zoneGroup\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Default-Zone-Group\",\"code\": \"Default-Zone-Group\"}},\"dataTypeId\": 9},\"logicalreader\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"zoneInId\": 1,\"zoneOutId\": 4,\"x\": \"\",\"y\": \"\",\"z\": \"\"},\"dataTypeId\": 23},\"group\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 5,\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"description\": \"Description of the group\",\"hierarchyName\": \">mojix>SM\",\"archived\": true,\"treeLevel\": 3},\"dataTypeId\": 22},\"shift\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 3,\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"active\": true,\"startTimeOfDay\": 1700,\"endTimeOfDay\": 1700,\"daysOfWeek\": \"23456\"},\"dataTypeId\": 7}}]},\"dataTypeId\": 27}},{\"eNode\": {\"id\": 5,\"time\": \"2016-10-24T17:37:40.161-04:00\",\"value\": \"x3ed9371\",\"dataTypeId\": 1},\"lastDetectTime\": {\"id\": 7,\"time\": \"2016-10-25T16:11:20.031-04:00\",\"value\": \"2016-10-25T16:11:20.031-04:00\",\"dataTypeId\": 24},\"lastLocateTime\": {\"id\": 6,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": \"2016-10-25T08:53:38.565-04:00\",\"dataTypeId\": 24},\"location\": {\"id\": 1,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": [-118.44380581724973,34.04821254121192,0],\"dataTypeId\": 2},\"locationXYZ\": {\"id\": 2,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": [61.46,24.48,0],\"dataTypeId\": 3},\"tsCoreIn\": {\"id\": 1,\"time\": \"2016-10-25T16:11:20.031-04:00\",\"value\": \"2016-10-25T16:11:31.575-04:00\",\"dataTypeId\": 11},\"zone\": {\"id\": 4,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 8,\"code\": \"Enance\",\"name\": \"Entrance\",\"facilityMap\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Map Store Santa Monica\",\"code\": \"SM\"},\"zoneType\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Off-Site\",\"code\": \"Off-Site\"},\"zoneGroup\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Default-Zone-Group\",\"code\": \"Default-Zone-Group\"}},\"dataTypeId\": 9},\"logicalreader\": {\"id\": 1,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"zoneInId\": 1,\"zoneOutId\": 4,\"x\": \"\",\"y\": \"\",\"z\": \"\"},\"dataTypeId\": 23},\"group\": {\"id\": 1,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 5,\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"description\": \"Description of the group\",\"hierarchyName\": \">mojix>SM\",\"archived\": true,\"treeLevel\": 3},\"dataTypeId\": 22},\"shift\": {\"id\": 1,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 3,\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"active\": true,\"startTimeOfDay\": 1700,\"endTimeOfDay\": 1700,\"daysOfWeek\": \"23456\"},\"dataTypeId\": 7},\"thingTypeUDF\": {\"id\": 40,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 300003,\"serialNumber\": \"TESTKAFKA003\",\"name\": \"TESTKAFKA003\",\"createdTime\": \"2016-10-24T17:37:40.188-04:00\",\"modifiedTime\": \"2016-10-25T08:53:41.060-04:00\",\"time\": \"2016-10-25T16:11:20.031-04:00\",\"group\": {\"id\": 3,\"name\": \"Santa Monica\",\"code\": \"SM\",\"groupType\": {\"id\": 3,\"name\": \"Store\",\"code\": \"Store\"}},\"thingType\": {\"id\": 1,\"name\": \"Default RFID Thing Type\",\"code\": \"default_rfid_thingtype\"}},\"dataTypeId\": 27}}]}";
//        ThingWrapper thingWrapper = ThingWrapper.parse(dataout);
        Map<String, ThingPropertyWrapper> current = new HashMap<>();
        current.put("eNode", new ThingPropertyWrapper(
                7L,
                new Date(),
                true,
                true,
                false,
                "1x3e4fy",
                1L)
        );
        current.put("shift", new ThingPropertyWrapper(
                7L,
                new Date(),
                true,
                true,
                false,
                new ShiftWrapper(
                        true,
                        0L,
                        3L,
                        "MTW",
                        0L,
                        "SHIFT",
                        "SHIFTCODE"),
                7L)
        );
        current.put("zone", new ThingPropertyWrapper(
                7L,
                new Date(),
                true,
                true,
                false,
                new ZoneWrapper(
                        new ZonePropertyWrapper(
                                new Date(),
                                true,
                                true,
                                true,
                                "FACILITY",
                                "FCODE"
                        ),
                        new ZonePropertyWrapper(
                                new Date(),
                                true,
                                true,
                                true,
                                "ZONETYPE",
                                "ZTCODE"
                        ),
                        new ZonePropertyWrapper(
                                new Date(),
                                true,
                                true,
                                true,
                                "ZONEGROUP",
                                "ZGCODE"
                        ),
                        0L,
                        "ZONE",
                        "ZONECODE"),
                9L)
        );
        Map<String, ThingPropertyWrapper> previous = current;
        ThingWrapper thingWrapper = new ThingWrapper(
                1L,
                "TESTKAFKA004",
                "TESTKAFKA004",
                new Date(),
                new Date(),
                new Date(),
                new GroupThingWrapper(
                        new ThingPropertyValueWrapper(
                                3L,
                                "GROUP NAME",
                                "GROUPCODE"
                        ),
                        2L,
                        "GROUP",
                        "GR"),
                new ThingPropertyValueWrapper(4L, "NAME", "CODE"),
                new PropertyWrapper(current, previous),
                new MetaWrapper("bridgeCode",
                        5L,
                        "SpecName",
                        new Float[] {},
                        "ft",
                        0L,
                        1L,
                        false,
                        false,
                        true)
                );
        DatumWriter<ThingWrapper> writer = new ReflectDatumWriter<>(schema);

        writer.write(thingWrapper, e);
        e.flush();
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "localhost:9092");
//        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
//        byte[] encodedByteArray = os.toByteArray();
//        ProducerRecord<String, byte[]> record = new ProducerRecord<>("___v1___test", encodedByteArray);
//        KafkaProducer<String, byte[]> producer = new KafkaProducer<>(props);
//        producer.send(record);

        System.out.println(os.toString());

        ReflectDatumReader<ThingWrapper> reader = new ReflectDatumReader<>(schema);
//        Decoder decoder = DecoderFactory.get().binaryDecoder(os.toByteArray(), null);
        Decoder decoder = DecoderFactory.get().jsonDecoder(schema, os.toString());
        ThingWrapper decodedThingWrapper= reader.read(null, decoder);

        System.out.println("Id: "+decodedThingWrapper.getId());
        System.out.println("Name: "+decodedThingWrapper.getName());
        System.out.println("SN: "+decodedThingWrapper.getSerialNumber());
        System.out.println("Properties: ");
        decodedThingWrapper.getProperties().getCurrent().forEach((s, udf) -> System.out.println(s + " - " + udf.getValue()));
    }
}