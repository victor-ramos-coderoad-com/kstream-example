package com.mojix.examples.avro;

import com.mojix.examples.commons.wrappers.ThingWrapper;
import org.apache.avro.Schema;
import org.apache.avro.io.*;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dbascope on 12/29/16
 */
public class TimingTest {
    private final static ReflectData reflectData = ReflectData.get();
    private final static Schema schema = reflectData.getSchema(ThingWrapper.class);

    private static Map<String, Long> results = new HashMap<>();

    public static void main(String[] args) throws IOException {
        File csvFile = new File(TimingTest.class.getClassLoader().getResource("jackson.csv").getPath());
        BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile));
        bw.write("Count,Encode,Decode");
        bw.close();
        int inc = 1;
        for (int i = 0; i < 10; i = i + inc) {
            test(1000 * (i + 1));
//            testJackson(1000 * (i + 1));
            FileWriter fw = new FileWriter(csvFile.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.append("\n" + 1000 * (i + 1) + "," +
                    results.get("Encode(" + 1000 * (i + 1) + ")") + "," +
                    results.get("Decode(" + 1000 * (i + 1) + ")"));
            bw.close();
            switch (i){
                case 4:
                    inc = 5;
                    break;
                case 9:
                    inc = 10;
                    break;
                case 49:
                    inc = 50;
                    break;
                case 99:
                    inc = 100;
                    break;
                case 499:
                    inc = 500;
                    break;
            }
        }
    }

    private static void test(int cases) throws IOException{
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Encoder e = EncoderFactory.get().jsonEncoder(schema, os);
        ArrayList<ThingWrapper> things = new ArrayList<>();
        ArrayList<String> jsons = new ArrayList<>();

        for (int i = 0; i < cases; i++){
            String dataout = "{\"meta\": {\"bridgeCode\": \"ALEB\",\"sqn\": " + (i + 1) + ",\"specName\": \"MYSPECNAME\",\"origin\": [],\"units\": \"ft\",\"partition\": 57,\"numPartitions\": 32,\"reblinked\": false,\"outOfOrder\": false,\"newBlink\": false},\"id\": 300004,\"serialNumber\": \"TEST" + (i + 1) + "\",\"name\": \"TEST" + (i + 1) + "\",\"createdTime\": \"2016-10-24T17:37:40.188-04:00\",\"modifiedTime\": \"2016-10-25T08:53:41.060-04:00\",\"time\": \"2016-10-25T16:11:20.031-04:00\",\"group\": {\"id\": 3,\"name\": \"Santa Monica\",\"code\": \"SM\",\"groupType\": {\"id\": 3,\"name\": \"Store\",\"code\": \"Store\"}},\"thingType\": {\"id\": 1,\"name\": \"Default RFID Thing Type\",\"code\": \"default_rfid_thingtype\"},\"properties\": [{\"eNode\": {\"id\": 5,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-24T17:37:40.161-04:00\",\"value\": \"x3ed9371\",\"dataTypeId\": 1},\"lastDetectTime\": {\"id\": 7,\"blinked\": true,\"modified\": true,\"timeSeries\": false,\"time\": \"2016-10-25T16:11:20.031-04:00\",\"value\": \"2016-10-25T16:11:20.031-04:00\",\"dataTypeId\": 24},\"lastLocateTime\": {\"id\": 6,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": \"2016-10-25T08:53:38.565-04:00\",\"dataTypeId\": 24},\"location\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": true,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": [-118.44380581724973,34.04821254121192,0],\"dataTypeId\": 2},\"locationXYZ\": {\"id\": 2,\"blinked\": false,\"modified\": false,\"timeSeries\": true,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": [61.46,24.48,0],\"dataTypeId\": 3},\"tsCoreIn\": {\"id\": 1,\"blinked\": true,\"modified\": false,\"timeSeries\": true,\"time\": \"2016-10-25T16:11:20.031-04:00\",\"value\": \"2016-10-25T16:11:31.575-04:00\",\"dataTypeId\": 11},\"zone\": {\"id\": 4,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"value\": {\"id\": 8,\"code\": \"Enance\",\"name\": \"Entrance\",\"facilityMap\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Map Store Santa Monica\",\"code\": \"SM\",\"blinked\": true,\"modified\": false},\"zoneType\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Off-Site\",\"code\": \"Off-Site\",\"blinked\": false,\"modified\": false},\"zoneGroup\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Default-Zone-Group\",\"code\": \"Default-Zone-Group\",\"blinked\": false,\"modified\": true}},\"dataTypeId\": 9},\"logicalreader\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"zoneInId\": 1,\"zoneOutId\": 4,\"x\": \"\",\"y\": \"\",\"z\": \"\"},\"dataTypeId\": 23},\"group\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 5,\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"description\": \"Description of the group\",\"hierarchyName\": \">mojix>SM\",\"archived\": true,\"treeLevel\": 3},\"dataTypeId\": 22},\"shift\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": true,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 3,\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"active\": true,\"startTimeOfDay\": 1700,\"endTimeOfDay\": 1700,\"daysOfWeek\": \"23456\"},\"dataTypeId\": 7},\"thingTypeUDF\": {\"id\": 40,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 300003,\"serialNumber\": \"TESTKAFKA003\",\"name\": \"TESTKAFKA003\",\"createdTime\": \"2016-10-24T17:37:40.188-04:00\",\"modifiedTime\": \"2016-10-25T08:53:41.060-04:00\",\"time\": \"2016-10-25T16:11:20.031-04:00\",\"group\": {\"id\": 3,\"name\": \"Santa Monica\",\"code\": \"SM\",\"groupType\": {\"id\": 3,\"name\": \"Store\",\"code\": \"Store\"}},\"thingType\": {\"id\": 1,\"name\": \"Default RFID Thing Type\",\"code\": \"default_rfid_thingtype\"},\"properties\": [{\"eNode\": {\"id\": 5,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-24T17:37:40.161-04:00\",\"value\": \"x3ed9371\",\"dataTypeId\": 1},\"lastDetectTime\": {\"id\": 7,\"blinked\": true,\"modified\": true,\"timeSeries\": false,\"time\": \"2016-10-25T16:11:20.031-04:00\",\"value\": \"2016-10-25T16:11:20.031-04:00\",\"dataTypeId\": 24},\"lastLocateTime\": {\"id\": 6,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": \"2016-10-25T08:53:38.565-04:00\",\"dataTypeId\": 24},\"location\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": true,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": [-118.44380581724973,34.04821254121192,0],\"dataTypeId\": 2},\"locationXYZ\": {\"id\": 2,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": [61.46,24.48,0],\"dataTypeId\": 3},\"tsCoreIn\": {\"id\": 1,\"blinked\": true,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T16:11:20.031-04:00\",\"value\": \"2016-10-25T16:11:31.575-04:00\",\"dataTypeId\": 11},\"zone\": {\"id\": 4,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"value\": {\"id\": 8,\"code\": \"Enance\",\"name\": \"Entrance\",\"facilityMap\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Map Store Santa Monica\",\"code\": \"SM\"},\"zoneType\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Off-Site\",\"code\": \"Off-Site\"},\"zoneGroup\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Default-Zone-Group\",\"code\": \"Default-Zone-Group\"}},\"dataTypeId\": 9},\"logicalreader\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"zoneInId\": 1,\"zoneOutId\": 4,\"x\": \"\",\"y\": \"\",\"z\": \"\"},\"dataTypeId\": 23},\"group\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 5,\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"description\": \"Description of the group\",\"hierarchyName\": \">mojix>SM\",\"archived\": true,\"treeLevel\": 3},\"dataTypeId\": 22},\"shift\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 3,\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"active\": true,\"startTimeOfDay\": 1700,\"endTimeOfDay\": 1700,\"daysOfWeek\": \"23456\"},\"dataTypeId\": 7}}]},\"dataTypeId\": 27}},{\"eNode\": {\"id\": 5,\"time\": \"2016-10-24T17:37:40.161-04:00\",\"value\": \"x3ed9371\",\"dataTypeId\": 1},\"lastDetectTime\": {\"id\": 7,\"time\": \"2016-10-25T16:11:20.031-04:00\",\"value\": \"2016-10-25T16:11:20.031-04:00\",\"dataTypeId\": 24},\"lastLocateTime\": {\"id\": 6,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": \"2016-10-25T08:53:38.565-04:00\",\"dataTypeId\": 24},\"location\": {\"id\": 1,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": [-118.44380581724973,34.04821254121192,0],\"dataTypeId\": 2},\"locationXYZ\": {\"id\": 2,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": [61.46,24.48,0],\"dataTypeId\": 3},\"tsCoreIn\": {\"id\": 1,\"time\": \"2016-10-25T16:11:20.031-04:00\",\"value\": \"2016-10-25T16:11:31.575-04:00\",\"dataTypeId\": 11},\"zone\": {\"id\": 4,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 8,\"code\": \"Enance\",\"name\": \"Entrance\",\"facilityMap\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Map Store Santa Monica\",\"code\": \"SM\"},\"zoneType\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Off-Site\",\"code\": \"Off-Site\"},\"zoneGroup\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Default-Zone-Group\",\"code\": \"Default-Zone-Group\"}},\"dataTypeId\": 9},\"logicalreader\": {\"id\": 1,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"zoneInId\": 1,\"zoneOutId\": 4,\"x\": \"\",\"y\": \"\",\"z\": \"\"},\"dataTypeId\": 23},\"group\": {\"id\": 1,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 5,\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"description\": \"Description of the group\",\"hierarchyName\": \">mojix>SM\",\"archived\": true,\"treeLevel\": 3},\"dataTypeId\": 22},\"shift\": {\"id\": 1,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 3,\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"active\": true,\"startTimeOfDay\": 1700,\"endTimeOfDay\": 1700,\"daysOfWeek\": \"23456\"},\"dataTypeId\": 7},\"thingTypeUDF\": {\"id\": 40,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 300003,\"serialNumber\": \"TESTKAFKA003\",\"name\": \"TESTKAFKA003\",\"createdTime\": \"2016-10-24T17:37:40.188-04:00\",\"modifiedTime\": \"2016-10-25T08:53:41.060-04:00\",\"time\": \"2016-10-25T16:11:20.031-04:00\",\"group\": {\"id\": 3,\"name\": \"Santa Monica\",\"code\": \"SM\",\"groupType\": {\"id\": 3,\"name\": \"Store\",\"code\": \"Store\"}},\"thingType\": {\"id\": 1,\"name\": \"Default RFID Thing Type\",\"code\": \"default_rfid_thingtype\"}},\"dataTypeId\": 27}}]}";
            things.add(ThingWrapper.parse(dataout));
        }

        long t0 = System.currentTimeMillis();
        for (int i =0; i < cases; i++){
            DatumWriter<ThingWrapper> writer = new ReflectDatumWriter<>(schema);
            writer.write(things.get(i), e);
            e.flush();
            jsons.add(os.toString());
        }
        results.put("Encode("+things.size()+")", (System.currentTimeMillis() - t0));
        System.out.println("Encode("+things.size()+"):" + (System.currentTimeMillis() - t0));

        t0 = System.currentTimeMillis();
        for (int i =0; i < cases; i++){
            ReflectDatumReader<ThingWrapper> reader = new ReflectDatumReader<>(schema);
            Decoder decoder = DecoderFactory.get().jsonDecoder(schema, jsons.get(i));
            ThingWrapper decodedThingWrapper= reader.read(null, decoder);
        }
        results.put("Decode("+things.size()+")", (System.currentTimeMillis() - t0));
        System.out.println("Decode("+things.size()+"):" + (System.currentTimeMillis() - t0));
    }

    private static void testJackson(int cases) throws IOException{
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Encoder e = EncoderFactory.get().jsonEncoder(schema, os);
        ArrayList<ThingWrapper> things = new ArrayList<>();
        ArrayList<String> jsons = new ArrayList<>();

        for (int i = 0; i < cases; i++){
            String dataout = "{\"meta\": {\"bridgeCode\": \"ALEB\",\"sqn\": " + (i + 1) + ",\"specName\": \"MYSPECNAME\",\"origin\": [],\"units\": \"ft\",\"partition\": 57,\"numPartitions\": 32,\"reblinked\": false,\"outOfOrder\": false,\"newBlink\": false},\"id\": 300004,\"serialNumber\": \"TEST" + (i + 1) + "\",\"name\": \"TEST" + (i + 1) + "\",\"createdTime\": \"2016-10-24T17:37:40.188-04:00\",\"modifiedTime\": \"2016-10-25T08:53:41.060-04:00\",\"time\": \"2016-10-25T16:11:20.031-04:00\",\"group\": {\"id\": 3,\"name\": \"Santa Monica\",\"code\": \"SM\",\"groupType\": {\"id\": 3,\"name\": \"Store\",\"code\": \"Store\"}},\"thingType\": {\"id\": 1,\"name\": \"Default RFID Thing Type\",\"code\": \"default_rfid_thingtype\"},\"properties\": [{\"eNode\": {\"id\": 5,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-24T17:37:40.161-04:00\",\"value\": \"x3ed9371\",\"dataTypeId\": 1},\"lastDetectTime\": {\"id\": 7,\"blinked\": true,\"modified\": true,\"timeSeries\": false,\"time\": \"2016-10-25T16:11:20.031-04:00\",\"value\": \"2016-10-25T16:11:20.031-04:00\",\"dataTypeId\": 24},\"lastLocateTime\": {\"id\": 6,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": \"2016-10-25T08:53:38.565-04:00\",\"dataTypeId\": 24},\"location\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": true,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": [-118.44380581724973,34.04821254121192,0],\"dataTypeId\": 2},\"locationXYZ\": {\"id\": 2,\"blinked\": false,\"modified\": false,\"timeSeries\": true,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": [61.46,24.48,0],\"dataTypeId\": 3},\"tsCoreIn\": {\"id\": 1,\"blinked\": true,\"modified\": false,\"timeSeries\": true,\"time\": \"2016-10-25T16:11:20.031-04:00\",\"value\": \"2016-10-25T16:11:31.575-04:00\",\"dataTypeId\": 11},\"zone\": {\"id\": 4,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"value\": {\"id\": 8,\"code\": \"Enance\",\"name\": \"Entrance\",\"facilityMap\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Map Store Santa Monica\",\"code\": \"SM\",\"blinked\": true,\"modified\": false},\"zoneType\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Off-Site\",\"code\": \"Off-Site\",\"blinked\": false,\"modified\": false},\"zoneGroup\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Default-Zone-Group\",\"code\": \"Default-Zone-Group\",\"blinked\": false,\"modified\": true}},\"dataTypeId\": 9},\"logicalreader\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"zoneInId\": 1,\"zoneOutId\": 4,\"x\": \"\",\"y\": \"\",\"z\": \"\"},\"dataTypeId\": 23},\"group\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 5,\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"description\": \"Description of the group\",\"hierarchyName\": \">mojix>SM\",\"archived\": true,\"treeLevel\": 3},\"dataTypeId\": 22},\"shift\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": true,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 3,\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"active\": true,\"startTimeOfDay\": 1700,\"endTimeOfDay\": 1700,\"daysOfWeek\": \"23456\"},\"dataTypeId\": 7},\"thingTypeUDF\": {\"id\": 40,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 300003,\"serialNumber\": \"TESTKAFKA003\",\"name\": \"TESTKAFKA003\",\"createdTime\": \"2016-10-24T17:37:40.188-04:00\",\"modifiedTime\": \"2016-10-25T08:53:41.060-04:00\",\"time\": \"2016-10-25T16:11:20.031-04:00\",\"group\": {\"id\": 3,\"name\": \"Santa Monica\",\"code\": \"SM\",\"groupType\": {\"id\": 3,\"name\": \"Store\",\"code\": \"Store\"}},\"thingType\": {\"id\": 1,\"name\": \"Default RFID Thing Type\",\"code\": \"default_rfid_thingtype\"},\"properties\": [{\"eNode\": {\"id\": 5,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-24T17:37:40.161-04:00\",\"value\": \"x3ed9371\",\"dataTypeId\": 1},\"lastDetectTime\": {\"id\": 7,\"blinked\": true,\"modified\": true,\"timeSeries\": false,\"time\": \"2016-10-25T16:11:20.031-04:00\",\"value\": \"2016-10-25T16:11:20.031-04:00\",\"dataTypeId\": 24},\"lastLocateTime\": {\"id\": 6,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": \"2016-10-25T08:53:38.565-04:00\",\"dataTypeId\": 24},\"location\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": true,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": [-118.44380581724973,34.04821254121192,0],\"dataTypeId\": 2},\"locationXYZ\": {\"id\": 2,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": [61.46,24.48,0],\"dataTypeId\": 3},\"tsCoreIn\": {\"id\": 1,\"blinked\": true,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T16:11:20.031-04:00\",\"value\": \"2016-10-25T16:11:31.575-04:00\",\"dataTypeId\": 11},\"zone\": {\"id\": 4,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"value\": {\"id\": 8,\"code\": \"Enance\",\"name\": \"Entrance\",\"facilityMap\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Map Store Santa Monica\",\"code\": \"SM\"},\"zoneType\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Off-Site\",\"code\": \"Off-Site\"},\"zoneGroup\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Default-Zone-Group\",\"code\": \"Default-Zone-Group\"}},\"dataTypeId\": 9},\"logicalreader\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"zoneInId\": 1,\"zoneOutId\": 4,\"x\": \"\",\"y\": \"\",\"z\": \"\"},\"dataTypeId\": 23},\"group\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 5,\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"description\": \"Description of the group\",\"hierarchyName\": \">mojix>SM\",\"archived\": true,\"treeLevel\": 3},\"dataTypeId\": 22},\"shift\": {\"id\": 1,\"blinked\": false,\"modified\": false,\"timeSeries\": false,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 3,\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"active\": true,\"startTimeOfDay\": 1700,\"endTimeOfDay\": 1700,\"daysOfWeek\": \"23456\"},\"dataTypeId\": 7}}]},\"dataTypeId\": 27}},{\"eNode\": {\"id\": 5,\"time\": \"2016-10-24T17:37:40.161-04:00\",\"value\": \"x3ed9371\",\"dataTypeId\": 1},\"lastDetectTime\": {\"id\": 7,\"time\": \"2016-10-25T16:11:20.031-04:00\",\"value\": \"2016-10-25T16:11:20.031-04:00\",\"dataTypeId\": 24},\"lastLocateTime\": {\"id\": 6,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": \"2016-10-25T08:53:38.565-04:00\",\"dataTypeId\": 24},\"location\": {\"id\": 1,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": [-118.44380581724973,34.04821254121192,0],\"dataTypeId\": 2},\"locationXYZ\": {\"id\": 2,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": [61.46,24.48,0],\"dataTypeId\": 3},\"tsCoreIn\": {\"id\": 1,\"time\": \"2016-10-25T16:11:20.031-04:00\",\"value\": \"2016-10-25T16:11:31.575-04:00\",\"dataTypeId\": 11},\"zone\": {\"id\": 4,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 8,\"code\": \"Enance\",\"name\": \"Entrance\",\"facilityMap\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Map Store Santa Monica\",\"code\": \"SM\"},\"zoneType\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Off-Site\",\"code\": \"Off-Site\"},\"zoneGroup\": {\"time\": \"2016-10-25T16:11:20.031-04:00\",\"name\": \"Default-Zone-Group\",\"code\": \"Default-Zone-Group\"}},\"dataTypeId\": 9},\"logicalreader\": {\"id\": 1,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"zoneInId\": 1,\"zoneOutId\": 4,\"x\": \"\",\"y\": \"\",\"z\": \"\"},\"dataTypeId\": 23},\"group\": {\"id\": 1,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 5,\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"description\": \"Description of the group\",\"hierarchyName\": \">mojix>SM\",\"archived\": true,\"treeLevel\": 3},\"dataTypeId\": 22},\"shift\": {\"id\": 1,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 3,\"code\": \"LR1\",\"name\": \"LogicalReader1\",\"active\": true,\"startTimeOfDay\": 1700,\"endTimeOfDay\": 1700,\"daysOfWeek\": \"23456\"},\"dataTypeId\": 7},\"thingTypeUDF\": {\"id\": 40,\"time\": \"2016-10-25T08:53:38.565-04:00\",\"value\": {\"id\": 300003,\"serialNumber\": \"TESTKAFKA003\",\"name\": \"TESTKAFKA003\",\"createdTime\": \"2016-10-24T17:37:40.188-04:00\",\"modifiedTime\": \"2016-10-25T08:53:41.060-04:00\",\"time\": \"2016-10-25T16:11:20.031-04:00\",\"group\": {\"id\": 3,\"name\": \"Santa Monica\",\"code\": \"SM\",\"groupType\": {\"id\": 3,\"name\": \"Store\",\"code\": \"Store\"}},\"thingType\": {\"id\": 1,\"name\": \"Default RFID Thing Type\",\"code\": \"default_rfid_thingtype\"}},\"dataTypeId\": 27}}]}";
            things.add(ThingWrapper.parse(dataout));
        }

        long t0 = System.currentTimeMillis();
        for (int i =0; i < cases; i++){
            String test = ThingWrapper.toJsonString(things.get(i));
            jsons.add(test);

        }
        results.put("Encode("+things.size()+")", (System.currentTimeMillis() - t0));
        System.out.println("Encode("+things.size()+"):" + (System.currentTimeMillis() - t0));

        t0 = System.currentTimeMillis();
        for (int i =0; i < cases; i++){
//            ThingWrapper decodedThingWrapper= ThingWrapper.parse(jsons.get(i));
            ThingWrapper decodedThingWrapper= ThingWrapper.parse(jsons.get(i));
        }
        results.put("Decode("+things.size()+")", (System.currentTimeMillis() - t0));
        System.out.println("Decode("+things.size()+"):" + (System.currentTimeMillis() - t0));
    }
}