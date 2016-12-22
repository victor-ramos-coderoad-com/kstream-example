package com.mojix.examples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;

/**
 * Created by dbascope on 12/22/16
 * Use Reflection to encode and decode the data.
 */
public class AvroReflect {
    private final static ReflectData reflectData = ReflectData.get();
    private final static Schema schema = reflectData.getSchema(Employee.class);

    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Encoder e = EncoderFactory.get().binaryEncoder(os, null);

        DatumWriter<Employee> writer = new ReflectDatumWriter<>(schema);
        Employee employee = new Employee();
        employee.setName("Kamal");
        employee.setSsn("000-00-0000");
        employee.setAge(29);

        writer.write(employee, e);
        e.flush();

        System.out.println(os.toString());

        ReflectDatumReader<Employee> reader = new ReflectDatumReader<>(schema);
        Decoder decoder = DecoderFactory.get().binaryDecoder(os.toByteArray(), null);
        Employee decodedEmployee = reader.read(null, decoder);

        System.out.println("Name: "+decodedEmployee.getName());
        System.out.println("Age: "+decodedEmployee.getAge());
        System.out.println("SSN: "+decodedEmployee.getSsn());
    }
}