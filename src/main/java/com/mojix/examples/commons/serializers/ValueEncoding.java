package com.mojix.examples.commons.serializers;

import com.mojix.examples.avro.EmployeeContact;
import com.mojix.examples.avro.EmployeeValue;
import org.apache.avro.Schema;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.Encoder;
import org.apache.avro.reflect.CustomEncoding;
import org.apache.avro.reflect.ReflectData;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by dbascope on 1/3/17
 */
public class ValueEncoding extends CustomEncoding<EmployeeValue> {
    public ValueEncoding() {
        ReflectData reflectData = ReflectData.get();
        schema = Schema.createUnion(
                Arrays.asList(
                        Schema.create(Schema.Type.STRING),
                        Schema.create(Schema.Type.LONG),
                        Schema.create(Schema.Type.BOOLEAN),
                        reflectData.getSchema(EmployeeContact.class)
                )
        );
    }
    @Override
    protected void write(Object datum, Encoder out) throws IOException {
        if (((EmployeeValue)datum).getDataTypeId() == 1L) {
            out.writeIndex(1);
            out.writeLong((Long)((EmployeeValue)datum).getValue());
        } else {
            if (((EmployeeValue)datum).getDataTypeId() == 2L) {
                out.writeIndex(0);
                out.writeString(((EmployeeValue) datum).getValue().toString());
            } else {
                out.writeIndex(3);
                out.writeString(((EmployeeContact)((EmployeeValue) datum).getValue()).getEmail());
                out.writeLong(((EmployeeContact)((EmployeeValue) datum).getValue()).getPhone());
                out.writeLong(((EmployeeContact)((EmployeeValue) datum).getValue()).isActive());
            }
        }
    }

    @Override
    protected EmployeeValue read(Object reuse, Decoder in) throws IOException {
        int index = in.readIndex();
        if (index == 0) {
            return new EmployeeValue(in.readString(), 2L);
        } else {
            if (index == 1) {
                return new EmployeeValue(in.readLong(), 1L);
            } else {
                return new EmployeeValue(new EmployeeContact(in.readString(), in.readLong(), in.readLong()), 3L);
            }
        }
    }

}
