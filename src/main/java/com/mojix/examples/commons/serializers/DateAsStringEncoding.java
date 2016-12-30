package com.mojix.examples.commons.serializers;

import org.apache.avro.Schema;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.Encoder;
import org.apache.avro.reflect.CustomEncoding;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import static com.mojix.examples.commons.utils.FormatUtil.parse;

/**
 * Created by dbascope on 12/27/16
 */
public class DateAsStringEncoding extends CustomEncoding<Date> {
    public DateAsStringEncoding() {
        schema = Schema.create(Schema.Type.STRING);
        schema.addProp("CustomEncoding", "DateAsStringEncoding");
    }

    @Override
    public void write(Object datum, Encoder out) throws IOException {
        out.writeString(datum.toString());
    }

    @Override
    public Date read(Object reuse, Decoder in) throws IOException {
        try {
            if (reuse != null) {
                reuse = parse(in.readString());
                return (Date)reuse;
            }
            else return parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}