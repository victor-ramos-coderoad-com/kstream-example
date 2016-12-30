package com.mojix.examples.commons.serializers;

import org.apache.avro.Schema;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.Encoder;
import org.apache.avro.reflect.CustomEncoding;

import java.io.IOException;
import java.util.Date;

/**
 * Created by dbascope on 12/27/16
 */
public class DateAsLongEncoding extends CustomEncoding<Date> {
    public DateAsLongEncoding() {
        schema = Schema.create(Schema.Type.LONG);
        schema.addProp("CustomEncoding", "DateAsLongEncoding");
    }

    @Override
    public void write(Object datum, Encoder out) throws IOException {
        out.writeLong(((Date)datum).getTime());
    }

    @Override
    public Date read(Object reuse, Decoder in) throws IOException {
        if (reuse != null) {
            ((Date)reuse).setTime(in.readLong());
            return (Date)reuse;
        }
        else return new Date(in.readLong());
    }
}