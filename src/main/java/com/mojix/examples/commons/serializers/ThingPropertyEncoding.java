package com.mojix.examples.commons.serializers;

import com.mojix.examples.commons.wrappers.ThingPropertyWrapper;
import org.apache.avro.Schema;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.Encoder;
import org.apache.avro.reflect.CustomEncoding;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;

/**
 * Created by dbascope on 12/27/16
 */
public class ThingPropertyEncoding extends CustomEncoding<ThingPropertyWrapper> {
    public ThingPropertyEncoding() {
        schema = Schema.create(Schema.Type.STRING);
        schema.addProp("CustomEncoding", "ThingPropertyEncoding");
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
