package com.mojix.examples.commons.serializers;

import com.mojix.examples.commons.wrappers.*;
import org.apache.avro.Schema;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.Encoder;
import org.apache.avro.reflect.CustomEncoding;
import org.apache.avro.reflect.ReflectData;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by dbascope on 12/27/16
 */
public class ThingPropertyEncoding extends CustomEncoding<ThingPropertyWrapper> {
    public ThingPropertyEncoding() {
        ReflectData reflectData = ReflectData.get();
        schema = Schema.createUnion(Arrays.asList(
                reflectData.getSchema(ShiftWrapper.class),
                reflectData.getSchema(ZoneWrapper.class),
                reflectData.getSchema(GroupThingFieldWrapper.class),
                reflectData.getSchema(LogicalReaderWrapper.class),
                Schema.create(Schema.Type.LONG),
                Schema.create(Schema.Type.BOOLEAN),
                Schema.create(Schema.Type.STRING)
        )) ;
    }

    @Override
    protected void write(Object datum, Encoder out) throws IOException {
        System.out.println("-------->" + datum);
        if (StringUtils.contains(datum.getClass().toString(), "java.")) {
            out.writeString(datum.toString());
        } else {
            String output = "{";
            try {
                for (Field i : datum.getClass().getFields()) {
                    if (StringUtils.contains(datum.getClass().toString(), "java.")) {
                        output += "\"" + i.getName() + "\":\"";
                        output += i.get(datum) + "\",";
                    } else {
                        for (Field j : i.get(datum).getClass().getFields()) {
                            output += "\"" + j.getName() + "\":\"";
                            output += j.get(i.get(datum)) + "\",";
                        }
                    }
                }
            } catch (Exception e) {
//                e.printStackTrace();
            }
            output += "}";
            out.writeString(output);
        }
    }

    @Override
    protected ThingPropertyWrapper read(Object reuse, Decoder in) throws IOException {
        System.out.println(in + "-------->" + in.getClass());
        return new ThingPropertyWrapper(0L,
                new Date(),
                false,
                false,
                false,
                "Value",
                0L);
    }
}
