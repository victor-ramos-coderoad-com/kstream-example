package com.mojix.examples.avro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;

/**
 * Created by dbascope on 12/22/16
 * Encode the data using JSON schema and embed the schema as metadata along with the data.
 * On the other end, just decode the data using the embedded schema.
 */
public class AvroDataFile {

    public static void main(String[] args) throws IOException {
        // Schema
        String schemaDescription = " {    \n"
                + " \"name\": \"FacebookUser\", \n"
                + " \"type\": \"record\",\n" + " \"fields\": [\n"
                + "   {\"name\": \"name\", \"type\": \"string\"},\n"
                + "   {\"name\": \"num_likes\", \"type\": \"int\"},\n"
                + "   {\"name\": \"num_photos\", \"type\": \"int\"},\n"
                + "   {\"name\": \"num_groups\", \"type\": \"int\"} ]\n" + "}";

        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(schemaDescription);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        DatumWriter<GenericRecord> writer = new GenericDatumWriter<>(schema);
        DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(writer);
        dataFileWriter.create(schema, os);

        // Populate data
        GenericRecord datum = new GenericData.Record(schema);
        datum.put("name", new org.apache.avro.util.Utf8("Doctor Who"));
        datum.put("num_likes", 1);
        datum.put("num_groups", 423);
        datum.put("num_photos", 0);

        dataFileWriter.append(datum);
        dataFileWriter.close();

        System.out.println("encoded string: " + os.toString());

        // Decode
        DatumReader<GenericRecord> reader = new GenericDatumReader<>();
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        DataFileStream<GenericRecord> dataFileReader = new DataFileStream<>(
                is, reader);

        GenericRecord record = null;
        while (dataFileReader.hasNext()) {
            record = dataFileReader.next(record);
            System.out.println(record.get("name").toString());
            System.out.println(record.get("num_likes").toString());
            System.out.println(record.get("num_groups").toString());
            System.out.println(record.get("num_photos").toString());
        }
    }
}