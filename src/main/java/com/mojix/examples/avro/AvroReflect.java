package com.mojix.examples.avro;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

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

    public static void main(String[] args) throws IOException, InterruptedException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        Encoder e = EncoderFactory.get().binaryEncoder(os, null);
        Encoder e = EncoderFactory.get().jsonEncoder(schema, os);

        DatumWriter<Employee> writer = new ReflectDatumWriter<>(schema);
        Employee employee = new Employee();
        employee.setName("Kamal");
        employee.setSsn("000-00-0000");
        employee.setAge(29);
        Date birthday = new Date();
        employee.setBirthday(birthday);

        writer.write(employee, e);
        e.flush();

        System.out.println(new Date() + " --- " + os.toString());

        Thread.sleep(5000);
        ReflectDatumReader<Employee> reader = new ReflectDatumReader<>(schema);
//        Decoder decoder = DecoderFactory.get().binaryDecoder(os.toByteArray(), null);
        Decoder decoder = DecoderFactory.get().jsonDecoder(schema, os.toString());
        Employee decodedEmployee = reader.read(null, decoder);

        System.out.println("Name: "+decodedEmployee.getName());
        System.out.println("Age: "+decodedEmployee.getAge());
        System.out.println("SSN: "+decodedEmployee.getSsn());
        System.out.println("Birthday: "+decodedEmployee.getBirthday());
    }
}