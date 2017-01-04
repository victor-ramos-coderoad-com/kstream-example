package com.mojix.examples.commons.serializers;

import com.mojix.examples.commons.wrappers.*;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.Encoder;
import org.apache.avro.reflect.CustomEncoding;
import org.apache.avro.reflect.ReflectData;

import java.io.IOException;
import java.util.*;

/**
 * Created by dbascope on 1/3/17
 */
public class FinalThingWrapper extends CustomEncoding<PropertyWrapper> {
    public FinalThingWrapper() {
        ReflectData reflectData = ReflectData.get();
        schema = reflectData.getSchema(PropertyWrapper.class);
    }

    @Override
    protected void write(Object datum, Encoder out) throws IOException {
        Map<String, ThingPropertyWrapper> current = ((PropertyWrapper) datum).getCurrent();
        Map<String, ThingPropertyWrapper> previous = ((PropertyWrapper) datum).getPrevious();
        encodeProperties(current, out);
        encodeProperties(previous, out);
    }

    @Override
    protected PropertyWrapper read(Object reuse, Decoder in) throws IOException {
        Map<String, ThingPropertyWrapper> current = decodeProperties(in);
        Map<String, ThingPropertyWrapper> previous = decodeProperties(in);
        return new PropertyWrapper(current, previous);
    }

    private void encodeProperties(Map<String, ThingPropertyWrapper> properties, Encoder out) throws IOException {
        out.writeMapStart();
        out.setItemCount(properties.size());
        for (String key : properties.keySet()) {
            out.startItem();
            out.writeString(key);
            out.writeLong(properties.get(key).getId());
            out.writeLong(properties.get(key).getTime().getTime());
            out.writeBoolean(properties.get(key).isBlinked());
            out.writeBoolean(properties.get(key).isModified());
            out.writeBoolean(properties.get(key).isTimeSeries());
            encodeValue(properties.get(key), out);
            out.writeLong(properties.get(key).getDataTypeId());
        }
        out.writeMapEnd();
    }

    private Map<String, ThingPropertyWrapper> decodeProperties(Decoder in) throws IOException {
        Map<String, ThingPropertyWrapper> properties = new HashMap<>();
        in.readMapStart();
        do {
            String udf = in.readString();
            Long id = in.readLong();
            Date time = new Date(in.readLong());
            Boolean blinked = in.readBoolean();
            Boolean modified = in.readBoolean();
            Boolean timeSeries = in.readBoolean();
            int index = in.readIndex();
            Object value = decodeValue(index, in);
            Long dataTypeId = in.readLong();
            properties.put(udf, new ThingPropertyWrapper(id,
                    time,
                    blinked,
                    modified,
                    timeSeries,
                    value,
                    dataTypeId
            ));
        } while (in.mapNext() > 0);
        return properties;
    }

    private void encodeValue(ThingPropertyWrapper udf, Encoder out) throws IOException {
        switch (DataType.getDataTypeById(udf.getDataTypeId())) {
            case TYPE_SHIFT:
                out.writeIndex(0);
                out.writeBoolean(((ShiftWrapper) udf.getValue()).isActive());
                out.writeLong(((ShiftWrapper) udf.getValue()).getStartTimeOfDay());
                out.writeLong(((ShiftWrapper) udf.getValue()).getEndTimeOfDay());
                out.writeString(((ShiftWrapper) udf.getValue()).getDaysOfWeek());
                out.writeLong(((ShiftWrapper) udf.getValue()).getId());
                out.writeString(((ShiftWrapper) udf.getValue()).getName());
                out.writeString(((ShiftWrapper) udf.getValue()).getCode());
                break;
            case TYPE_ZONE:
                out.writeIndex(1);
//Facility Map
                out.writeLong(((ZoneWrapper) udf.getValue()).getFacilityMap().getTime().getTime());
                out.writeBoolean(((ZoneWrapper) udf.getValue()).getFacilityMap().getBlinked() != null
                        && ((ZoneWrapper) udf.getValue()).getFacilityMap().getBlinked());
                out.writeBoolean(((ZoneWrapper) udf.getValue()).getFacilityMap().getModified() != null
                        && ((ZoneWrapper) udf.getValue()).getFacilityMap().getModified());
                out.writeBoolean(false);
                out.writeString(((ZoneWrapper) udf.getValue()).getFacilityMap().getName());
                out.writeString(((ZoneWrapper) udf.getValue()).getFacilityMap().getCode());
//Zone Type
                out.writeLong(((ZoneWrapper) udf.getValue()).getZoneType().getTime().getTime());
                out.writeBoolean(((ZoneWrapper) udf.getValue()).getZoneType().getBlinked() != null
                        && ((ZoneWrapper) udf.getValue()).getZoneType().getBlinked());
                out.writeBoolean(((ZoneWrapper) udf.getValue()).getZoneType().getModified() != null
                        && ((ZoneWrapper) udf.getValue()).getZoneType().getModified());
                out.writeBoolean(false);
                out.writeString(((ZoneWrapper) udf.getValue()).getZoneType().getName());
                out.writeString(((ZoneWrapper) udf.getValue()).getZoneType().getCode());
//Zone Group
                out.writeLong(((ZoneWrapper) udf.getValue()).getZoneGroup().getTime().getTime());
                out.writeBoolean(((ZoneWrapper) udf.getValue()).getZoneGroup().getBlinked() != null
                        && ((ZoneWrapper) udf.getValue()).getZoneGroup().getBlinked());
                out.writeBoolean(((ZoneWrapper) udf.getValue()).getZoneGroup().getModified() != null
                        && ((ZoneWrapper) udf.getValue()).getZoneGroup().getModified());
                out.writeBoolean(false);
                out.writeString(((ZoneWrapper) udf.getValue()).getZoneGroup().getName());
                out.writeString(((ZoneWrapper) udf.getValue()).getZoneGroup().getCode());
                out.writeLong(((ZoneWrapper) udf.getValue()).getId());
                out.writeString(((ZoneWrapper) udf.getValue()).getName());
                out.writeString(((ZoneWrapper) udf.getValue()).getCode());
                break;
            case TYPE_GROUP:
                out.writeIndex(2);
                out.writeString(((GroupThingFieldWrapper) udf.getValue()).getDescription());
                out.writeString(((GroupThingFieldWrapper) udf.getValue()).getHierarchyName());
                out.writeBoolean(((GroupThingFieldWrapper) udf.getValue()).isArchived());
                out.writeLong(((GroupThingFieldWrapper) udf.getValue()).getTreeLevel());
                out.writeLong(((GroupThingFieldWrapper) udf.getValue()).getId());
                out.writeString(((GroupThingFieldWrapper) udf.getValue()).getName());
                out.writeString(((GroupThingFieldWrapper) udf.getValue()).getCode());
                break;
            case TYPE_LOGICAL_READER:
                out.writeIndex(3);
                out.writeLong(((LogicalReaderWrapper) udf.getValue()).getZoneInId());
                out.writeLong(((LogicalReaderWrapper) udf.getValue()).getZoneOutId());
                out.writeString(((LogicalReaderWrapper) udf.getValue()).getX());
                out.writeString(((LogicalReaderWrapper) udf.getValue()).getY());
                out.writeString(((LogicalReaderWrapper) udf.getValue()).getZ());
                out.writeLong(0L);
                out.writeString(((LogicalReaderWrapper) udf.getValue()).getName());
                out.writeString(((LogicalReaderWrapper) udf.getValue()).getCode());
                break;
            case TYPE_NUMBER:
                out.writeIndex(4);
                out.writeLong((Long) udf.getValue());
                break;
            case TYPE_BOOLEAN:
                out.writeIndex(5);
                out.writeBoolean((Boolean) udf.getValue());
                break;
            case TYPE_TEXT:
            default:
                out.writeIndex(6);
                out.writeString(udf.getValue().toString());
        }
    }

    private Object decodeValue(int index, Decoder in) throws IOException {
        switch (index) {
            case 0:
                return new ShiftWrapper(
                        in.readBoolean(),
                        in.readLong(),
                        in.readLong(),
                        in.readString(),
                        in.readLong(),
                        in.readString(),
                        in.readString()
                );
            case 1:
                return new ZoneWrapper(
                        new ZonePropertyWrapper(
                                new Date(in.readLong()),
                                in.readBoolean(),
                                in.readBoolean(),
                                in.readBoolean(),
                                in.readString(),
                                in.readString()
                        ),
                        new ZonePropertyWrapper(
                                new Date(in.readLong()),
                                in.readBoolean(),
                                in.readBoolean(),
                                in.readBoolean(),
                                in.readString(),
                                in.readString()
                        ),
                        new ZonePropertyWrapper(
                                new Date(in.readLong()),
                                in.readBoolean(),
                                in.readBoolean(),
                                in.readBoolean(),
                                in.readString(),
                                in.readString()
                        ),
                        in.readLong(),
                        in.readString(),
                        in.readString()
                );
            case 2:
                return new GroupThingFieldWrapper(
                        in.readString(),
                        in.readString(),
                        in.readBoolean(),
                        in.readLong(),
                        in.readLong(),
                        in.readString(),
                        in.readString()
                );
            case 3:
                return new LogicalReaderWrapper(
                        in.readLong(),
                        in.readLong(),
                        in.readString(),
                        in.readString(),
                        in.readString(),
                        in.readLong(),
                        in.readString(),
                        in.readString()
                );
            case 4:
                return in.readLong();
            case 5:
                return in.readBoolean();
            case 6:
            default:
                return in.readString();
        }
    }
}
